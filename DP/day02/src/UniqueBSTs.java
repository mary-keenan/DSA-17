import java.util.ArrayList;

public class UniqueBSTs {

    public static int uniqueBSTs(int n) {
        //bottom-up approach

        int[] memo = new int[n + 1]; //memo.get(n) = # of unique trees for that n
        //base cases
        memo[0] = 1; //this just gives it flexibility to do i-1 without problems
        memo[1] = 1;

        //make table -- just like fibonacci; start at 2 since we already did 0 and 1
        for (int i = 2; i < n + 1; i++) {
            //retrieve already-calculated values to calculate our current i, a-la-fibonacci
            for (int j = 0; j <= i - 1; j++) {
                //given i as the root of the tree, multiply the # of #s that could be on the left side by the corresponding # of #s on the right side
                int uniqueTreesHere = memo[j] * memo[i - j - 1]; //as more numbers go to the right side, there are fewer numbers on the left side
                memo[i] = uniqueTreesHere;
            }
        }
        return memo[n];
    }
}

