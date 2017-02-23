import java.lang.reflect.Array;

public class MergeSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * This is the recursive step in which you split the array up into
     * a left and a right portion, sort them, and then merge them together.
     *
     * Best-case runtime: O(nlog(n)), because you iterate through every element (when merging) and your tree is log(n) tall (which is how many merges you do)
     * Worst-case runtime: O(nlog(n)), same as above
     * Average-case runtime: O(nlog(n)), same as above
     *
     * Space-complexity: O(n), make new arrays --> eventually have n arrays of size 1
     */
    @Override
    public int[] sort(int[] array) {
//        System.out.print(array.length);
//        System.out.println(array.length/2);

        if (array.length <= 1) { //base case
            return array;
        }
        //create first and second half arrays
        int[] firstHalf = new int[array.length/2]; //TODO: will the /2 cause problems with splitting?
        int[] secondHalf = new int[array.length/2];
        if (array.length % 2 != 0) { //if the length is odd,
            secondHalf = new int[array.length/2 + 1]; //round up for the second array
        }
        for (int i = 0; i < firstHalf.length; i++) {
            firstHalf[i] = array[i];
        }
        for (int j = 0; j < secondHalf.length; j++) {
            secondHalf[j] = array[firstHalf.length + j];
        }
        //sort first and second half arrays
        firstHalf = sort(firstHalf);
        secondHalf = sort(secondHalf);
        int[] sortedArray = merge(firstHalf, secondHalf);
//        System.out.println(String.valueOf(sortedArray));
        return sortedArray;
    }

    /**
     * Given two sorted arrays a and b, return a new sorted array containing
     * all elements in a and b. A test for this method is provided in `SortTest.java`
     * Use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     */
    public int[] merge(int[] a, int[] b) {
        int[] fullArray = new int[a.length + b.length]; //we need to make new array to combine A and B
        //check to see if we can use Insertion Sort
        if (a.length + b.length <= INSERTION_THRESHOLD){ //if the combined length of the arrays are insertion sortable
            for (int i = 0; i < fullArray.length; i++) {
                if (i < a.length) { //first a.length part of fullArray is made up of A
                    fullArray[i] = a[i];
                }
                else {
                    fullArray[i] = b[i - a.length]; //last b.length part of fullArray is made up of B
                }
            }
            //FROM INSERTION SORT WHICH IS PASSING TEST
            for (int i = 1; i < fullArray.length; i++) { //check each number's placement
                int iVal = fullArray[i]; //need to remember original i value for j loop
                int lastIndex = i;
                for (int j = i-1; j >= 0; j--) { //look at all the entries in front of the number
                    if (iVal < fullArray[j]) { //if entry in front of i value is greater than it, switch them
                        fullArray[lastIndex] = fullArray[j]; //swap i and j values
                        fullArray[j] = iVal;
                        lastIndex = j;
                    }
                }
            }
            return fullArray;
        }
        //too long to use insertion sort
        int a_index = 0;
        int b_index = 0;
        for (int i = 0; i < fullArray.length; i++) {
            if (a_index < a.length && b_index < b.length ) { //check to make sure we're not out of bounds for either index
                if (b_index >= b.length || a[a_index] < b[b_index]) { //if current A value is less than current B value
                    fullArray[i] = a[a_index]; //add current A value to fullArray
                    a_index++; // move to the next A value
                } else {
                    fullArray[i] = b[b_index]; //add current B value to fullArray
                    b_index++; // move to the next B value
                }
            } else if (b_index >= b.length) { //if B is done, do A by default
                fullArray[i] = a[a_index];
                a_index++;
            } else if (a_index >= a.length) { //vice versa
                fullArray[i] = b[b_index];
                b_index++;
            }
        }
        return fullArray;
    }

}
