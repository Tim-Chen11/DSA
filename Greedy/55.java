class Solution {
    public boolean canJump(int[] nums) {
        int cover = 0;
        if(nums.length == 1) return true;

        for(int i=0;i<=cover;i++){
            cover = Math.max(cover, i+nums[i]);
            if(cover >=nums.length-1){
                return true;
            }
        }

        return false;

        
    }
}