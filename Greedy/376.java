class Solution {
    public int wiggleMaxLength(int[] nums) {
        int ans = 1;
        int prevDiff = 0;
        int curDiff = 0;

        for(int i=0;i<nums.length;i++){
            curDiff = nums[i]-nums[i-1];

            if((curDiff>0&&prevDiff<=0) || (curDiff<0&&prevDiff>=0)){
                ans++;
                prevDiff = curDiff;
            }
        }

        return ans;
    }
}