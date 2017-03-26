public class ReviewProblem {

    public static int minimumLengthSubArray(int[] A, int desiredSum) {
        int minLen = 0;
        for (int i = 0; i < A.length; i++) {
            int currSum = 0;
            int currLen = 0;
            int j = i;

            while (j < A.length && currSum < desiredSum) {
                currSum += A[j];
                currLen++;
                j++;
            }
            if (currSum >= desiredSum && minLen == 0 || currSum >= desiredSum && currLen < minLen) {
                minLen = currLen;
            }
        }
        return minLen;
    }
}
