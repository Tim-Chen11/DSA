import java.io.*;
import java.util.*;

/**
 * Stripe “Catch Me If You Can” – Parts 1–3 combined
 *
 * ─────────────────────────────────────────────────────────────────────────────
 *  Input blocks (identical for every part)
 *   1) comma-CSV of NON-fraud codes
 *   2) comma-CSV of FRAUD   codes
 *   3) <mcc>,<threshold> … (blank line terminates block)
 *        –  threshold > 1  → integer COUNT threshold   (Part 1)
 *        –  threshold ≤ 1  → FRACTION threshold 0–1    (Part 2/3)
 *   4) <account_id>,<mcc>  … (blank line terminates block)
 *   5) single integer  = minimum #tx before evaluation
 *   6) event stream (any order, any length):
 *        CHARGE ,<id>,<acct>,<amt>,<code>
 *        DISPUTE,<chargeId>
 *
 *  Output  : comma-separated, lexicographically-sorted list of account-ids
 *            whose merchants are currently flagged as fraudulent.
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *  Rules summary
 *  -------------
 *  •  A charge is “fraudulent” if its response-code is in the fraud list.
 *  •  Part 1        – flag when (#fraud ≥ countThreshold)       AND total ≥ minTx
 *  •  Part 2        – flag when (fraud/total ≥ pctThreshold)    AND total ≥ minTx
 *                     once flagged it remains flagged unless a DISPUTE cancels
 *                     the fraud (Part 3 rule).
 *  •  Part 3        – DISPUTE,<chargeId> turns that charge from
 *                     “fraudulent” → “non-fraudulent”.
 *                     If that drops the merchant below threshold,
 *                     the flag is removed; it can be re-applied later.
 */
public final class Main {

    /* ─────────────────────── domain ─────────────────────── */

    private static final class Merchant {
        final String id;
        final double threshold;          // raw value from table
        final boolean isPct;             // true if ≤ 1.0  (ratio threshold)
        final int thresholdCount;        // integer form when isPct == false

        Merchant(String id, double threshold) {
            this.id = id;
            this.threshold = threshold;
            this.isPct = threshold <= 1.0;
            this.thresholdCount = (int) Math.round(threshold);
        }
    }

    private static final class MerchantStats {
        int total = 0;
        int fraud = 0;
        boolean flagged = false;

        void recordCharge(boolean isFraud) {
            total++;
            if (isFraud) fraud++;
        }

        void cancelFraud() { if (fraud > 0) fraud--; }

        double ratio() { return total == 0 ? 0.0 : fraud / (double) total; }
    }

    private static final class Charge {
        final String id;
        final String acct;
        boolean wasFraud;
        boolean disputed = false;

        Charge(String id, String acct, boolean wasFraud) {
            this.id = id; this.acct = acct; this.wasFraud = wasFraud;
        }
    }

    /* ─────────────────────── rule engine ─────────────────────── */

    private static final class FraudRuleEngine {
        private final int minTx;

        FraudRuleEngine(int minTx) { this.minTx = minTx; }

        /** called on a *new charge* – may only set flag → true (never unflag) */
        void evaluateOnCharge(Merchant m, MerchantStats s) {
            if (s.flagged) return;
            if (s.total < minTx) return;

            if (m.isPct) {
                if (s.ratio() >= m.threshold) s.flagged = true;
            } else {
                if (s.fraud   >= m.thresholdCount) s.flagged = true;
            }
        }

        /** called *after a dispute* – may clear flag if now below threshold */
        void evaluateOnDispute(Merchant m, MerchantStats s) {
            if (!s.flagged) return;             // nothing to clear
            if (s.total < minTx) { s.flagged = false; return; }

            boolean stillFraud;
            if (m.isPct)  stillFraud = s.ratio()  >= m.threshold;
            else          stillFraud = s.fraud    >= m.thresholdCount;

            if (!stillFraud) s.flagged = false;
        }
    }

    /* ─────────────────────── program entry ─────────────────────── */

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllStdin();
        Iterator<String> it = lines.iterator();

        /* 1 & 2 – response-code lists */
        Set<String> nonFraudCodes = csvToSet(next(it));
        Set<String> fraudCodes    = csvToSet(next(it));

        /* 3 – MCC thresholds */
        Map<String, Double> mccThr = new HashMap<>();
        for (String l; !(l = next(it)).isEmpty();) {
            String[] p = l.split(",", 2);
            mccThr.put(p[0].trim(), Double.parseDouble(p[1].trim()));
        }

        /* 4 – merchants */
        Map<String, Merchant> merchants = new HashMap<>();
        for (String l; !(l = next(it)).isEmpty();) {
            String[] p = l.split(",", 2);
            String acct = p[0].trim();
            String mcc  = p[1].trim();
            merchants.put(acct, new Merchant(acct, mccThr.get(mcc)));
        }

        /* 5 – minimum tx before evaluation */
        int minTx = Integer.parseInt(next(it));
        FraudRuleEngine engine = new FraudRuleEngine(minTx);

        /* 6 – stream processing */
        Map<String, MerchantStats> stats = new HashMap<>();
        Map<String, Charge>        charges = new HashMap<>();

        while (it.hasNext()) {
            String l = it.next().trim();
            if (l.isEmpty()) continue;

            if (l.startsWith("CHARGE")) {
                String[] p = l.split(",", 5);
                if (p.length != 5) continue;

                String chId   = p[1].trim();
                String acctId = p[2].trim();
                String code   = p[4].trim().replace("\"", "");

                Merchant m = merchants.get(acctId);
                if (m == null) continue;                        // unknown merchant

                boolean isFraud = fraudCodes.contains(code);
                MerchantStats st = stats.computeIfAbsent(acctId, k -> new MerchantStats());
                st.recordCharge(isFraud);

                charges.put(chId, new Charge(chId, acctId, isFraud));

                engine.evaluateOnCharge(m, st);

            } else if (l.startsWith("DISPUTE")) {
                String[] p = l.split(",", 2);
                if (p.length != 2) continue;

                Charge ch = charges.get(p[1].trim());
                if (ch == null || ch.disputed || !ch.wasFraud) continue;  // nothing to undo

                ch.disputed = true;
                MerchantStats st = stats.get(ch.acct);
                if (st == null) continue;                 // should not happen
                st.cancelFraud();

                Merchant m = merchants.get(ch.acct);
                engine.evaluateOnDispute(m, st);
            }
        }

        /* output */
        List<String> flagged = new ArrayList<>();
        for (var e : stats.entrySet())
            if (e.getValue().flagged) flagged.add(e.getKey());

        Collections.sort(flagged);
        System.out.println(String.join(",", flagged));


        // map.entrySet().removeIf(e -> e.getValue() == 0);

    }

    /* ─────────────────────── helpers ─────────────────────── */

    private static List<String> readAllStdin() throws IOException {
        List<String> ls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            for (String ln; (ln = br.readLine()) != null;) ls.add(ln);
        }
        return ls;
    }

    private static String next(Iterator<String> it) {
        while (it.hasNext()) return it.next().trim();
        throw new NoSuchElementException("Unexpected end of input");
    }

    private static Set<String> csvToSet(String csv) {
        Set<String> s = new HashSet<>();
        for (String tok : csv.split(",")) s.add(tok.replace("\"", "").trim());
        return s;
    }
}
