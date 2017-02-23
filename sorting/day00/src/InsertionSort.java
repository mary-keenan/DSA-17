import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class InsertionSort extends SortAlgorithm {
    /**
     * Use the insertion sort algorithm to sort the array
     *
     * Best-case runtime: O(n), if it's already sorted it just needs to run through them once
     * Worst-case runtime: O(n^2), because it's going to run through them n * (n-1) times
     * Average-case runtime: O(n^2), same reason as above
     *
     * Space-complexity: O(1), because you don't create any new data structures, just adjust the old one
     */
    @Override
    public int[] sort(int[] array) {
        for (int i = 1; i < array.length; i++) { //check each number's placement
            int iVal = array[i]; //need to remember original i value for j loop
            int lastIndex = i;
            for (int j = i-1; j >= 0; j--) { //look at all the entries in front of the number
                if (iVal < array[j]) { //if entry in front of i value is greater than it, switch them
                    array[lastIndex] = array[j]; //swap i and j values
                    array[j] = iVal;
                    lastIndex = j;
                } else { //gives it O(n) time in best case scenario;
                    break; //assumes numbers in front are already sorted
                }
            }
        }
        return array;
    }
}
