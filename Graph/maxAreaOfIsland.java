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

    static final int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int result = 0;
    static int count = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = scanner.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && map[i][j] == 1) {
                    count = 0;
                    bfs(map, visited, i, j);
                    result = Math.max(count, result);

                }
            }
        }
        System.out.println(result);
    }

    static void bfs(int[][] map, boolean[][] visited, int x, int y){
        Deque<int[]> deque = new ArrayDeque<>();
        deque.addLast(new int[]{x,y});
        visited[x][y] = true;

        while(!deque.isEmpty()){
            int[] cur = deque.pollFirst();

            for(int i=0;i<4;i++){
                int nextX = cur[0]+dir[i][0];
                int nextY = cur[1]+dir[i][1];

                if(nextX<0||nextX>map.length-1||nextY<0||nextY>map[i].length-1){
                    continue;
                }

                if(!visited[nextX][nextY] && map[nextX][nextY]){
                    visited[nextX][nextY] = true;
                    count++;
                    deque.addLast(int[]{nextX,nextY});
                }
            }
            
        }

    }
}

