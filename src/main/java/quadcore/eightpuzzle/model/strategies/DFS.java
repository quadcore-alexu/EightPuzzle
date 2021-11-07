package quadcore.eightpuzzle.model.strategies;

import org.javatuples.Pair;
import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;
import java.util.logging.Logger;

public class DFS extends PuzzleSolver {

    private final Logger logger = Logger.getLogger("DFS");

    @Override
    public boolean solve(State initialState) {
        reset();
        if (initialState == null) throw new NullPointerException("Initial state is null");

        Deque<State> frontier = new LinkedList<>();
        Set<State> explored = new HashSet<>();
        Map<State, Pair<TreeNode<State>, Integer>> map = new HashMap<>();

        frontier.push(initialState);

        while (!frontier.isEmpty()) {
            State state = frontier.pop();
            explored.add(state);
            logger.info(() -> "Exploring: " + state.getAsString());
            TreeNode<State> node = new TreeNode<>(state);

            //add to search tree
            if (state == initialState) {
                map.put(initialState, Pair.with(node, 0));
                searchTree = node;
            } else {
                map.get(state).getValue0().addChild(node);
                updateMaxDepth(map.get(state).getValue1());
            }

            //if goal
            if (state.isGoal()) {
                solution = getSolFromTree(node);
                return true;
            }

            //add neighbours to stack
            List<State> neighbours = state.getPossibleNextStates();
            int depth = map.get(state).getValue1();
            for (State neighbour : neighbours) {
                if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
                    frontier.push(neighbour);
                    map.put(neighbour, Pair.with(node, depth + 1));
                }
            }
        }
        return false;
    }

}
