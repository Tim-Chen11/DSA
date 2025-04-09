class Solution {
    public int fib(int n) {
        int prev0 = 0, prev1 = 1;
        int dp = 0;
        for(int i=2;i<=n;i++){
            dp = prev0 + prev1;
            
            prev0 = prev1;
            prev1 = dp;
        }
        return dp;
        
    }
}