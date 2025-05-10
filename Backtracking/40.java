class Solution { 
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> solutions = new ArrayList<>();
        List<Integer> solution = new ArrayList<>();
        backtracing(candidates, target, solution, solutions, 0, 0);
        return solutions;
    }

    public void backtracing(int[] candidates, int target, List<Integer> solution, List<List<Integer>> solutions, int sum, int startIndex) {
        if (sum == target) {
            solutions.add(new ArrayList<>(solution)); // Deep copy
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            if (sum + candidates[i] > target) break; // Prune
            if (i > startIndex && candidates[i] == candidates[i - 1]) continue; // Skip duplicates

            solution.add(candidates[i]);
            backtracing(candidates, target, solution, solutions, sum + candidates[i], i + 1);
            solution.remove(solution.size() - 1); // Backtrack
        }
    }
}
