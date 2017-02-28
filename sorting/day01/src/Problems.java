import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Problems {

    private static PriorityQueue<Integer> minPQ() {
        return new PriorityQueue<>(11);
    }

    private static PriorityQueue<Integer> maxPQ() {
        return new PriorityQueue<>(11, Collections.reverseOrder());
    }

    private static double getMedian(List<Integer> A) {
        double median = (double) A.get(A.size()/2);
        if (A.size() % 2 == 0)
            median = (median + A.get(A.size()/2-1))/2.0;
        return median;
    }

    // Runtime of this algorithm is O(N^2). Sad! We provide it here for testing purposes
    public static double[] runningMedianReallySlow(int[] A) {
        double[] out = new double[A.length];
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            int j = 0;
            while (j < seen.size() && seen.get(j) < A[i])
                j++;
            seen.add(j, A[i]);
            out[i] = getMedian(seen);
        }
        return out;
    }

    /**
     *
     * @param inputStream an input stream of integers
     * @return the median of the stream, after each element has been added
     */
    public static double[] runningMedian(int[] inputStream) {
        double[] runningMedian = new double[inputStream.length];
        PriorityQueue belowPQ = maxPQ(); //store values < median
        PriorityQueue abovePQ = minPQ(); //store values > median

        for (int i = 0; i < inputStream.length; i++) {
            //insert into queues
            double currVal = inputStream[i];
            if (abovePQ.isEmpty() || currVal >= (double) abovePQ.peek()) { //default is abovePQ
                abovePQ.offer(currVal);
            } else {
                belowPQ.offer(currVal);
            }

            //balance the queues
            if (belowPQ.size() > abovePQ.size() + 1) { //if below has more, move first entry to above
                abovePQ.offer(belowPQ.poll());
            } else if (abovePQ.size() > belowPQ.size()){ //won't do them both because successful if -/-> else if
                belowPQ.offer(abovePQ.poll());
            }

            //caculate median
            if ((abovePQ.size() + belowPQ.size()) % 2 == 0) { //if it's even, need to divide
                runningMedian[i] = ((double) abovePQ.peek() + (double) belowPQ.peek()) / 2.0;
            } else if (belowPQ.size() < abovePQ.size()){
                runningMedian[i] = (double) abovePQ.peek();
            } else { //below has more
                runningMedian[i] = (double) belowPQ.peek();
            }
        }
        return runningMedian;
    }
}
