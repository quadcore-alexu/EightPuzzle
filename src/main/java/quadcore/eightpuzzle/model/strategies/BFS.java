package quadcore.eightpuzzle.model.strategies;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;
import java.util.logging.Logger;

public class BFS extends PuzzleSolver {

    private final Logger logger = Logger.getLogger("BFS");

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
            logger.info(() -> "Exploring: " + state.getAsString());
            TreeNode<State> node = new TreeNode<>(state);

            //add to search tree
            if (state == initialState) {
                parents.put(initialState, node);
                searchTree = node;
            } else parents.get(state).addChild(node);

            //if goal
            if (state.isGoal()) {
                solution = getSolFromTree(node);
                return true;
            }

            //add neighbours
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
