import java.util.ArrayList;
import java.util.HashMap;

public class BillsNeeded {

    public int billsNeeded(int N, int[] billDenominations) {
        return pickBill(N, 0, 0, billDenominations, new HashMap(), new HashMap<>());
    }

    private int pickBill(int N, int currSum, int numCurrBills, int[] billDenomination, HashMap<Integer, Integer> sumMap, HashMap<Integer, Integer> billNumMap) {

        //base cases 1/2 -- we hit the number or passed it
        if (currSum == N) {
            return numCurrBills;
        } else if (currSum > N) {
            return Integer.MAX_VALUE;
        }

        int minNumBills = Integer.MAX_VALUE;
        int pathNumBills;
        //iterate through bills
        for (int bill: billDenomination) {
            int key = currSum + bill;
            if (sumMap.get(key) != null && sumMap.get(key) <= numCurrBills) { //we've already tried this path and it's faster
                pathNumBills = billNumMap.get(key);
            } else { //first time down this path or we're off to a faster start
                pathNumBills = pickBill(N, currSum + bill, numCurrBills + 1, billDenomination, sumMap, billNumMap);
                sumMap.put(key, numCurrBills); //put it in the map
                billNumMap.put(key, pathNumBills);
            }
            if (pathNumBills < minNumBills) {
                minNumBills = pathNumBills;
            }
        }

        return minNumBills;
    }


}