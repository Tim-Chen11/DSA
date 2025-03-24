class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] solution = new int[nums.length];

        int i=0, j=nums.length-1;
        int index = solution.length-1;
        while(i<=j){
            if(Math.pow(nums[i], 2)>Math.pow(nums[j], 2)){
                solution[index] =Math.pow(nums[i], 2);
                index--;
                i++;
            }else{
                solution[index] = Math.pow(nums[j], 2);
                index--;
                j--;
            }
        }

        return solution;
    }
}