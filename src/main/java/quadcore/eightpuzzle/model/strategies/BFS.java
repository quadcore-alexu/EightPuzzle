package quadcore.eightpuzzle.model.strategies;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;

public class BFS extends PuzzleSolver {

    @Override
    public boolean solve(State initialState) {
        if (initialState == null) throw new NullPointerException("Initial state is null");

        Queue<State> frontier = new LinkedList<>();
        Set<State> explored = new HashSet<>();
        Map<State, TreeNode<State>> parents = new HashMap<>();

        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            State state = frontier.poll();
            explored.add(state);
            TreeNode<State> node = new TreeNode<>(state);

            if (state == initialState) {
                parents.put(initialState, node);
                searchTree = node;
            } else parents.get(state).addChild(node);

            if (state.isGoal()) {
                solution = getSolFromTree(node);
                return true;
            }

            List<State> neighbours = state.getPossibleNextStates();
            for (State neighbour : neighbours) {
                if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
                    frontier.add(neighbour);
                    parents.put(neighbour, node);
                }
            }
        }
        return false;
    }

}
