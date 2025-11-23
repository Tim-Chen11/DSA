// two stack, find the max number of elements sum that are smaller than the value: 3214, 1527, max=6. 
// Answer is 321 from first stack or 312 where the 1 from second stack. U can only pop the front element . 
// How to do this question

#include <vector>;
using namespace std;

int maxCount(vector<int>& A, vector<int>& B, int maxSum) {
    long long sum = 0;
    int i = 0, j = 0;
    int n = A.size(), m = B.size();

    // Take as many as possible from A first
    while (i < n && sum + A[i] <= maxSum) {
        sum += A[i];
        i++;
    }

    int best = i; // currently only from A

    // Now start taking from B, and backtrack A when needed
    while (j < m) {
        sum += B[j];
        j++;

        while (sum > maxSum && i > 0) {
            i--;
            sum -= A[i];
        }

        if (sum <= maxSum) {
            best = max(best, i + j);
        }
    }

    return best;
}
