package quadcore.eightpuzzle.model.strategies;

import org.jetbrains.annotations.NotNull;
import quadcore.eightpuzzle.model.Game;
import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.LinkedList;
import java.util.List;

public abstract class PuzzleSolver {

    protected TreeNode<State> searchTree;
    protected TreeNode<State> goal;
    protected int goalDepth = -1;
    protected List<State> solution;
    protected int maxDepth = 0;

    /**
     * Solve the eight puzzle using this strategy and the given initial state.
     * In case this method returned true,
     * all the following getters will return meaningful values with no exceptions:<br/>
     * - getSolution<br/>
     * - getSearchTree<br/>
     * - getMaxDepth<br/>
     * - getGoal<br/>
     * - getGoalDepth<br/>
     *
     * @param initialState the start point of the search process.
     * @return true if a goal was found, false otherwise.
     */
    public abstract boolean solve(State initialState);

    /**
     * @return a list of states, where the first is the initial state, the last is a goal state,
     * and for any state i, i + 1 is possible next state of i.
     */
    public List<State> getSolution() {
        if (solution == null) throw new NullPointerException("Please call `solve()` before getting solution.");
        return solution;
    }

    /**
     * Used to get the whole expanded nodes while searching.
     *
     * @return the root tree node of the search tree. The root tree node always the initial state as its value.
     */
    public TreeNode<State> getSearchTree() {
        if (searchTree == null) throw new NullPointerException("Please call `solve()` before getting search tree.");
        return searchTree;
    }

    /**
     * @return the tree node containing the goal state. This node is part of the search tree.
     */
    public TreeNode<State> getGoal() {
        if (goal == null) throw new NullPointerException("Please call `solve()` before getting goal.");
        return goal;
    }

    /**
     * @return the depth at which the goal was found.
     */
    public int getGoalDepth() {
        if (goalDepth == -1) throw new NullPointerException("Please call `solve()` before getting goal depth.");
        return goalDepth;
    }

    /**
     * @return the maximum depth of the search tree.
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Used to increase the max depth reached.
     * The maximum depth is increased only if the passed depth > the current max depth.
     *
     * @param depth candidate for the new maximum depth.
     */
    protected void updateMaxDepth(int depth) {
        if (depth > maxDepth) maxDepth = depth;
    }

    /**
     * Gets the solution as a list of states.
     *
     * @param goal the tree node of the goal state. Part of the search tree.
     * @return list of states constituting the solution.
     */
    protected @NotNull List<State> getSolFromTree(TreeNode<State> goal) {
        List<State> sol = new LinkedList<>();
        TreeNode<State> node = goal;
        do {
            sol.add(0, node.getValue());
            node = node.getParent();
        } while (node != null);
        return sol;
    }

    /**
     * Logs the solution using the {@code Game.Logger}
     *
     * @param solution list of states to be logged.
     */
    protected void logSolution(List<State> solution) {
        Game.LOGGER.info("Found solution at depth = " + getGoalDepth());
        StringBuilder stringBuilder = new StringBuilder();
        for (State state : solution) {
            stringBuilder.append(state.getAsString()).append(" -> ");
        }
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        Game.LOGGER.info("Goal path: " + stringBuilder);
    }

    /**
     * Resets the following values:<br/>
     * - searchTree<br/>
     * - goal<br/>
     * - goalDepth<br/>
     * - solution<br/>
     * - maxDepth<br/>
     */
    protected void reset() {
        searchTree = null;
        goal = null;
        goalDepth = -1;
        solution = null;
        maxDepth = 0;
    }
}
