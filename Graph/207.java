class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> g = new ArrayList<>();
        for(int i=0;i<numCourses;i++){
            g.add(new ArrayList<>());
        }

        for(int[] p : prerequisites){
            int a = p[0], b = p[1];
            g.get(b).add(a);
        }

        int[] state = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            if(state[i] == 0 && !dfs(i, g, state)) return false;
        }
    }

    private boolean dfs(int u, List<List<Integer>> g, int[] st){
        st[u] = 1;
        for(int v : g.get(u)){
            if(st[v]==1) return false;
            if(st[v]==0 && !dfs(v,g,st)) return false;
        }

        st[u] = 2;
        return true;
    }
}