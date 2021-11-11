package quadcore.eightpuzzle.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import quadcore.eightpuzzle.model.datastructures.TreeNode;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

import java.util.List;

public class Game {

    public static final Logger LOGGER = LogManager.getLogger(Game.class.getName());

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
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver(strategy);
    }

    /**
     * Starts solving the game.
     *
     * @return True if a solution is found. False otherwise.
     */
    public boolean solve(String initialState) {
        State state = new EightPuzzleState(initialState);
        return puzzleSolver.solve(state);
    }

    /**
     * Gets the solution as a list of states, where each state can be reached directly from the previous one.
     * The first is the initial state, and the last is the goal state.
     * This should be called after {@code `solve()`}.
     *
     * @return the solution as a list of states.
     */
    public List<State> getSolution() {
        return puzzleSolver.getSolution();
    }

    /**
     * Gets the search tree used by the pre-specified strategy.
     * This should be called after {@code `solve()`}.
     *
     * @return the root of the tree as a tree node.
     */
    public TreeNode<State> getSearchTree() {
        return puzzleSolver.getSearchTree();
    }

    /**
     * Gets the goal tree node from the search tree.
     * This should be called after {@code `solve()`}.
     *
     * @return the goal tree node.
     */
    public TreeNode<State> getGoal() {
        return puzzleSolver.getGoal();
    }

    /**
     * Gets the maximum depth reached by the used puzzle solver strategy.
     *
     * @return the maximum depth of the search tree.
     */
    public int getMaxDepth() {
        return puzzleSolver.getMaxDepth();
    }

    /**
     * Gets the depth at which the goal was found.
     *
     * @return the depth of the goal.
     */
    public int getGoalDepth() {
        return puzzleSolver.getGoalDepth();
    }

    /**
     * @return the number of nodes expanded by the puzzle solver while doing the search.
     */
    public int getNumberOfNodesExpanded() {
        return puzzleSolver.getNumberOfNodesExpanded();
    }

}
