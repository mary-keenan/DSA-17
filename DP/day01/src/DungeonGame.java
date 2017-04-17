public class DungeonGame {

    public static int minInitialHealth(int[][] map) {
        int initHealth = tabulateHealth(map);
        return initHealth;
    }

    private static int tabulateHealth(int[][] map) {
        //get map boundaries
        int width = map.length;
        int height = map[0].length;

        //create N x M matrix with maximum health balances so far
        int[][] dipMap = new int[width][height];
        for (int x = 0; x < width; x++) { //start in top right corner
            for (int y = 0; y < height; y++) {
                int currBoxVal = map[x][y];
                int boxBelowVal = Integer.MAX_VALUE;
                int boxLeftVal = Integer.MAX_VALUE;
                if (y > 0) boxBelowVal = dipMap[x][y - 1];
                if (x > 0) boxBelowVal = dipMap[x - 1][y];
                if (boxBelowVal == Integer.MAX_VALUE && boxLeftVal == Integer.MAX_VALUE) boxBelowVal = 0; //if we're in the bottom right corner, cost is 0
                int lowerCost = Math.min(boxBelowVal, boxLeftVal);
                dipMap[x][y] = currBoxVal + lowerCost;
            }
        }

        //go through matrix and replace current health balances with worst dip seen so far
        for (int x = 0; x < width; x++) { //start in top right corner
            for (int y = 0; y < height; y++) {
                int currBoxVal = map[x][y];
                int boxBelowVal = Integer.MIN_VALUE;
                int boxLeftVal = Integer.MIN_VALUE;
                if (y > 0) boxBelowVal = dipMap[x][y - 1];
                if (x > 0) boxBelowVal = dipMap[x - 1][y];
                if (boxBelowVal == Integer.MIN_VALUE && boxLeftVal == Integer.MIN_VALUE) boxBelowVal = 0; //if we're in the bottom right corner, worst health so far is 0
                int highestDip = Math.max(boxBelowVal, boxLeftVal);
                if (currBoxVal < highestDip) dipMap[x][y] = currBoxVal;
                else dipMap[x][y] = highestDip;
            }
        }

        return Math.abs(dipMap[0][0]) + 1;
    }
}
