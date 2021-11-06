package quadcore.eightpuzzle.model;

import quadcore.eightpuzzle.model.datastructures.TreeNode;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

import java.util.List;

public class Game {

    //BFS is chosen as default because branching factor for eight puzzle game is low
    private PuzzleSolver puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("BFS");

    /**
     * Sets the strategy to be used when solving the game.
     * For example: "BFS" or "DFS".
     * The string is case-insensitive and is checked using the {@code `String.contains()`} method.
     * That is, both "A star with manhattan" and "manhattan A*" will use the same strategy.<br/>
     *
     * @param strategy the strategy to be used in solving the game.
     */
    public void setPuzzleSolver(String strategy) {
        this.puzzleSolver = PuzzleSolverFactory.createPuzzleSolver(strategy);
    }

    /**
     * Starts solving the game.
     *
     * @return True if a solution is found. False otherwise.
     */
    public boolean solve(String initialState) {
        State state = new EightPuzzleState(initialState);
        if (!state.isValid()) throw new IllegalArgumentException("State is inconsistent");
        return puzzleSolver.solve(state);
    }

    /**
     * Gets the solution as a list of states, where each state can be reached directly from the previous one.
     * The first is the initial state, and the last is the goal state.
     * This should be called after {@code `solve()`}.
     *
     * @return the solution as a list of states.
     * @throws NullPointerException if {@code `solve()`} method was not called or did not terminate successfully.
     */
    public List<State> getSolution() {
        return puzzleSolver.getSolution();
    }

    /**
     * Gets the search tree used by the pre-specified strategy.
     *
     * @return the root of the tree as a tree node.
     * This should be called after {@code `solve()`}.
     * @throws NullPointerException if {@code `solve()`} method was not called or did not terminate successfully.
     */
    public TreeNode<State> getSearchTree() {
        return puzzleSolver.getSearchTree();
    }

}
