import java.util.*;

class Solution {
    private int[] prefix;
    private int total;
    private Random rand;

    public Solution(int[] w) {
        int n = w.length;
        prefix = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += w[i];
            prefix[i] = sum;
        }
        total = sum;
        rand = new Random();
    }

    public int pickIndex() {
        // random int in [1, total]
        int x = rand.nextInt(total) + 1;

        // binary search: first index with prefix[i] >= x
        int l = 0, r = prefix.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (prefix[mid] >= x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}
