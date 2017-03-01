import java.util.LinkedList;

public class Problems {

    static void sortNumsBetween100s(int[] A) {
        //count sort would be 2n (already know min/max range)
        //is merge sort or heap sort faster than nlogn if we know the number range?

        int[] countArray = new int[201]; // -100 to 100 inclusive
        for (int i: A) {
            countArray[i + 100]++;
        }
        int index = 0;
        for (int j = 0; j < countArray.length; j++) {
            for (int n = 0; n < countArray[j]; n++) {
                A[index] = j - 100;
                index++;
            }
        }
    }

    /**
     * @param n the character number, 0 is the rightmost character
     * @return
     */
    private static int getNthCharacter(String s, int n) {
        return s.charAt(s.length() - 1 - n) - 'a';
    }


    /**
     * Use counting sort to sort the String array according to a character
     *
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByCharacter(String[] A, int n) { //same thing as countingSortByDigit
        int alphaLen = 26;
        int digit;
        LinkedList<String>[] L = new LinkedList[alphaLen]; //list for each significant digit
        for (int i = 0; i < alphaLen; i++) { //26 letters in alphabet, 26 lists
            L[i] = new LinkedList<>();
        }

        for (String s : A) {
            digit = getNthCharacter(s, n); //getNthChar returns a digit that represents the letter (0-26)
            L[digit].add(s); //add string to that linked list (i.e. bucket b might have bob and bub)
        }
        int index = 0;
        for (LinkedList<String> list : L) {
            for (String letter: list) {
                A[index] = letter;
                index++;
            }
        }
    }

    /**
     * @param stringLength The length of each of the strings in S
     */
    static void sortStrings(String[] S, int stringLength) {
        // find the length of the strings in the array
        int stringLen = S[0].length(); //all strings in array are of the same length
        //use radix sort
        for (int n = 0; n < stringLen; n++) {
            countingSortByCharacter(S, n);
        }
    }
}
