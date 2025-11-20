class Solution {
    public long countSubarrays(int[] nums, long k) {
        long ans = 0;
        long windowSum = 0;
        int n = nums.length;
        int left = 0;

        for (int right = 0; right < n; right++) {
            windowSum += nums[right];

            // 确保当前窗口 score < k（有的题是 < k，有的题是 <= k，按题意改）
            while (left <= right && windowSum * (right - left + 1) >= k) {
                windowSum -= nums[left];
                left++;
            }

            // 当前 right 下，以 right 结尾的合法子数组个数
            ans += (right - left + 1);
        }

        return ans;
    }
}
