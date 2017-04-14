import java.util.*;

public class SplitCoins {
    int[] coins;
    int[][] memo;

    public int splitCoins(int[] coins) { //TODO: took away the static part -- it wouldn't let me call pickCoin
        int maxSum = 0;
        for (int coin: coins) {
            maxSum += coin;
        }

        this.coins = coins;
        memo = new int[coins.length + 1][maxSum + 1];

        for (int i=0; i<=coins.length; i++)
            Arrays.fill(memo[i], -1);

        return pickCoin(maxSum, 0, 0);
    }

    private int pickCoin(int maxSum, int i, int currSum) {

        //ADD option
        if (memo[i][currSum] != -1) { //already exists
            return memo[i][currSum];
        }

        //base case -- no coins left to allocate -- return current difference
        if (i == coins.length) {
            return Math.abs(currSum * 2 - maxSum);
        }

        int diff1 = pickCoin(maxSum, i + 1, currSum + coins[i]);
        int diff2 = pickCoin(maxSum, i + 1, currSum);

        return memo[i][currSum] = Math.min(diff1, diff2);
    }
}
