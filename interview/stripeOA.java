import java.io.*;
import java.util.*;

/**
 * Stripe “Catch Me If You Can” – Part 1
 *
 * Reads six logical input sections from STDIN (see README)
 * and prints a lexicographically-sorted, comma-separated list
 * of fraudulent merchants (their account-ids) to STDOUT.
 *
 * Design notes
 * ------------
 * 1.  Domain objects
 *     • Merchant – immutable id + MCC + threshold
 *     • MerchantStats – running totals (#tx, #fraud) + flag
 * 2.  Services
 *     • FraudRuleService – single place for fraud decision logic
 * 3.  Parsing
 *     • Read all lines once → walk through them block-by-block
 *     • Empty lines act as block separators
 */
public final class Main {

    /* ---------- Domain ---------- */

    private static final class Merchant {
        final String id;
        final String mcc;
        final int fraudThreshold;

        Merchant(String id, String mcc, int threshold) {
            this.id = id;
            this.mcc = mcc;
            this.fraudThreshold = threshold;
        }
    }

    private static final class MerchantStats {
        int totalTx = 0;
        int fraudTx = 0;
        boolean alreadyFlagged = false;

        void record(boolean isFraud) {
            totalTx++;
            if (isFraud) fraudTx++;
        }
    }

    /* ---------- Fraud rule (business logic) ---------- */

    private static final class FraudRuleService {
        private final int minTxBeforeEvaluation;

        FraudRuleService(int minTxBeforeEvaluation) {
            this.minTxBeforeEvaluation = minTxBeforeEvaluation;
        }

        boolean shouldFlag(Merchant merchant, MerchantStats stats) {
            return !stats.alreadyFlagged                              // not flagged before
                   && stats.totalTx >= minTxBeforeEvaluation          // seen enough traffic
                   && stats.fraudTx >= merchant.fraudThreshold;       // crossed threshold
        }
    }

    /* ---------- Main workflow ---------- */

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines();
        Iterator<String> it = lines.iterator();

        /* 1. Non-fraud codes (not actually needed but kept for completeness) */
        Set<String> nonFraudCodes = parseCodes(nextNonEmpty(it));

        /* 2. Fraud codes */
        Set<String> fraudCodes = parseCodes(nextNonEmpty(it));

        /* 3. MCC → threshold table */
        Map<String, Integer> mccThresholds = new HashMap<>();
        for (String line; !(line = nextNonEmpty(it)).isEmpty(); ) {
            String[] parts = line.split(",", 2);
            mccThresholds.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }

        /* 4. Merchant table */
        Map<String, Merchant> merchants = new HashMap<>();
        for (String line; !(line = nextNonEmpty(it)).isEmpty(); ) {
            String[] parts = line.split(",", 2);
            String accountId = parts[0].trim();
            String mcc = parts[1].trim();
            Integer threshold = mccThresholds.get(mcc);
            if (threshold == null) {
                throw new IllegalArgumentException("Unknown MCC: " + mcc);
            }
            merchants.put(accountId, new Merchant(accountId, mcc, threshold));
        }

        /* 5. Minimum #tx before we start evaluating a merchant */
        int minTx = Integer.parseInt(nextNonEmpty(it));

        FraudRuleService ruleService = new FraudRuleService(minTx);

        /* 6. Process charges */
        Map<String, MerchantStats> statsByMerchant = new HashMap<>();
        while (it.hasNext()) {
            String line = it.next().trim();
            if (line.isEmpty()) continue;
            // Expected format: CHARGE,charge_id,account_id,amount,code
            String[] parts = line.split(",", 5);
            if (parts.length != 5 || !"CHARGE".equals(parts[0])) continue; // ignore malformed

            String accountId = parts[2].trim();
            String code      = parts[4].trim().replace("\"", "");

            Merchant merchant = merchants.get(accountId);
            if (merchant == null) continue;  // ignore charges from unknown merchants

            MerchantStats stats = statsByMerchant.computeIfAbsent(accountId, k -> new MerchantStats());
            boolean isFraudCode = fraudCodes.contains(code);
            stats.record(isFraudCode);

            if (ruleService.shouldFlag(merchant, stats)) {
                stats.alreadyFlagged = true; // mark only once
            }
        }

        /* 7. Collect & print flagged merchants */
        List<String> fraudulentMerchants = new ArrayList<>();
        for (Map.Entry<String, MerchantStats> e : statsByMerchant.entrySet()) {
            if (e.getValue().alreadyFlagged) fraudulentMerchants.add(e.getKey());
        }
        Collections.sort(fraudulentMerchants);
        System.out.println(String.join(",", fraudulentMerchants));
    }

    /* ---------- Helpers ---------- */

    private static List<String> readAllLines() throws IOException {
        List<String> out = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            for (String line; (line = br.readLine()) != null; ) out.add(line);
        }
        return out;
    }

    /** Returns the next line that is not null (iterator hasNext) – may be empty string. */
    private static String nextNonEmpty(Iterator<String> it) {
        while (it.hasNext()) {
            String l = it.next();
            if (l != null) return l.trim();
        }
        throw new NoSuchElementException("Unexpected end of input");
    }

    private static Set<String> parseCodes(String csv) {
        Set<String> s = new HashSet<>();
        for (String token : csv.split(",")) {
            s.add(token.replace("\"", "").trim());
        }
        return s;
    }
}
