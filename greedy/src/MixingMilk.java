import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MixingMilk {
    public static int solve(int M, int N, int[] units, int[] price) { //TODO: units and prices lists are mixed up???
        int currMilkSum = 0;
        int currMilkCost = 0;
        HashMap<Integer, Integer> quantityCostMap = new HashMap<>(); //quantityCostMap[cost] = quantity
        ArrayList costList = new ArrayList(); //we'll sort this --> get lowest cost --> access quantity from map

        //SORT FARMER'S QUANTITIES BY COST -- STORE IN HASHMAP
        for (int n = 0; n < N; n++) {
            if (quantityCostMap.get(units[n]) == null) { //if this cost isn't already in the map, just add it normally
                quantityCostMap.put(units[n], price[n]);
                costList.add(units[n]);
            } else { //otherwise update current value -- keeping track of specific farmers doesn't matter, just overall cost
                quantityCostMap.put(units[n], quantityCostMap.get(units[n]) + price[n]);
            }
        }
        Collections.sort(costList); //sorts in ascending order

        //RUN THROUGH COST LIST/QUANTITY MAP UNTIL WE HAVE ENOUGH MILK
        while (currMilkSum < M) {
            if (costList.size() > 0) {
                int currMinCost = (int) costList.get(0);
                int costQuantity = quantityCostMap.get(currMinCost);
                if (M >= currMilkSum + costQuantity) { //if this won't take us over the limit, continue
                    currMilkSum += costQuantity;
                    currMilkCost += costQuantity * currMinCost;
                } else {
                    int diff = M - currMilkSum;
                    currMilkSum += diff;
                    currMilkCost += diff * currMinCost;
                }
                costList.remove(0);
            }
            else { //might as well end this loop
                currMilkSum = M;
            }
        }
        return currMilkCost;
    }
}
