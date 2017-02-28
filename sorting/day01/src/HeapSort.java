public class HeapSort extends SortAlgorithm {
    int size;
    int[] heap;

    private int parent(int i) {
        return (i-1) / 2;
    }

    private int leftChild(int i) {
        return 2*i + 1;
    }

    private int rightChild(int i) {
        return 2 * (i + 1);
    }

    // Recursively corrects the position of element indexed i: check children, and swap with larger child if necessary.
    public void heapify(int i) {

        int leftKidInd = leftChild(i);
        int rightKidInd = rightChild(i);
        int largestNumInd = i;
        int currVal = heap[i];

        if (leftKidInd < size) {
            if (heap[leftKidInd] > heap[largestNumInd]) {
                largestNumInd = leftKidInd;
            }
        }
        if (rightKidInd < size) {
            if (heap[rightKidInd] > heap[largestNumInd]) {
                largestNumInd = rightKidInd;
            }
        }
        if (largestNumInd != i) {
            heap[i] = heap[largestNumInd]; //swap spots
            heap[largestNumInd] = currVal;
            heapify(largestNumInd); //check to see if we need to keep swapping
        }
    }

    // Given the array, build a heap by correcting every non-leaf's position.
    public void buildHeapFrom(int[] array) {
        this.heap = array;
        this.size = array.length;
        for (int i = this.size - 1; i >= 0; i--) { //go down so you don't miss one (swap it behind pointer, never hit it)
            heapify(i);

        }
    }

    /**
     * Best-case runtime: O(nlogn), because tree is n wide at the base and log(n) tall
     * Worst-case runtime: O(nlogn)
     * Average-case runtime: O(nlogn)
     *
     * Space-complexity: O(1), because it doesn't create any new data structures
     */
    @Override
    public int[] sort(int[] array) {
        buildHeapFrom(array);
        for (int i=size-1; i>0; i--) {
            int temp = heap[0];
            heap[0] = heap[i];
            heap[i] = temp;
            size = i; //don't start at end (or you'll end up with max values at front again)
            heapify(0);
        }
        return heap;
    }
}
