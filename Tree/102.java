/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> sol = new ArrayList<>();
    

    public List<List<Integer>> levelOrder(TreeNode root) {
        check(root, 0);

        return sol;
    }

    public void check(TreeNode root, Integer deep){
        if(root == null) return;

        deep++;

        if(sol.size() < deep){
            List<Integer> item = new ArrayList<Integer>();
            sol.add(item);
        }

        sol.get(deep-1).add(root.val);

        check(root.left, deep);
        check(root.right, deep);

    }
}