public class CountingSort {

    /**
     * Use counting sort to sort positive integer array A.
<<<<<<< HEAD
     * Runtime: O(N + k)
=======
     * Runtime: TODO
     *
     * k: maximum element in array A
>>>>>>> 83863a25176141187ac3c3f980ba84de26d1936e
     */
    static void countingSort(int[] A) {
        //FIND MAX VALUE
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
            if (A[i] < min) { //first time (i=0), it'll be both
                min = A[i];
            }
        }

        //CREATE COUNT LIST
        int currVal;
        int[] countList = new int[max - min + 1];
        for (int j = 0; j < A.length; j++) {
            currVal = A[j];
            countList[currVal - min]++; //adjust for non-zero min --> -1 or max
        }

        //RECREATE ARRAY
        int index = 0;
        for (int n = 0; n < countList.length; n++) {
            for (int m = 0; m < countList[n]; m++) {
                A[index] = n;
                index++;
            }
        }
    }
}
