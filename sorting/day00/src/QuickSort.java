import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class QuickSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * Best-case runtime: O(nlog(n)), same reason as merge sort
     * Worst-case runtime: O(n^2), if list is already sorted in ascending or descending order, because one side of partition will always have no elements
     * Average-case runtime: O(nlog(n)), same reason as merge sort
     *
     * Space-complexity: O(log(n)), because you keep using the same array, just need to remember recursion tree calls
     */
    @Override
    public int[] sort(int[] array) {
        if (array.length > 1){
            Collections.shuffle(Arrays.asList(array)); //randomly sort to reduce chance of worst case scenario
            quickSort(array, 0, array.length - 1);
        }
        return array;
    }

    /**
     * Partition the array around a pivot, then recursively sort the left and right
     * portions of the array. A test for this method is provided in `SortTest.java`
     * Optional: use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     *
     * @param low The beginning index of the subarray being considered (inclusive)
     * @param high The ending index of the subarray being considered (inclusive)
     */
    public void quickSort(int[] a, int low, int high) {
        if (high - low < 1) { //base case -- if array range is 1 (difference is 0), don't sort array
            a = a;
        } else { //haven't gotten to base case yet
            int pivot = partition(a, low, high); //get pivot index so we can divide array range into two different quickSort calls
            quickSort(a, low, pivot);
            quickSort(a, pivot + 1, high);
        }
    }


    /**
     * Given an array, choose the array[low] element as the "pivot" element.
     * Place all elements smaller than "pivot" on "pivot"'s left, and all others
     * on its right. Return the final position of "pivot" in the partitioned array.
     *
     * @param low The beginning index of the subarray being considered (inclusive)
     * @param high The ending index of the subarray being considered (inclusive)
     */
    public int partition(int[] array, int low, int high) {
        int pivot = array[low];
        int i = low + 1; //tells us where the division between > and < is
        for (int j = 0; i + j <= high; j++) {
            if (array[i + j] < pivot) {
                int oldNum = array[i]; //save bigger number we're swapping
                array[i] = array[i + j]; // switch smaller number to bigger number's spot
                array[i + j] = oldNum; //switch bigger number to smaller number's spot
                i = i+1; //increment i, move to next spot
                j--; //so we don't skip over next number (i increased, so j can't)
            }
        }
        array[low] = array[i-1];
        array[i-1] = pivot;
        return i-1; //pivot would be where i - 1 is (since my i is actually i + 1 -- easier for swapping)
    }

}
