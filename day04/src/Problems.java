import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Problems {

    public static Map<Integer, Integer> getCountMap(int[] arr) {
        MyLinearMap countMap = new MyLinearMap();
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            if (countMap.get(num) != null) { //if int is already in map
                int count = (int) countMap.get(num);
                count++; //increase number of occurences
                countMap.put(num, count); //put updated count in map
            } else {
                countMap.put(num, 1);
            }
        }
        return countMap;
    }

    public static List<Integer> removeKDigits(int[] num, int k) {
        List<Integer> newNum = new ArrayList();
        List<Integer> indicesRemove = new ArrayList<>(); //don't remove indices until later?
        LinkedList<Integer> moveThrough = new LinkedList<>(); //can move through list in constant time...
        for (int b = 0; b < num.length; b++) { //put int array into list to make removing easier -- O(n)
            newNum.add(num[b]);
        }
        //now start removing numbers; return list when k = 0
        for (int i = 0; i < newNum.size() - 1; i++) { //now loop through array -- O(N)
            if (k == 0) { //this means we're done removing digits and need to return the list
                return newNum;
            }
            else if (newNum.get(i) >= newNum.get(i+1)) { //if we're on the number we're currently removing
                newNum.remove(i); //
                k--;
                i--; //otherwise, we skip one after removing it (cause indices shift forward)
            }
        }
        //if k is still > 0 (i.e. array was [1 2 3]), just take off biggest numbers (which should be in back since it's in ascending order)
        for (int i = newNum.size() - 1; i > 0; i--) { //O(N)
            if (k == 0) { //this means we're done removing digits and need to return the list
                return newNum;
            }
            newNum.remove(i); //
            k--;
            } //don't need to shift indices here (because removing from the end of the list)
        return null;
    }

    public static int sumLists(Node<Integer> h1, Node<Integer> h2) {
        Node<Integer> count1 = h1;
        Node<Integer> count2 = h2;

        int length1 = 0;
        int length2 = 0;
        for (; h1.next != null; h1 = h1.next) {
            length1++;
        }
        for (; h2.next != null; h2 = h2.next) {
            length2++;
        }

        int sum1 = 0;
        int sum2 = 0;
        for (; count1 != null; count1 = count1.next){
            double val1 = count1.data * Math.pow(10, length1); // 10^length --> that way you have numbers in correct places
            sum1 += val1;
            length1--;
        }
        for (; count2 != null; count2 = count2.next){
            double val2 = count2.data * Math.pow(10, length2);
            sum2 += val2;
            length2--;
        }
        int sumNodes = sum1 + sum2;
        return sumNodes;
    }
}

//        for (int b = 0; b < num.length; b++) { //put int array into list to make removing easier -- O(n)
//        newNum.add(num[b]);
//        }
//        //now start removing numbers; return list when k = 0
//        for (int i = 0; i < newNum.size() - 1; i++) { //now loop through array -- O(N)
//        if (k == 0) { //this means we're done removing digits and need to return the list
//        return newNum;
//        }
//        else if (newNum.get(i) >= newNum.get(i+1)) { //if we're on the number we're currently removing
//        newNum.remove(i); //
//        k--;
//        i--; //otherwise, we skip one after removing it (cause indices shift forward)
//        }
//        }
//        //if k is still > 0 (i.e. array was [1 2 3]), just take off biggest numbers (which should be in back since it's in ascending order)
//        for (int i = newNum.size() - 1; i > 0; i--) { //O(N)
//        if (k == 0) { //this means we're done removing digits and need to return the list
//        return newNum;
//        }
//        newNum.remove(i); //
//        k--;
//        } //don't need to shift indices here (because removing from the end of the list)