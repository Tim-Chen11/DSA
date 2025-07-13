import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int bagWeight = scanner.nextInt();

        int[] weight = new int[N];
        int[] value = new int[N];
        for (int i = 0; i < N; i++) {
            weight[i] = scanner.nextInt();
            value[i] = scanner.nextInt();
        }

        int[] dp = new int[bagWeight + 1];

        for (int j = 0; j <= bagWeight; j++) { // 遍历背包容量
            for (int i = 0; i < weight.length; i++) { // 遍历物品
                if (j >= weight[i]) {
                    dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
                }
            }
        }

        System.out.println(dp[bagWeight]);
        scanner.close();
    }
}
