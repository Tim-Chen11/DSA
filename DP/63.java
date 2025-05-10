class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];

        boolean block = false;
        for(int r=0;r<dp.length;r++){
            if(obstacleGrid[r][0] == 1){
                block = true;
            }
            dp[r][0] = block?0:1;
        }
        block = false;
        for(int c=0;c<dp[0].length;c++){
            if(obstacleGrid[0][c] == 1){
                block = true;
            }
            dp[0][c] = block?0:1;;
        }


        for(int r=1;r<dp.length;r++){
            for(int c=1;c<dp[0].length;c++){
                if(obstacleGrid[r][c] == 1){
                    dp[r][c] = 0;
                }else{
                    dp[r][c] = dp[r-1][c] + dp[r][c-1];
                }
            }
        }

        return dp[obstacleGrid.length-1][obstacleGrid[0].length-1];
    }
}