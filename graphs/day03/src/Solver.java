/**
 * Solver definition for the 8 Puzzle challenge
 * Construct a tree of board states using A* to find a path to the goal
 */

import java.util.*;

public class Solver {

    public int minMoves = -1;
    private State solutionState;
    private boolean solved = false;
    private Board currBoard;

    /**
     * State class to make the cost calculations simple
     * This class holds a board state and all of its attributes
     */
    private class State implements Comparable<State>{
        // Each state needs to keep track of its cost and the previous state
        private Board board;
        private int moves;
        public int cost;
        private State prev;

        public State(Board board, int moves, State prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            // TODO: Compute cost of board state according to A*
            cost = this.moves + this.board.manhattan();
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).board.equals(this.board);
        }

        /*
         * Return the cost difference between two states
         */
        @Override
        public int compareTo(State s) {
            return this.cost - s.cost;
        }
    }

    /*
     * Return the root state of a given state
     */
    private State root(State state) {
    	// TODO: Uma's code here
        State s = state;
        while (s.prev != null) {
            s = s.prev;
        }
        return s;
    }

    /*
     * A* Solver
     * Find a solution to the initial board using A* to generate the state tree
     * and a identify the shortest path to the the goal state
     */
    public Solver(Board initial) {
    	// TODO: Your code here
        this.currBoard = initial; //TODO: WHY DO WE UPDATE THIS IF WE AREN'T GOING TO USE IT?
        HashSet<State> availableStates = new HashSet<>(); //store States we haven't been to yet
        HashSet<State> closedStates = new HashSet<>();
        State startState = new State(initial, 0, null);
        availableStates.add(startState); //make sure list isn't empty to start with

        while (availableStates.size() != 0) {
            //GET CLOSEST STATE AND POP IT
            State nearestState = Collections.min(availableStates);
            availableStates.remove(nearestState);

            //GET NEIGHBOR FRIENDS
            List<Board> neighborFriends = (List<Board>) nearestState.board.neighbors();
            for (Board neighbor: neighborFriends) {
                State neighborState = new State(neighbor, nearestState.moves + 1, nearestState);
                boolean ignore = false;
                //CHECK IF GOAL
                if (neighborState.board.isGoal()) {
                    minMoves = neighborState.moves;
                    solutionState = neighborState;
                    availableStates.clear();
                    ignore = true;
                }
                for (State openState: availableStates) {
                    if (openState.equals(neighborState) && openState.cost < neighborState.cost) {
                        ignore = true;
                    }
                }
                for (State closedState: closedStates) {
                    if (closedState.equals(neighborState) && closedState.cost < neighborState.cost) {
                        ignore = true;
                    }
                }
                if (!ignore) {
                    availableStates.add(neighborState);
                }
            }
            closedStates.add(nearestState);
        }
        solved = true;
    }

    /*
     * Is the input board a solvable state
     */
    public boolean isSolvable() {
    	// TODO: Your code here
        return this.currBoard.solvable(currBoard.tiles);
    }

    /*
     * Return the sequence of boards in a shortest solution, null if unsolvable
     */
    public Iterable<Board> solution() {
    	// TODO: Your code here
        //CHECK IF GOAL
        List<Board> solutionsList = new ArrayList<>();
        while (solutionState.prev != null) {
            solutionsList.add(0, solutionState.board);
            solutionState = solutionState.prev;
        }
        return solutionsList;
    }

    public State find(Iterable<State> iter, Board b) {
        for (State s : iter) {
            if (s.board.equals(b)) {
                return s;
            }
        }
        return null;
    }

    /*
     * Debugging space: Your solution can have whatever output you find useful
     * Optional challenge: create a command line interaction for users to input game
     * states
     */
    public static void main(String[] args) {
        int[][] initState = {{8, 6, 7}, {2, 5, 4}, {3, 0, 1}};
        Board initial = new Board(initState);

        // Solve the puzzle
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            System.out.println("No solution");
        else {
            for (Board board : solver.solution()) {
                board.printBoard();
            }
            System.out.println(solver.minMoves);
        }
    }


}
