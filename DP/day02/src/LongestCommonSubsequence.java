public class LongestCommonSubsequence {

    public static int longestCommonSubsequence(String S, String T) {
        int[][] LCSTable = new int[S.length()][T.length()];

        for (int s = 0; s < S.length(); s++) {
            for (int t = 0; t < T.length(); t++) {
                int boxLeft = Integer.MIN_VALUE;
                int boxUp = Integer.MIN_VALUE;
                if (s > 0) boxLeft = LCSTable[s - 1][t]; //check to make sure there is a box to the left
                if (t > 0) boxUp = LCSTable[s][t - 1]; //check to make sure there is a box above
                int boxCurr = Math.max(boxLeft, boxUp);
                if (S.charAt(s) == T.charAt(t) && s > 0 && t > 0) boxCurr = LCSTable[s - 1][t - 1] + 1;
                else if (S.charAt(s) == T.charAt(t) && s == 0 && t == 0) boxCurr = 1;
                else if (s == 0 && t == 0) boxCurr = 0;
                LCSTable[s][t] = boxCurr;
            }
        }
        return LCSTable[S.length() - 1][T.length() - 1];
    }
}
