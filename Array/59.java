class Solution {
    public int[][] generateMatrix(int n) {
        int num[][] = new int[n][n];
        int startX = 0, startY = 0;
        int offset = 1;
        int count = 1;
        int loop = 1;
        int i,j;

        while(loop <= n/2){
            for(j = startY; j< n-offset;j++){
                nums[startX][j] = count++;
            }

            for(i = startX; i<n-offset;i++){
                nums[i][j] = count++;
            }

            for(;j>startX;j--){
                nums[i][j] = count++;
            }

            for (; i > startX; i--) {
                nums[i][j] = count++;
            }

            startX++;
            startY++;
            offset++;
            loop++;
        }

        if (n % 2 == 1) { // n 为奇数时，单独处理矩阵中心的值
            nums[startX][startY] = count;
        }
        return nums;
    }
}

