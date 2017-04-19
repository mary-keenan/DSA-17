import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TextJustification {

    public static ArrayList<ArrayList> splitCostList;

    public static List<Integer> justifyText(String[] w, int m) {
        splitCostList = new ArrayList(w.length);
        for (int i = 0; i < w.length; i++) splitCostList.add(0, null);
        List<Integer> lineBreaks = makeChoice(w, m, 0, 0);
//        Collections.sort(lineBreaks);
        return lineBreaks;
    }

    private static List<Integer> makeChoice(String[] w, int m, int currWordInd, int currCost) {
        List<Integer> splitIndices = new ArrayList<>();
        //check memo
        if (splitCostList.size() > 0 && splitCostList.get(currWordInd) != null)
            return splitCostList.get(currWordInd);

        //check base case -- breaking after last line
        if (currWordInd > w.length - 2 && currWordInd > 1) {
            ArrayList costIndicesList = new ArrayList<>();
            costIndicesList.add(0, currCost);
            ArrayList indicesList = new ArrayList();
            indicesList.add(currWordInd);
            costIndicesList.add(1, indicesList);
            splitCostList.add(currWordInd, costIndicesList); //no more
            return splitIndices; //empty array list
        }

        //TODO: Given a starting index, what is the best way to split the rest of the lines?



        //initialize variables we're tracking
        int currLineSum = -1; //no space at the beginning
        int minCost = calculateCost(w, m, 0, 0); //minCost is only this word in the line
        int nextPointerInd = currWordInd; //allows for one word lines

        //loop until options are no longer valid
        while (nextPointerInd < w.length) {
            int nextWordLen = w[nextPointerInd].length();
            if (currLineSum + nextWordLen + 1 <= m) { //make sure we won't go past the line limit -- set nextPointerInd to w.length if it does to end it
                currLineSum += nextWordLen + 1; //account for space between words
                int currLineCost = currCost + calculateCost(w, m, currWordInd, nextPointerInd);
                //make recursive call -- we want the final cost, and if it's lower, we want to keep the returned list
                List<Integer> wordLineBreaks = new ArrayList<>(makeChoice(w, m, nextPointerInd + 1, currLineCost));
                //check path's cost in splitCostList -- is it worth keeping?
                int futureCost = (int) splitCostList.get(nextPointerInd + 1).get(0);
                if (futureCost <= minCost) { //this path is best so far -- might want to put in memo if it stays the best
                    wordLineBreaks.add(currWordInd);
                    splitIndices = wordLineBreaks; //update current fave list
                    minCost = futureCost; //update current minCost
                }
            } else nextPointerInd = w.length; //we've looked at all of the viable options already, let's break out of this joint
            nextPointerInd++;
        }
        //save the best thing we found so far and get out of here
        ArrayList bestPair = new ArrayList();
        bestPair.add(0, minCost);
        bestPair.add(1, splitIndices);
        splitCostList.remove(currWordInd); //correct for everything shifting back one when we add to certain index
        splitCostList.add(currWordInd, bestPair);
        return splitIndices;
    }

    private static Integer calculateCost(String[] w, int m, int startWordInd, int endWordInd) {
        int charSum = 0;
        for (int wordNum = startWordInd; wordNum <= endWordInd; wordNum++) {
            charSum += w[wordNum].length() + 1; //need to put in a space
        }
        int cost = (int) Math.pow(m - charSum + 1, 3); //take off the space at the end by subtracting 1 from charSum (which is just adding to m)
        return cost;
    }
}