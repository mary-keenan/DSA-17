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




        return null;
    }

}
