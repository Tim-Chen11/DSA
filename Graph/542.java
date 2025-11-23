class Solution {
public:
    vector<vector<int>> updateMatrix(vector<vector<int>>& mat) {
        int n = mat.size();
        int m = mat[0].size();
        queue<pair<int, int>> q;

        // Step 1: initialize distances
        //  - 0 stays 0
        //  - 1 becomes a big number (infinity)
        const int INF = 1e9;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 0) {
                    q.push({i, j});      // multi-source BFS start
                } else {
                    mat[i][j] = INF;     // unknown distance yet
                }
            }
        }

        int dirs[4][2] = {{1,0},{-1,0},{0,1},{0,-1}};

        // Step 2: BFS
        while (!q.empty()) {
            auto [r, c] = q.front();
            q.pop();

            for (auto& d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;

                // If we can relax (improve) the neighbor's distance
                if (mat[nr][nc] > mat[r][c] + 1) {
                    mat[nr][nc] = mat[r][c] + 1;
                    q.push({nr, nc});
                }
            }
        }

        return mat;
    }
};
