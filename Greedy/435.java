class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;

        // 1. Sort by end time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 1; // we can always include the first interval
        int end = intervals[0][1]; // the end of the last non-overlapping interval

        // 2. Iterate from the second interval
        for (int i = 1; i < intervals.length; i++) {
            // 3. If the current interval doesn't overlap
            if (intervals[i][0] >= end) {
                count++;
                end = intervals[i][1]; // update the end to current's end
            }
            // else, it's overlapping — so we skip it (implicitly remove it)
        }

        return intervals.length - count;
    }
}
