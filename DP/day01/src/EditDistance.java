import java.util.ArrayList;
import java.util.Arrays;

public class EditDistance {

    public static int minEditDist(String a, String b) {
        //create table
        int[][] table= new int[a.length() + 1][b.length() + 1];

        //take bottom-up approach
        int width = table.length; //a
        int height = table[0].length; //b

        //account for empty string
        for (int y = 0; y < height; y++) {
            table[0][y] = y;
        }
        for (int x = 0; x < width; x++) {
            table[x][0] = x;
        }

        for (int x = 1; x < width; x++) { //start in top left corner, one column over
            for (int y = 1; y < height; y++) {
                //initialize box values
                int boxAboveVal = Integer.MAX_VALUE; //replace
                int boxLeftVal = Integer.MAX_VALUE; //insert
                int boxDiagVal = Integer.MAX_VALUE; //remove
                //update box values if possible
                if (y > 1) boxAboveVal = table[x][y - 1];
                if (x > 1) boxLeftVal = table[x - 1][y];
                if (x > 1 && y > 1) boxDiagVal = table[x - 1][y - 1];
                //find lowest box value
                int lowestCost = Math.min(boxAboveVal, Math.min(boxLeftVal, boxDiagVal));
                //update table
                if (a.charAt(x - 1) == b.charAt(y - 1) && lowestCost == Integer.MAX_VALUE) table[x][y] = 0; //if we're in the top left corner and they don't match up
                else if (lowestCost == Integer.MAX_VALUE) table[x][y] = 1;
                else if (a.charAt(x - 1) == b.charAt(y - 1)) table[x][y] = boxDiagVal;
                else table[x][y] = lowestCost + 1;
            }
        }

        return table[width - 1][height - 1];
    }
}
