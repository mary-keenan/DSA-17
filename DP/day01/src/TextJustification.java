import java.util.ArrayList;
import java.util.List;

public class TextJustification {

    public static ArrayList<ArrayList> splitLinesList;
    public static ArrayList<Integer> costList;
    public static ArrayList<Integer> lastCostList;

    public static List<Integer> justifyText(String[] w, int m) {
        splitLinesList = new ArrayList(w.length);
        costList = new ArrayList<>(w.length);
        lastCostList = new ArrayList<>(w.length);
        for (int i = 0; i < w.length; i++) {
            splitLinesList.add(0, null);
            costList.add(0, null);
            lastCostList.add(0, null);
        }
        List<Integer> lineBreaks = makeChoice(w, m, 0, 0);
        return lineBreaks;
    }

    private static List<Integer> makeChoice(String[] w, int m, int currWordInd, int currCost) {
        ArrayList<Integer> breakIndices = new ArrayList<>();

        //check memo
        if (splitLinesList.size() > 0 && splitLinesList.get(currWordInd) != null) {
            if (lastCostList.get(currWordInd) > currCost) {
                int costGainForLinesAfterHere = costList.get(currWordInd) - lastCostList.get(currWordInd);
                costList.remove(currWordInd);
                costList.add(currWordInd, currCost + costGainForLinesAfterHere);
                lastCostList.remove(currWordInd);
                lastCostList.add(currWordInd, currCost);
            }
            return splitLinesList.get(currWordInd);
        }

        //TODO: Given a starting index, what is the best way to split the rest of the lines?

        //initialize variables we're tracking
        int currLineSum = w[currWordInd].length(); //no space at the beginning
        int nextPointerInd = currWordInd + 1; //allows for one word lines
        int currLineCost = currCost + calculateCost(w, m, currLineSum);
        int minCost = Integer.MAX_VALUE;

        //base case -- no words left, will skip by while loop
        if (currWordInd > w.length - 2)
            minCost = currLineCost;

        //loop until options are no longer valid
        while (nextPointerInd < w.length) {
            //what if we split on this line?

            ArrayList<Integer> wordLineBreaks = new ArrayList<>(makeChoice(w, m, nextPointerInd, currLineCost));
            //check path's cost in splitCostList -- is it worth keeping?
            int futureCost = costList.get(nextPointerInd);
            if (futureCost <= minCost) { //this path is best so far -- might want to put in memo if it stays the best
                breakIndices = wordLineBreaks; //update current fave list
                minCost = futureCost; //update current minCost
            }
            //now add next word if possible
            int nextWordLen = w[nextPointerInd].length();
            if (currLineSum + nextWordLen + 1 <= m) { //make sure we won't go past the line limit -- set nextPointerInd to w.length if it does to end it
                currLineSum += nextWordLen + 1; //account for space between words
                currLineCost = currCost + calculateCost(w, m, currLineSum);
                nextPointerInd++;
            } else { //new word would exceed line sum
                break; //we've looked at all of the viable options already, let's break out of this joint
            }
            //we're about to move beyond the last word
            if (nextPointerInd == w.length) {
                if (currCost + calculateCost(w, m, currLineSum) <= minCost) { //this path is best so far -- might want to put in memo if it stays the best
                    breakIndices = new ArrayList<>(); //update current fave list
                    minCost = currCost + calculateCost(w,m, currLineSum); //update current minCost
                }
            }
        }

        breakIndices.add(0, currWordInd);
        //save the best thing we found so far and get out of here
        costList.remove(currWordInd); //have to remove first so inserting doesn't move everything down
        costList.add(currWordInd, minCost);
        splitLinesList.remove(currWordInd);
        splitLinesList.add(currWordInd, breakIndices);
        lastCostList.remove(currWordInd);
        lastCostList.add(currWordInd, currCost);
        return breakIndices;
    }

    private static Integer calculateCost(String[] w, int m, int currLineSum) {
        int cost = (int) Math.pow(m - currLineSum, 3); //take off the space at the end by subtracting 1 from charSum (which is just adding to m)
        return cost;
    }
}