import java.util.Set;

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for(int i : nums){
            set.add(i);
        }

        int ans = 0;
        for(int i : set){
            if(!set.contains(i-1)){
                int num = 1;
                while(set.contains(num+i)){
                    num++;
                }
                ans = Math.max(ans, num);
            }
        }

        return ans;
    }
}