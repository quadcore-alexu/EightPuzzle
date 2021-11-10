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

    public abstract boolean solve(State initialState);

    public List<State> getSolution() {
        if (solution == null) throw new NullPointerException("Please call `solve()` before getting solution.");
        return solution;
    }

    public TreeNode<State> getSearchTree() {
        if (searchTree == null) throw new NullPointerException("Please call `solve()` before getting search tree.");
        return searchTree;
    }

    public TreeNode<State> getGoal() {
        if (goal == null) throw new NullPointerException("Please call `solve()` before getting goal.");
        return goal;
    }

    public int getGoalDepth() {
        if (goalDepth == -1) throw new NullPointerException("Please call `solve()` before getting goal depth.");
        return goalDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    protected void updateMaxDepth(int depth) {
        if (depth > maxDepth) maxDepth = depth;
    }

    protected @NotNull List<State> getSolFromTree(TreeNode<State> goal) {
        List<State> sol = new LinkedList<>();
        TreeNode<State> node = goal;
        do {
            sol.add(0, node.getValue());
            node = node.getParent();
        } while (node != null);
        return sol;
    }

    protected void logSolution() {
        Game.LOGGER.info("Found solution at depth = " + getGoalDepth());
        StringBuilder stringBuilder = new StringBuilder();
        for (State state : solution) {
            stringBuilder.append(state.getAsString()).append(" -> ");
        }
        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        Game.LOGGER.info("Goal path: " + stringBuilder);
    }

    protected void reset() {
        searchTree = null;
        goal = null;
        goalDepth = -1;
        solution = null;
        maxDepth = 0;
    }
}
