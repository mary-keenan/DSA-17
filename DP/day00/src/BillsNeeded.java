import java.util.ArrayList;
import java.util.HashMap;

public class BillsNeeded {

    public int billsNeeded(int N, int[] billDenominations) {
        return pickBill(N, 0, 0, billDenominations, new HashMap());
    }

    private int pickBill(int N, int currSum, int numCurrBills, int[] billDenomination, HashMap sumMap) {

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
            if (sumMap.get(currSum + bill) != null) { //we've already tried this path
                ArrayList key = new ArrayList();
                key.add(currSum + bill, numCurrBills);
                pathNumBills = (int) sumMap.get(key);
            } else { //first time down this path
                pathNumBills = pickBill(N, currSum + bill, numCurrBills + 1, billDenomination, sumMap);
                ArrayList key = new ArrayList();
                key.add(currSum + bill);
                key.add(numCurrBills);
                sumMap.put(key, pathNumBills); //put it in the map
            }
            if (pathNumBills < minNumBills) {
                minNumBills = pathNumBills;
            }
        }

        return minNumBills;
    }


}
