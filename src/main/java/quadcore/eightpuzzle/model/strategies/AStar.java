package quadcore.eightpuzzle.model.strategies;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.List;
import java.util.function.Function;

public class AStar implements PuzzleSolver {

    public AStar(Function<State, Integer> heuristic) {
        //todo: implementation & pass the heuristic function in the constructor
    }

    @Override
    public boolean solve(State initialState) {
        //todo: implementation
        return false;
    }

    @Override
    public List<State> getSolution() {
        //todo: implementation
        return null;
    }

    @Override
    public TreeNode<State> getSearchTree() {
        //todo: implementation
        return null;
    }
}
