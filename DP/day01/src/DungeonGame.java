public class DungeonGame {

    public static int minInitialHealth(int[][] map) {
        int[][][] memo = new int[map.length][map[0].length][2]; //[movesRight][movesDown][currHealth, worstEver]
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                memo[i][j][0] = Integer.MAX_VALUE;
                memo[i][j][1] = Integer.MAX_VALUE;
            }
        }
        int initHealth = makeChoice(map, 0, 0, 0, 0, memo);
        return Math.abs(initHealth) + 1;
    }

    private static int makeChoice(int[][] map, int movesRight, int movesDown, int currHealthBalance, int worstEver, int[][][] memo) {
        //add room to health
        currHealthBalance += map[movesRight][movesDown];
        if (currHealthBalance < worstEver) worstEver = currHealthBalance;

        //check if base case -- we've reached Sidd
        if (movesRight >= memo.length - 1 && movesDown >= memo[0].length - 1) return worstEver;

        //check if already memoized a better scenario
        if (memo[movesRight][movesDown][0] != Integer.MAX_VALUE && memo[movesRight][movesDown][0] >= currHealthBalance) return memo[movesRight][movesDown][1];

        //make a choice -- down or right
        int worstRight = Integer.MIN_VALUE;
        if (movesRight < map.length - 1) worstRight = makeChoice(map, movesRight + 1, movesDown, currHealthBalance, worstEver, memo);
        int worstDown = Integer.MIN_VALUE;
        if (movesDown < map[0].length - 1) worstDown = makeChoice(map, movesRight, movesDown + 1, currHealthBalance, worstEver, memo);

        memo[movesRight][movesDown][0] = currHealthBalance;
        memo[movesRight][movesDown][1] = Math.max(worstRight, worstDown);

        return memo[movesRight][movesDown][1];
    }
}




//    private static int tabulateHealth(int[][] map) {
//        //get map boundaries
//        int width = map.length;
//        int height = map[0].length;
//
//        //create N x M matrix with maximum health balances so far
//        int[][] dipMap = new int[width][height];
//        for (int x = 0; x < width; x++) { //start in top right corner
//            for (int y = 0; y < height; y++) {
//                int currBoxVal = map[x][y];
//                int boxBelowVal = Integer.MAX_VALUE;
//                int boxLeftVal = Integer.MAX_VALUE;
//                if (y > 0) boxBelowVal = dipMap[x][y - 1];
//                if (x > 0) boxBelowVal = dipMap[x - 1][y];
//                if (boxBelowVal == Integer.MAX_VALUE && boxLeftVal == Integer.MAX_VALUE) boxBelowVal = 0; //if we're in the bottom right corner, cost is 0
//                int lowerCost = Math.min(boxBelowVal, boxLeftVal);
//                dipMap[x][y] = currBoxVal + lowerCost;
//            }
//        }
//
//        //go through matrix and replace current health balances with worst dip seen so far
//        for (int x = 0; x < width; x++) { //start in top right corner
//            for (int y = 0; y < height; y++) {
//                int currBoxVal = map[x][y];
//                int boxBelowVal = Integer.MIN_VALUE;
//                int boxLeftVal = Integer.MIN_VALUE;
//                if (y > 0) boxBelowVal = dipMap[x][y - 1];
//                if (x > 0) boxBelowVal = dipMap[x - 1][y];
//                if (boxBelowVal == Integer.MIN_VALUE && boxLeftVal == Integer.MIN_VALUE) boxBelowVal = 0; //if we're in the bottom right corner, worst health so far is 0
//                int highestDip = Math.max(boxBelowVal, boxLeftVal);
//                if (currBoxVal < highestDip) dipMap[x][y] = currBoxVal;
//                else dipMap[x][y] = highestDip;
//            }
//        }
//
//        return Math.abs(dipMap[0][0]) + 1;
//    }