import java.util.Scanner;

public class Main{
    public static int[][] dir = {{0,1},{1,1},{1,0},{0,0}};

    public static void dfs(boolean[][] visited, int x, int y, int[][] grid){
        for(int i=0;i<4;i++){
            int nextX = x+dir[i][0];
            int nextY = y+dir[i][1];

            if(nextX<0 || nextX>grid.length-1 || nextY<0 ||nextY>grid[i].length-1){
                continue;
            }

            if(grid[nextX][nextY] == 1 &&visited[nextX][nextY]==false){
                visited[nextX][nextY] = true;
                dfs(visited, nextX, nextY, grid);
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j]=sc.nextInt();
            }
        }

        boolean[][]visited =new boolean[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(!visited[i][j]&&grid[i][j]==1)
                {
                    ans++;
                    visited[i][j]=true;
                    dfs(visited,i,j,grid);
                }
            }
        }
        System.out.println(ans);
    }
}