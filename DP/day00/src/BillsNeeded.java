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
            return -1;
        }

        int minNumBills = -1;
        int pathNumBills;
        //iterate through bills
        for (int bill: billDenomination) {
            if (sumMap.get(currSum + bill) != null) { //we've already tried this path
                pathNumBills = (int) sumMap.get(currSum + bill);
            } else { //first time down this path
                pathNumBills = pickBill(N, currSum + bill, numCurrBills + 1, billDenomination, sumMap);
                sumMap.put(currSum + bill, pathNumBills); //put it in the map
            }
            if (pathNumBills < minNumBills) {
                minNumBills = pathNumBills;
            }
        }

        return minNumBills;
    }


}
