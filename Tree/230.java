class Solution {
    private int k, ans;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode n) {
        if (n == null) return;
        dfs(n.left);
        if (--k == 0) { ans = n.val; return; }
        dfs(n.right);
    }
}
