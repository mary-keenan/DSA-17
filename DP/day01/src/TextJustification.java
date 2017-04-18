import java.util.ArrayList;
import java.util.List;

public class TextJustification {

    public static List<Integer> justifyText(String[] w, int m) {
        ArrayList bob = new ArrayList();
        for (int i = 0; i < w.length; i++) bob.add(0, null);
        List<Integer> lineBreaks = makeChoice(w, m, 0, 0, bob);
        return lineBreaks;
    }


    private static List<Integer> makeChoice(String[] w, int m, int currWordInd, int currCost, ArrayList<ArrayList> splitCostList) {
        List<Integer> splitIndices = new ArrayList<>();
        //check memo
        if (splitCostList.size() > 0 && splitCostList.get(currWordInd) != null) return splitCostList.get(currWordInd); //TODO: should I predefine vals? How do I add to specific indices if it's not that long...?

        //check base case TODO: does this update splitCostList correctly outside of this recursive call
        if (currWordInd == w.length) {
            ArrayList costIndicesList = new ArrayList<>();
            costIndicesList.add(0, currCost);
            costIndicesList.add(1, new ArrayList<>());
            splitCostList.add(currWordInd, costIndicesList);
            return new ArrayList<>();
        }

        //given a starting index, what is the best way to split the rest of the lines?
        //initialize variablese we're tracking
        int minCost = Integer.MAX_VALUE;
        int currLineSum = w[currWordInd].length();
        int nextPointerInd = currWordInd + 1;
        //loop until options are no longer valid
        while (currLineSum < m && nextPointerInd < w.length) {
            int nextWordLen = w[nextPointerInd].length();
            if (currLineSum + nextWordLen <= m) { //make sure we won't go past the line limit
                currLineSum += nextWordLen + 1; //account for space between words
                int currLineCost = currCost + calculateCost(w, m, currWordInd, nextPointerInd);
                //make recursive call -- we want the final cost, and if it's lower, we want to keep the returned list
                List<Integer> wordLineBreaks = new ArrayList<>(makeChoice(w, m, nextPointerInd + 1, currLineCost, splitCostList));
                //check its cost in splitCostList -- is it worth pursuing?
                if ((int) splitCostList.get(nextPointerInd).get(0) < minCost) { //this path is best so far -- it's worth remembering
                    wordLineBreaks.add(currWordInd + 1); //breaks after this word
                    splitIndices = wordLineBreaks; //update current fave list
                    minCost = (int) splitCostList.get(nextPointerInd).get(0); //update current minCost
                }
            }
            nextPointerInd++;

        }
        //save the best thing we found so far and get out of here
        ArrayList bestPair = new ArrayList();
        bestPair.add(0, minCost);
        bestPair.add(1, splitIndices);
        splitCostList.add(currWordInd, bestPair);
        return splitIndices;
    }

    private static Integer calculateCost(String[] w, int m, int startWordInd, int endWordInd) {
        int charSum = 0;
        for (int wordNum = startWordInd; wordNum <= endWordInd; wordNum++) {
            charSum += w[wordNum].length() + 1; //need to put in a space
        }
        int cost = (int) Math.pow(m - charSum - 1, 3); //take off the space at the end by subtracting 1
        return cost;
    }
}