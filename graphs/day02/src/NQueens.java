import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NQueens {

    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[][] copyOf(char[][] A) {
        char[][] B = new char[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    public static List<char[][]> nQueensSolutions(int n) {
        //board is n x n with n Queens
        //create board of all '.'
        char[][] currBoard = new char[n][n];
        for (int rowInd = 0; rowInd < currBoard.length; rowInd++) {
            for (int colInd = 0; colInd < currBoard.length; colInd++) {
                currBoard[rowInd][colInd] = '.';
            }
        }
        List<char[][]> bob = placeQueens(new ArrayList<>(), currBoard, n, 0, 0);
        return bob;
    }

    public static List<char[][]> placeQueens(List<char[][]> allBoards, char[][] currBoard, int queensLeft, int startRowInd, int startColInd) {
        if (queensLeft == 0) {
            char[][] bob = copyOf(currBoard);
            allBoards.add(bob);
            return allBoards;
        }
        for (int rowInd = startRowInd; rowInd < currBoard.length; rowInd++) {
            for (int colInd = startColInd; colInd < currBoard.length; colInd++) {
                if (isValid(rowInd, colInd, currBoard)) {
                    currBoard[rowInd][colInd] = 'Q';
                    placeQueens(allBoards, currBoard, queensLeft - 1, rowInd, colInd);
                    currBoard[rowInd][colInd] = '.';
                }
                startColInd = 0; //reset to 0 so we don't skip columns
            }
        }
        return allBoards;
    }

    public static Boolean isValid(int rowInd, int colInd, char[][] currBoard) {
        //check row
        for (char rowItem: currBoard[rowInd]) {
            if (rowItem == 'Q') {
                return false;
            }
        }
        //check column
        for (char[] row: currBoard) {
            char colItem = row[colInd];
            if (colItem == 'Q') {
                return false;
            }
        }
        //IMPORTANT: both diagonals move left to right, but one goes high-low and the other low-high
        //check up-down diagonal
        int xTop = rowInd;
        int yTop = colInd;
        for (; xTop > 0 && yTop > 0; xTop--) { //back up to top left
            yTop--; //y's are kind of backwards, but it doesn't matter as long as I'm consistent
        }
        for (; xTop < currBoard.length && yTop < currBoard.length; xTop++) {
            if (currBoard[xTop][yTop] == 'Q') {
                return false;
            }
            yTop++;
        }
        //check down-up diagonal
        int xBot = rowInd;
        int yBot = colInd;
        for (; xBot > 0 && yBot < currBoard.length - 1; xBot--) { //move down to bottom left
            yBot++;
        }
        for (; xBot < currBoard.length && yBot > 0; xBot++) {
            if (currBoard[xBot][yBot] == 'Q') {
                return false;
            }
            yBot--;
        }
        return true;
    }

}
