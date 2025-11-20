class Solution {
    public int splitArray(int[] nums, int k) {
        int max = 0;
        long sum = 0;

        for (int x : nums) {
            max = Math.max(max, x);
            sum += x;
        }

        // Search space: [max, sum]
        long left = max;
        long right = sum;

        while (left < right) {
            long mid = left + (right - left) / 2;

            if (canSplit(nums, k, mid)) {
                // mid is feasible, try smaller
                right = mid;
            } else {
                // mid too small
                left = mid + 1;
            }
        }

        return (int) left;
    }

    // Check if we can split nums into <= k subarrays
    // such that each subarray sum <= limit
    private boolean canSplit(int[] nums, int k, long limit) {
        int pieces = 1;
        long curSum = 0;

        for (int x : nums) {
            if (curSum + x <= limit) {
                curSum += x;
            } else {
                // start a new subarray
                pieces++;
                curSum = x;

                if (pieces > k) return false;
            }
        }

        return true;
    }
}
