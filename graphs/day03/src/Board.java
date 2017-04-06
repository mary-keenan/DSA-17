import java.util.ArrayList;
import static java.lang.Math.abs;

/**
 * Board definition for the 8 Puzzle challenge
 */
public class Board {

    private int n;
    public int[][] tiles;
    private int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    public int[][] goalPoints = {{2,2},{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1}}; //index is key; 0 is actually where 9 would go
    public int[] blankPoint = {0,0};

    /*
     * Set the global board size and tile state
     */
    public Board(int[][] b) {
        tiles = b;
        n = tiles.length;
    }

    /*
     * Size of the board (equal to 3 for 8 puzzle, but in theory the Board
     * class should  work for any puzzle size)
     */
    private int size() {
        return tiles.length;
    }

    /*
     * Sum of the manhattan distances between the tiles and the goal
     * Estimated cost from the current node to the goal for A* (h(n))
     */
    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                int num = tiles[row][col];
                if (num != 0) { //don't want no zeroes in here
                    int[] goalPoint = goalPoints[num];
                    int horizontalDist = abs(row - goalPoint[0]);
                    int verticalDist = abs(col - goalPoint[1]);
                    sum += horizontalDist + verticalDist;
                } else {
                    blankPoint = new int[]{row, col};
                }
            }
        }
        return sum;
    }

    /*
     * Compare the current state to the goal state
     */
    public boolean isGoal() {
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                if (tiles[row][col] != goal[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Returns true if the board is solvable
     */
    public boolean solvable(int[][] board) {
        // A pair of tiles form an inversion if the the values on tiles are in reverse order of their appearance in goal state.
        // It is not possible to solve an instance of 8 puzzle if number of inversions is odd in the input state.
        int[] tilesArr = boardToArray(board);
        int inversions = 0;
        for (int i = 0; i < tilesArr.length; i++) {
            for (int j = i + 1; j < tilesArr.length; j++) {
                if (tilesArr[i] > tilesArr[j]) {
                    inversions++;
                }
            }
        }
        return (inversions % 2 == 0);
    }

    /*
     * Return the neighboring boards in the state tree
     * One possible method: Create a duplicate board state, try moving the
     * blank space up, down, left, and right, and if the resulting board state
     * is valid, add it to an accumulator.
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborFriends = new ArrayList<>();
        int blankX = blankPoint[0];
        int blankY = blankPoint[1];

        //check up
        if (blankY > 0) {
            int[][] duplicateTilesUp = copyOf(tiles);
            int swapVal = duplicateTilesUp[blankX][blankY - 1];
            duplicateTilesUp[blankX][blankY - 1] = 0;
            duplicateTilesUp[blankX][blankY] = swapVal;
            Board boardUp = new Board(duplicateTilesUp);
            neighborFriends.add(boardUp);
        }

        //check left
        if (blankX > 0) {
            int[][] duplicateTilesLeft = copyOf(tiles);
            int swapVal = duplicateTilesLeft[blankX - 1][blankY];
            duplicateTilesLeft[blankX - 1][blankY] = 0;
            duplicateTilesLeft[blankX][blankY] = swapVal;
            Board boardLeft = new Board(duplicateTilesLeft);
            neighborFriends.add(boardLeft);
        }

        //check down
        if (blankY < 2) {
            int[][] duplicateTilesDown = copyOf(tiles);
            int swapVal = duplicateTilesDown[blankX][blankY + 1];
            duplicateTilesDown[blankX][blankY + 1] = 0;
            duplicateTilesDown[blankX][blankY] = swapVal;
            Board boardDown = new Board(duplicateTilesDown);
            neighborFriends.add(boardDown);
        }

        //check right
        if (blankX < 2) {
            int[][] duplicateTilesRight = copyOf(tiles);
            int swapVal = duplicateTilesRight[blankX + 1][blankY];
            duplicateTilesRight[blankX + 1][blankY] = 0;
            duplicateTilesRight[blankX][blankY] = swapVal;
            Board boardRight = new Board(duplicateTilesRight);
            neighborFriends.add(boardRight);
        }
        return neighborFriends;
    }

    /*
     * Prints out the board state nicely for debugging purposes
     */
    public void printBoard() {
        for (int[] tile : tiles) {
            for (int aTile : tile) System.out.print(aTile + " ");
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Check if this board equals a given board state
     */
    @Override
    public boolean equals(Object x) {
        // Check if the board equals an input Board object
        if (x == this) return true;
        if (x == null) return false;
        if (!(x instanceof Board)) return false;
        // Check if the same size
        Board y = (Board) x;
        if (y.tiles.length != n || y.tiles[0].length != n) {
            return false;
        }
        // Check if the same tile configuration
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != y.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates a deep copy of the input array and returns it
     */
    private static int[][] copyOf(int[][] A) {
        int[][] B = new int[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    private static int[] boardToArray(int[][] A) {
        // put in separate array for solvable method
        int i = 0;
        int [] tilesArr = new int[A.length * A.length];
        for (int[] row: A) {
            for (int item: row) {
                if (item != 0) {
                    tilesArr[i] = item;
                    i++;
                }
            }
        }
        return tilesArr;
    }

    public static void main(String[] args) {
        // DEBUG - Your solution can include whatever output you find useful
        int[][] initState = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(initState);
        board.printBoard();
//        Board copy = new Board (copyOf(board.tiles));
//        copy.printBoard();
        System.out.println("Size: " + board.size());
        System.out.println("Solvable: " + board.solvable(board.tiles));
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        Iterable<Board> it = board.neighbors();
        for (Board b : it)
            b.printBoard();
    }
}
