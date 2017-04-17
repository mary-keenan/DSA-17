import java.util.ArrayList;
import java.util.Arrays;

public class EditDistance {

    public static int minEditDist(String a, String b) {
        //create memo
        int[][] memo = new int[b.length() + 1][a.length() + 1]; //[bInd][aInd]
        for (int i=0; i <= b.length(); i++) {
            Arrays.fill(memo[i], -1);
        }
        //take top-down approach
        int minNumTrans = makeChoice(a, b, memo, 0, 0, 0);
        return minNumTrans;
    }

    private static int makeChoice(String a, String b, int[][] memo, int aInd, int bInd, int numTrans) {
        //check if memoized already -- no reason to go further if it is
//        if (memo[bInd][aInd] != -1) {
//            return memo[bInd][aInd];
//        }

        //check base cases -- reached the bottom or right edge of our sub-problem matrix
        if (a.length() != 0 && aInd == a.length() || bInd == b.length()) {
            if (bInd == b.length() && aInd == a.length())
                return numTrans; //we finished transforming the string
            else return Integer.MAX_VALUE;
        }
        //if current letter in A matches current letter in B, do not make any transformation
        if (aInd < a.length() && a.charAt(aInd) == b.charAt(bInd)) {
            memo[bInd][aInd] = makeChoice(a, b, memo, aInd + 1, bInd + 1, numTrans);
        } else if (aInd >= a.length()) { //if index has gone past a's length, you can ONLY INSERT
            memo[bInd][aInd] = makeChoice(a, b, memo, aInd, bInd + 1, numTrans + 1); //we only take care of b, not a
        } else {
            //otherwise, perform the three possible transformations -- insert b.charAt(bInd), replace a.charAt(aInd) with b.charAt(bInd), or remove a.charAt(aInd)
            int numTransRemove = makeChoice(a, b, memo, aInd + 1, bInd, numTrans + 1); //we only take care of a, not b
            int numTransReplace = makeChoice(a, b, memo, aInd + 1, bInd + 1, numTrans + 1); //we take care of a and b
            int numTransInsert = makeChoice(a, b, memo, aInd, bInd + 1, numTrans + 1); //we only take care of b, not a
            //memoize lowest numTrans and return min
            memo[bInd][aInd] = Math.min(Math.min(numTransRemove, numTransReplace), numTransInsert);
        }
        return memo[bInd][aInd];
    }

}
