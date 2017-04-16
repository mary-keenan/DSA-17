import java.util.ArrayList;
import java.util.Arrays;

public class EditDistance {

    public static int minEditDist(String a, String b) {
        //create memo
        int[][] memo = new int[b.length() + 1][b.length() + 1]; //[numTrans][aInd]
        for (int i=0; i <= b.length(); i++) {
            Arrays.fill(memo[i], -1);
        }
        //take top-down approach
        int minNumTrans = makeChoice(a, b, memo, 0, 0, 0);
        return minNumTrans;
    }

    private static int makeChoice(String a, String b, int[][] memo, int aInd, int bInd, int numTrans) {
        //check if memoized already -- no reason to go further if it is
        if (memo[numTrans][aInd] != -1) {
            return memo[numTrans][aInd];
        }
        //check base cases -- reached the bottom or right edge of our sub-problem matrix
        if (aInd == a.length() || numTrans == b.length()) {
            if (bInd >= b.length() - 1 && aInd >= a.length() - 1) return Integer.MAX_VALUE; //we never finished transforming the string
            else return numTrans;
        }
        //if current letter in A matches current letter in B, do not make any transformation
        if (a.charAt(aInd) == b.charAt(bInd)) {
            memo[numTrans][aInd] = makeChoice(a, b, memo, aInd + 1, bInd + 1, numTrans);
            return memo[numTrans][aInd];
        }
        //otherwise, perform the three possible transformations -- insert b.charAt(bInd), replace a.charAt(aInd) with b.charAt(bInd), or remove a.charAt(aInd)
        int numTransRemove = makeChoice(a, b, memo, aInd + 1, bInd, numTrans + 1); //we only take care of a, not b
        int numTransReplace = makeChoice(a, b, memo, aInd + 1, bInd + 1, numTrans + 1); //we take care of a and b
        int numTransInsert = makeChoice(a, b, memo, aInd, bInd + 1, numTrans + 1); //we only take care of b, not a

        //memoize lowest numTrans and return min
        memo[numTrans][aInd] = Math.min(Math.min(numTransRemove, numTransReplace), numTransInsert);
        return memo[numTrans][aInd];
    }

}
