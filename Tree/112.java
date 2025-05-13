class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return hasPathSumHelper(root, targetSum, 0);
    }

    private boolean hasPathSumHelper(TreeNode root, int targetSum, int currentSum) {
        if (root == null) {
            return false;
        }

        currentSum += root.val;

        if (root.left == null && root.right == null) {
            return currentSum == targetSum;
        }

        return hasPathSumHelper(root.left, targetSum, currentSum) || hasPathSumHelper(root.right, targetSum, currentSum);
    }
}
