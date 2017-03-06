import java.util.LinkedList;

public class RadixSort {

    /**
     * @param n the digit number, 0 is least significant
     * @return
     */
    private static int getNthDigit(int number, int base, int n) {
        return number / ((int) Math.pow(base, n)) % base;
    }


    /**
     * Use counting sort to sort the integer array according to a digit
     *
     * @param b The base used in radix sort
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByDigit(int[] A, int b, int n) {
        int digit;
        LinkedList<Integer>[] L = new LinkedList[b]; //list for each significant digit
        for (int i = 0; i < b; i++) {
            L[i] = new LinkedList<>();
        }
        for (int i : A) {
            // Extract the relevant digit from i, and add i to the corresponding Linked List.
            digit = getNthDigit(i, b, n);
            L[digit].add(i); //add number to that linked list (i.e. bucket 6 might have 623 and 645)
        }
        int index = 0;
        for (LinkedList<Integer> list : L) {
            // Put all numbers in the linked lists into A
            for (int num: list) {
                A[index] = num;
                index++;
            }
        }
    }

    /**
     * TODO: Express your runtime in terms of n, b, and w
     * Runtime: O(w*n) where does b fit in? Is it w*n / b? I just know runtime goes down if b gets bigger. Actually, I don't know that. I just think that.
     *
     * n: length of array
     * w: word length of integers A in base b (equal to log base b of k (log_b k) )
     *
     * @param b The base to use for radix sort
     */
    static void radixSort(int[] A, int b) {
        // Calculate the upper-bound for numbers in A
        int k = A[0] + 1;
        for (int i = 1; i < A.length; i++)
            k = (A[i] + 1 > k) ? A[i] + 1 : k;
        int w = (int) Math.ceil(Math.log(k) / Math.log(b)); // w = log base b of k, word length of numbers
        // TODO: Perform radix sort
        for (int n = 0; n < w; n++) {
            countingSortByDigit(A, b, n);
        }
    }

}
