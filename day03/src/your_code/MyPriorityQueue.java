package your_code;

import java.util.LinkedList;

/**
 * An implementation of a priority Queue
 */
public class MyPriorityQueue {


    LinkedList<Integer> myLinkedList = new LinkedList<>();
    int size = 0;

    public void enqueue(int item) {
        myLinkedList.add(size, item); //add to end of list, which has an index equal to size
        size++;
    }

    /**
     * Return and remove the largest item on the queue.
     */
    public int dequeueMax() {
        if (size >= 1) {
            int max = 0;
            int max_index = 0;
            for (int i = 0; i < size; i++) { //run through every value in queue
                int val = myLinkedList.get(i);
                if (val > max) { //if value is greater than max, it becomes new max
                    max = val;
                    max_index = i;
                }
            }
            int dequeuedMaxInt = myLinkedList.get(max_index);
            myLinkedList.remove(max_index);
            size--;
            return dequeuedMaxInt;
        }
        return -1;
    }

}