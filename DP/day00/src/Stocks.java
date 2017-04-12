import java.util.ArrayList;

public class Stocks {

    public static int maxProfit(int[] prices) {
        //make a list of the difference in stock value between each day and the next
        ArrayList<Integer> differences = new ArrayList();
        for (int i = 0; i < prices.length - 1; i++) {
            differences.add(prices[i+1] - prices[i]);
        }

        //keep track of the sums of positive difference streaks
        int maxProfit = 0;
        int currProfit = 0;
        Boolean streak = false; //could just use currProfit to check if we're on a streak (is it non-0?) but this makes it more readable
        for (int j = 0; j < differences.size(); j++) {
            if (differences.get(j) > 0) {
                if (!streak) streak = true;
                currProfit += differences.get(j);
            } else if (differences.get(j) < 0 && streak == true) {
                if (currProfit > maxProfit) maxProfit = currProfit;
                currProfit = 0;
                streak = false;
            } //if the difference is 0, it doesn't matter if we're on a streak or not
        }
        if (currProfit > maxProfit) {
            maxProfit = currProfit;
        }

        return maxProfit;
    }

    public int maxProfitWithK(int[] prices, int k) {
        // TODO: Optional
        return -1;
    }

}
