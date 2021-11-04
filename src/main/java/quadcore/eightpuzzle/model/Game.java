package quadcore.eightpuzzle.model;

import quadcore.eightpuzzle.model.datastructures.TreeNode;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

import java.util.List;

public class Game {

    //BFS is chosen as default because branching factor for eight puzzle game is low
    private PuzzleSolver puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("BFS");
    private State initialState;

    /**
     * Sets the initial state of the game.<br/>
     * Any of this method and {@code `setPuzzleSolver()`} can be used before the other. There's no specific order.
     * @param initialState the string representation of the initial state.
     * @throws IllegalArgumentException the state represented by the passed string is invalid.
     */
    public void setInitialState(String initialState) throws IllegalArgumentException {
        State state = new EightPuzzleState(initialState);
        if (!state.isValid()) throw new IllegalArgumentException("State is inconsistent");
        this.initialState = state;
        puzzleSolver.setInitialState(state);
    }

    /**
     * Sets the strategy to be used when solving the game.
     * For example: "BFS" or "DFS".
     * The string is case-insensitive and is checked using the {@code `String.contains()`} method.
     * That is, both "A star with manhattan" and "manhattan A*" will use the same strategy.<br/>
     * Any of this method and {@code `setPuzzleSolver()`} can be used before the other. There's no specific order.
     * @param strategy the strategy to be used in solving the game.
     */
    public void setPuzzleSolver(String strategy) {
        this.puzzleSolver = PuzzleSolverFactory.createPuzzleSolver(strategy);
        if (initialState != null) this.puzzleSolver.setInitialState(initialState);
    }

    /**
     * Starts solving the game.
     * @return True if a solution is found. False otherwise.
     * @throws NullPointerException if no initial state is specified.
     */
    public boolean solve() throws NullPointerException {
        if (initialState == null) throw new NullPointerException("Initial state not set");
        return puzzleSolver.solve();
    }

    /**
     * Gets the solution as a list of states, where each state can be reached directly from the previous one.
     * The first is the initial state, and the last is the goal state.
     * @return the solution as a list of states.
     * @throws NullPointerException if {@code `solve()`} method was not called or did not terminate successfully.
     */
    public List<State> getSolution() throws NullPointerException {
        return puzzleSolver.getSolution();
    }

    /**
     * Gets the search tree used by the pre-specified strategy.
     * @return the root of the tree as a tree node.
     * @throws NullPointerException if {@code `solve()`} method was not called or did not terminate successfully.
     */
    public TreeNode<State> getSearchTree() throws NullPointerException {
        return puzzleSolver.getSearchTree();
    }

}
