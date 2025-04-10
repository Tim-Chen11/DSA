class Solution {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);  // sort in ascending order
        int n = citations.length;

        for (int i = 0; i < n; i++) {
            int h = n - i;  // number of papers with at least citations[i] citations
            if (citations[i] >= h) {
                return h;
            }
        }
        return 0;
    }
}