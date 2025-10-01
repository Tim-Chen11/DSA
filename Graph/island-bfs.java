import java.util.*;

public class Main {
    public static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};//下右上左逆时针遍历

    public static void bfs(int[][] grid, boolean[][] visited, int x, int y) {
        Queue<pair> queue = new LinkedList<pair>();
        queue.add(new pair(x, y));
        visited[x][y] = true;
        while(!queue.isEmpty()){
            int curX = queue.peek().first;
            int curY = queue.poll().second;
            for(int i=0;i<4;i++){
                int nextX = curX+dir[i][0];
                int nextY = curY+dir[i][1];

                if(nextX<0 ||nextX>grid.length-1||nextY<0||nextY>grid.length-1){
                    continue;
                }

                if(grid[nextX][nextY]==1 && !visited[nextX][nextY]){
                    visited[nextX][nextY] = true;
                    queue.add(new pair(nextX, nextY));
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][] grid = new int[m][n];
        boolean[][] visited = new boolean[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    ans++;
                    bfs(grid, visited, i, j);
                }
            }
        }
        System.out.println(ans);
    }
}