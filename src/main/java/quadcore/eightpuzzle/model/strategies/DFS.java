package quadcore.eightpuzzle.model.strategies;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;
import java.util.logging.Logger;

public class DFS extends PuzzleSolver {

    private final Logger logger = Logger.getLogger("DFS");

    @Override
    public boolean solve(State initialState) {
        if (initialState == null) throw new NullPointerException("Initial state is null");

        Deque<State> frontier = new LinkedList<>();
        Set<State> explored = new HashSet<>();
        Map<State, TreeNode<State>> parents = new HashMap<>();

        frontier.push(initialState);

        while (!frontier.isEmpty()) {
            State state = frontier.pop();
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

            //add neighbours to stack
            List<State> neighbours = state.getPossibleNextStates();
            for (State neighbour : neighbours) {
                if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
                    frontier.push(neighbour);
                    parents.put(neighbour, node);
                }
            }
        }
        return false;
    }

}
