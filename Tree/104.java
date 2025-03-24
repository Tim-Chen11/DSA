class Solution {
    int maxnum = 0;

    public int maxDepth(TreeNode root) {
        ans(root,0);
        return maxnum;
    }
    
    void ans(TreeNode tr,int tmp){
        if(tr==null) return;
        tmp++;
        maxnum = maxnum<tmp?tmp:maxnum;
        ans(tr.left,tmp);
        ans(tr.right,tmp);
        tmp--;
    }
}