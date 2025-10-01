import java.util.*;

// dfs
public class Main{
    static int count = 0;
    static int result = 0;
    static int[][] dir = {{0,0},{0,1},{1,0},{1,1}};

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j]=scanner.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(!visited[i][j] && map[i][j] == 1){
                    count = 0;
                    dfs(map, visited, i, j);
                    result = Math.max(count, result);
                }
            }
        }
        System.out.println(result);
    }

    public static void dfs(int[][] map, boolean[][] visited, int x, int y){
        for(int i=0;i<4;i++){
            int nextX = x+dir[i][0];
            int nextY = y+dir[i][1];

            if(nextX<0||nextX>map.length-1||nextY<0||nextY>map[i].length-1){
                continue;
            }

            if(map[nextX][nextY]==1 && !visited[nextX][nextY]){
                count++;
                dfs(map, visited, nextX, nextY);
            }
        }
    }
}

public class Main {

    static final int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // rows
        int m = scanner.nextInt(); // cols
        int[][] map = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                map[i][j] = scanner.nextInt();

        boolean[][] visited = new boolean[n][m];
        int result = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && map[i][j] == 1) {
                    int size = bfs(map, visited, i, j);
                    result = Math.max(result, size);
                }
            }
        }
        System.out.println(result);
    }

    static int bfs(int[][] map, boolean[][] visited, int sx, int sy) {
        int rows = map.length, cols = map[0].length;
        Deque<int[]> dq = new ArrayDeque<>();
        dq.addLast(new int[]{sx, sy});
        visited[sx][sy] = true;

        int size = 1; // count the starting cell

        while (!dq.isEmpty()) {
            int[] cur = dq.pollFirst();
            int x = cur[0], y = cur[1];

            for (int[] d : dir) {
                int nx = x + d[0], ny = y + d[1];
                if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) continue;

                if (!visited[nx][ny] && map[nx][ny] == 1) {
                    visited[nx][ny] = true;
                    size++;
                    dq.addLast(new int[]{nx, ny});
                }
            }
        }
        return size;
    }
}

