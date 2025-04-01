class Solution {
    /**
     * 递归法，相比求MaxDepth要复杂点
     * 因为最小深度是从根节点到最近**叶子节点**的最短路径上的节点数量
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        if (root.left == null) {
            return rightDepth + 1;
        }
        if (root.right == null) {
            return leftDepth + 1;
        }
        // 左右结点都不为null
        return Math.min(leftDepth, rightDepth) + 1;
    }
    
}

class Solution {
    /**
     * 递归法（思路来自二叉树最大深度的递归法）
     * 该题求最小深度，最小深度为根节点到叶子节点的深度，所以在迭代到每个叶子节点时更新最小值。
     */
    int depth = 0;
    // 定义最小深度，初始化最大值
    int minDepth = Integer.MAX_VALUE;
    public int minDepth(TreeNode root) {
        dep(root);
        return minDepth == Integer.MAX_VALUE ? 0 : minDepth;
    }
    void dep(TreeNode root){
        if(root == null) return ;
        // 递归开始，深度增加
        depth++;
        dep(root.left);
        dep(root.right);
        // 该位置表示递归到叶子节点了，需要更新最小深度minDepth
        if(root.left == null && root.right == null)
            minDepth = Math.min(minDepth , depth);
        // 递归结束，深度减小
        depth--;
    }
}

class Solution {
    /**
      * 迭代法，层序遍历
      */
     public int minDepth(TreeNode root) {
         if (root == null) {
             return 0;
         }
         Deque<TreeNode> deque = new LinkedList<>();
         deque.offer(root);
         int depth = 0;
         while (!deque.isEmpty()) {
             int size = deque.size();
             depth++;
             for (int i = 0; i < size; i++) {
                 TreeNode poll = deque.poll();
                 if (poll.left == null && poll.right == null) {
                     // 是叶子结点，直接返回depth，因为从上往下遍历，所以该值就是最小值
                     return depth;
                 }
                 if (poll.left != null) {
                     deque.offer(poll.left);
                 }
                 if (poll.right != null) {
                     deque.offer(poll.right);
                 }
             }
         }
         return depth;
     }
 }