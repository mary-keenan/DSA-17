package your_code;

import ADTs.StackADT;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * An implementation of the Stack interface.
 */
public class MyStack implements StackADT<Integer> {

    ArrayList<Integer> myArrayList = new ArrayList<>();
    ArrayList<Integer> maxList = new ArrayList<>();
    int size = 0;
    int max_size = 0;
    int max = 0;

    @Override
    public void push(Integer e) {
        myArrayList.add(size, e); //add to end of list, which has an index equal to size
        size++;

        if (e >= max) {
            maxList.add(max_size, e);
            max_size++;
            max = e;
        }
    }

    @Override
    public Integer pop() {
        if (size >= 1){
            int poppedInt = myArrayList.get(size - 1); //get it from the end of the list
            myArrayList.remove(size - 1); //remove it after getting it
            size--;

            if (poppedInt == max && max_size > 1) {
                maxList.remove(max_size - 1); //remove last thing
                max_size--;
                max = maxList.get(max_size - 1); //get new last thing
            } else if (poppedInt == max && max_size == 1){ //can't be less than 1 unless list is empty
                maxList.remove(0); //remove last thing
                max_size = 0;
                max = 0;
            }
            return poppedInt;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Integer peek() {
        if (size >= 1){
            int peekInt = myArrayList.get(size - 1);
            return peekInt;
        }
        return null;
    }

    public Integer maxElement() {
        return max;
    }
}
