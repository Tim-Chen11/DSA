import java.util.Set;

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[0];
        }

        Set<Integer> solution = new HashSet<>();

        Set<Integer> set1 = new HashSet<>();

        for (int i : nums1) {
            set1.add(i);
        }

        for (int i : nums2) {
            if (set1.contains(i)) {
                solution.add(i);
            }
        }

        int[] arr = new int[solution.size()];
        int j=0;
        for(int i : solution){
            arr[j++] = i;
        }

        return arr;
    }
}