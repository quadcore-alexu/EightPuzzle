package quadcore.eightpuzzle.model.strategies;

import org.jetbrains.annotations.NotNull;
import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.LinkedList;
import java.util.List;

public abstract class PuzzleSolver {

    protected TreeNode<State> searchTree;
    protected List<State> solution;

    public abstract boolean solve(State initialState);

    public List<State> getSolution() {
        if (solution == null) throw new NullPointerException("Please call `solve()` before getting solution.");
        return solution;
    }

    public TreeNode<State> getSearchTree() {
        if (searchTree == null) throw new NullPointerException("Please call `solve()` before getting search tree.");
        return searchTree;
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

}
