class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;  // update buy point
            } else {
                int profit = price - minPrice;  // sell at current price
                maxProfit = Math.max(maxProfit, profit);  // update max profit
            }
        }

        return maxProfit;
    }
}
