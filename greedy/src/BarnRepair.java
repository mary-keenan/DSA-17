import java.util.ArrayList;

public class BarnRepair {
    public static int solve(int M, int S, int C, int[] occupied) {
        int blockedStallSum = S;
        //CREATE LIST OF UNOCCUPIED STREAKS
        ArrayList<ArrayList> streakList = new ArrayList<>(); //list of [start, end] indices of empty stall streaks
        int startStreak = -1;
        for (int currStall = 1; currStall <= S; currStall++) { //INDEXES FROM 1
            Boolean endStreak = false;
            for (int occupiedStall : occupied) {
                if (currStall == occupiedStall) { //if the current stall is occupied
                    endStreak = true; //we're done here
                }
            }
            if (!endStreak && startStreak == -1) { //streak did not exist but will now continue
                startStreak = currStall;
            } else if (endStreak && startStreak == 1){
                blockedStallSum -= (currStall - 1);
                startStreak = -1;
            } else if (endStreak && startStreak != -1) { //streak existed but is now over
                ArrayList streakBounds = new ArrayList();
                streakBounds.add(0, currStall - 1);
                streakBounds.add(0, startStreak);
                streakList.add(streakBounds);
                startStreak = -1;
            }
        }
        if (startStreak != -1) { //make sure we chop off anything on the right end
            blockedStallSum -= (S - startStreak + 1);
        }

        //WHITTLE DOWN BASED ON LONGEST UNOCCUPIED STREAKS
        while (M > 1) {
            int maxLen = -1;
            int arrayInd = -1;
            for (int i = 0; i < streakList.size(); i++) {
                int currLen = (int) streakList.get(i).get(1) - (int) streakList.get(i).get(0) + 1;
                if (currLen > maxLen) { //if this streak is the longest we've seen so far
                    maxLen = currLen;
                    arrayInd = i;
                }
            }
            if (arrayInd != -1) {
                streakList.remove(arrayInd);
                blockedStallSum -= maxLen;
            }
            M--;
        }
        return blockedStallSum;
    }
}
