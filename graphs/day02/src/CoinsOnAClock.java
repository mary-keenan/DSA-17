import java.util.ArrayList;
import java.util.List;

public class CoinsOnAClock {

    private static char[] copyOf(char[] A) {
        char[] B = new char[A.length];
        for (int i = 0; i < A.length; i++)
            B[i] = A[i];
        return B;
    }

    public static List<char[]> coinsOnAClock(int pennies, int nickels, int dimes, int hoursInDay) {
        return placeCoin(new ArrayList<>(), new char[hoursInDay], pennies, nickels, dimes, 0); //shouldn't matter where you start
    }

    public static List<char[]> placeCoin(List<char[]> allClocks, char[] currClock, int penniesLeft, int nicklesLeft, int dimesLeft, int currHour) {
        //base case -- everything's been placed in valid spots, we're donesy's
        if (penniesLeft == 0 && nicklesLeft == 0 && dimesLeft == 0) {
            char[] bob = copyOf(currClock);
            allClocks.add(bob);
            return allClocks;
        } else if (currClock[currHour] == 'p' || currClock[currHour] == 'n' || currClock[currHour] == 'd') {
            return allClocks;
        }

        //try placing penny
        if (penniesLeft > 0) {
            int newHour = calcNewHour(currClock, currHour, 1);
            currClock[currHour] = 'p';
            placeCoin(allClocks, currClock, penniesLeft - 1, nicklesLeft, dimesLeft, newHour);
            currClock[currHour] = ' ';
        }
        //try placing nickel
        if (nicklesLeft > 0) {
            int newHour = calcNewHour(currClock, currHour, 5);
            currClock[currHour] = 'n';
            placeCoin(allClocks, currClock, penniesLeft, nicklesLeft - 1, dimesLeft, newHour);
            currClock[currHour] = ' ';
        }

        //try placing dime
        if (dimesLeft > 0) {
            int newHour = calcNewHour(currClock, currHour, 10);
            currClock[currHour] = 'd';
            placeCoin(allClocks, currClock, penniesLeft, nicklesLeft, dimesLeft - 1, newHour);
            currClock[currHour] = ' ';
        }

        return allClocks;
    }

    public static Integer calcNewHour(char[] clockBoard, int currHour, int coinVal) {
        int newHour = currHour + coinVal;
        if (newHour >= clockBoard.length) {
            newHour = newHour % clockBoard.length;
        }
        return newHour;
    }

}
