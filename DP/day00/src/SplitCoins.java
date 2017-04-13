import java.util.*;

public class SplitCoins {

    public int splitCoins(int[] coins) { //TODO: took away the static part -- it wouldn't let me call pickCoin
        ArrayList coinsLeft = new ArrayList(); //TODO: doesn't seem to include first element of testcase FML
        for (int coin: coins) {
            coinsLeft.add(coin);
        }
        ArrayList piles = new ArrayList();
        piles.add(new ArrayList<>());
        piles.add(new ArrayList<>());
        return pickCoin(coinsLeft, piles, new HashMap());
    }

    private int pickCoin(ArrayList<Integer> coinsLeft, ArrayList<ArrayList<Integer>> piles, HashMap<ArrayList<ArrayList<Integer>>, Integer> pilesOutcomeMap) {
        int minDiff = Integer.MAX_VALUE;
        //base case -- no coins left to allocate -- return current difference
        if (coinsLeft.size() == 0) {
            int sum1 = 0;
            int sum2 = 0;
            for (int coin: piles.get(0)) {
                sum1 += coin;
            }
            for (int coin: piles.get(1)) {
                sum2+= coin;
            }
            return Math.abs(sum1 - sum2);
        }

        //iterate through coins -- look at all possibilities
        int diff = 0;
        for (int coin: new ArrayList<>(coinsLeft)) { //TODO: put in for loop? Becomes difficult to differentiate between piles
            for (int pileNum = 0; pileNum < piles.size(); pileNum++) {
                ArrayList pile1 = new ArrayList(piles.get(0));
                ArrayList pile2 = new ArrayList(piles.get(1));

                if (pileNum == 0) pile1.add(coin); //TODO: check
                else pile2.add(coin);

                coinsLeft.remove((Object) coin); //cast as an object so it knows it's not an index

                //check to see if already memoized
                ArrayList key = putPilesInKey(pile1, pile2);
                if (pilesOutcomeMap.get(key) != null && pilesOutcomeMap.get(key) < minDiff) { //already exists and is lower
                    diff = pilesOutcomeMap.get(key); //update minDiff value with new, lower value
                } else if (pilesOutcomeMap.get(key) == null){
                    diff = pickCoin(coinsLeft, key, pilesOutcomeMap);
                    pilesOutcomeMap.put(new ArrayList<>(key), diff);
                }
                if (diff < minDiff) minDiff = diff;

                if (pileNum == 0) pile1.remove((Object) coin);
                else pile2.remove((Object) coin);

                coinsLeft.add(coin);
            }
        }

        return minDiff;
    }

    private ArrayList putPilesInKey(ArrayList pile1, ArrayList pile2) {
        ArrayList pilesKey = new ArrayList();
        Collections.sort(pile1); //TODO: probably bad for runtime -- need to be able to compare to dict keys regardless of order
        Collections.sort(pile2);
        pilesKey.add(pile2);
        pilesKey.add(0, pile1);
        return pilesKey;
    }

}
