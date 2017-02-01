package your_code;

import ADTs.QueueADT;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An implementation of the Queue interface.
 */
public class MyQueue implements QueueADT<Integer> {

    LinkedList<Integer> myLinkedList = new LinkedList<>();
    int size = 0;

    @Override
    public void enqueue(Integer item) {
        myLinkedList.add(size, item); //add to end of list, which has an index equal to size
        size++;
    }

    @Override
    public Integer dequeue() {
        if (size >= 1) {
            int dequeuedInt = myLinkedList.get(0);
            myLinkedList.remove(0);
            size--;
            return dequeuedInt;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Integer front() {
        if (size >= 1){
            int frontInt = myLinkedList.get(0);
            return frontInt;
        }
        return null;
    }
}