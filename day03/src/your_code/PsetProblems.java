package your_code;

import ADTs.StackADT;

import java.util.Stack;

public class PsetProblems {

    public static int longestValidSubstring(String s) {
        String[] myString = s.split("");
        int max_streak = 0;
        int streak = 0;
        Stack myStack = new Stack();
        for (int i = 1; i < myString.length; i++) {
            String val = myString[i];
            if (val.equals("(")) {
                myStack.push(val);
            } else if (myStack.empty() != true){ //value must be ")"
                myStack.pop();
                streak += 2; //one for the L and one for the R parentheses
            } else { //stack is empty and value is ")" -- invalid
                if (streak > max_streak) {
                    max_streak = streak;
                }
                streak = 0;
            }
        }
        return max_streak;
    }

    public static StackADT<Integer> sortStackLimitedMemory(StackADT<Integer> myStack) {
        StackADT<Integer> resultStack = new MyStack(); //will be sorted in ascending order
        int currInt = 0; //integer floating in-between stacks
        int topInt = 0; //integer at top of result stack
        resultStack.push(myStack.pop()); //get the first value in there
        while (myStack.isEmpty() == false) {
            System.out.println("new");
            currInt = myStack.pop();
            topInt = resultStack.peek();
            System.out.println(currInt);
            System.out.println(topInt);
            if (topInt >= currInt) { //order is correct, top value is equal or smaller
                resultStack.push(currInt);
            } else { //order is incorrect
                topInt = resultStack.pop(); //put topInt back in old stack
                myStack.push(topInt);
                if (resultStack.isEmpty()){ //if nothing is in the result stack, currInt goes there
                    resultStack.push(currInt);
                }
                myStack.push(currInt); //put currInt back in old stack, but now it's on top
            }
        }
        return resultStack;
    }

}
