class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtracing(result, new ArrayList<>(), candidates, target, 0, 0);
        return result;
    }

    public void backtracing(List<List<Integer>> result, List<Integer> path, int[] candidates, int target, int sum, int startIndex){
        if(sum == target){
            result.add(new ArrayList<>(path));
        }

        for(int i=startIndex; i<candidates.length;i++){
            if(sum + candidates[i] > target){
                break;
            }
            path.add(candidates[i]);
            backtracing(result, path, candidates, target, sum+candidates[i], i);
            path.remove(path.size() - 1);
        }
    }
}