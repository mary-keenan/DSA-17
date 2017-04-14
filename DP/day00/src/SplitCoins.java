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

        return pickCoin(maxSum, 0, 0);
    }

    private int pickCoin(int maxSum, int i, int currSum) {
        int diff;
        int minDiff = Integer.MAX_VALUE;
        //base case -- no coins left to allocate -- return current difference
        if (i == coins.length) {
            diff = Math.abs(currSum * 2 - maxSum);
            return diff;
        }

        //ADD option
        if (memo[i][currSum + coins[i]] != 0) { //already exists
            diff = memo[i][currSum + coins[i]];
        } else { //doesn't exist
            diff = pickCoin(maxSum, i + 1, currSum + coins[i]);
            memo[i + 1][currSum + coins[i]] = diff;
        }

        if (diff < minDiff) minDiff = diff;

        //DON'T ADD option
        if (memo[i][currSum] != 0) { //already exists TODO: what if it equals 0?
            diff = memo[i][currSum];
        } else { //doesn't exist
            diff = pickCoin(maxSum, i + 1, currSum);
            memo[i + 1][currSum] = diff;
        }

        if (diff < minDiff) minDiff = diff;


        return minDiff;
    }
}
