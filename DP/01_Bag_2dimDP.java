// 🎒 Problem: 0-1 Knapsack Basic Theory
// 🧾 Given:
// The maximum weight capacity of the knapsack is 4.

// You have 3 items, each with a given weight and value:
// | Item   | Weight | Value |
// | ------ | ------ | ----- |
// | Item 0 | 1      | 15    |
// | Item 1 | 3      | 20    |
// | Item 2 | 4      | 30    |


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int bagweight = scanner.nextInt();

        int[] weight = new int[n];
        int[] value = new int[n];

        for (int i = 0; i < n; ++i) {
            weight[i] = scanner.nextInt();
        }
        for (int j = 0; j < n; ++j) {
            value[j] = scanner.nextInt();
        }

        int[][] dp = new int[n][bagweight + 1];

        for (int j = weight[0]; j <= bagweight; j++) {
            dp[0][j] = value[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= bagweight; j++) {
                if (j < weight[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }

        System.out.println(dp[n - 1][bagweight]);
    }
}
