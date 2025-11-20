class Solution {
    public long minimumTime(int[] time, int totalTrips) {
        long left = 0;

        // find fastest time[i]
        long fastest = Long.MAX_VALUE;
        for (int t : time) {
            fastest = Math.min(fastest, t);
        }

        // upper bound: fastest * totalTrips
        long right = fastest * totalTrips;

        while (left < right) {
            long mid = left + (right - left) / 2;

            long served = 0;
            for (int t : time) {
                served += mid / t;    // <-- correct formula
                if (served >= totalTrips) break;
            }

            if (served >= totalTrips) {
                right = mid;         // try smaller time
            } else {
                left = mid + 1;      // need more time
            }
        }

        return left;
    }
}
