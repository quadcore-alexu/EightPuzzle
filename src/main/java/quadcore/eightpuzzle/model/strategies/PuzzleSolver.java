package quadcore.eightpuzzle.model.strategies;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.List;

public interface PuzzleSolver {

    boolean solve(State initialState);

    List<State> getSolution();

    TreeNode<State> getSearchTree();

}
