import java.util.*;

public class Main {

    // ================= Part 1: In-place expand =================
    /**
     * Expand (count, value) pairs in-place.
     * @param a       array containing pairs [c0,v0,c1,v1,...]
     * @param pairsLen number of valid elements (must be even)
     * @return new expanded length
     */
    public static int expandInPlace(int[] a, int pairsLen) {
        if ((pairsLen & 1) == 1) throw new IllegalArgumentException("pairsLen must be even");

        // 1) Calculate expanded length
        int newLen = 0;
        for (int i = 0; i < pairsLen; i += 2) {
            int count = a[i];
            newLen += count;
        }
        if (newLen > a.length) {
            throw new IllegalStateException("Not enough capacity for expansion");
        }

        // 2) Write backwards
        int write = newLen - 1;
        for (int i = pairsLen - 1; i >= 1; i -= 2) {
            int val = a[i];
            int count = a[i - 1];
            for (int k = 0; k < count; k++) {
                a[write--] = val;
            }
        }
        return newLen;
    }

    // ================= Part 2: Iterator =================
    public static class RLEIterator implements Iterator<Integer> {
        private final int[] a;
        private int i = 0;      // next pair index
        private int rem = 0;    // remaining count for current value
        private int val = 0;

        public RLEIterator(int[] a) {
            this.a = a;
            advance();
        }

        private void advance() {
            while (rem == 0 && i + 1 < a.length) {
                rem = a[i];
                val = a[i + 1];
                i += 2;
                if (rem > 0) break;
            }
        }

        @Override
        public boolean hasNext() {
            if (rem > 0) return true;
            int j = i;
            while (j + 1 < a.length) {
                if (a[j] > 0) return true;
                j += 2;
            }
            return false;
        }

        @Override
        public Integer next() {
            if (rem == 0) advance();
            if (rem == 0) throw new NoSuchElementException();
            rem--;
            int out = val;
            if (rem == 0) advance();
            return out;
        }
    }

    // ================= Demo =================
    public static void main(String[] args) {
        // --- Part 1 demo ---
        int[] arr = new int[10]; // enough capacity
        int[] init = {4,3,2,1};
        System.arraycopy(init, 0, arr, 0, init.length);
        int newLen = expandInPlace(arr, init.length);
        System.out.println("Expanded in-place: " +
            Arrays.toString(Arrays.copyOf(arr, newLen))); // [3,3,3,3,1,1]

        // --- Part 2 demo ---
        int[] a2 = {4,3,2,1};
        RLEIterator it = new RLEIterator(a2);
        System.out.print("Iterator output: ");
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}
