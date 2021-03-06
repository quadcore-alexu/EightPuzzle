package quadcore.eightpuzzle.model.strategies;

import org.javatuples.Pair;
import quadcore.eightpuzzle.model.Game;
import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;

public class BFS extends PuzzleSolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean solve(State initialState) {
        reset();
        if (!initialState.isSolvable()) return false;

        Game.LOGGER.info("Starting " + this.getClass().getSimpleName());

        Queue<State> frontier = new LinkedList<>();
        Map<State, Pair<TreeNode<State>, Integer>> map = new HashMap<>();

        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            State state = frontier.poll();
            numberOfNodesExpanded++;
            int currentDepth = 0;
            TreeNode<State> node = new TreeNode<>(state);

            if (state == initialState) {
                map.put(initialState, Pair.with(node, currentDepth));
                searchTree = node;
            } else {
                map.get(state).getValue0().addChild(node);
                currentDepth = map.get(state).getValue1();
                updateMaxDepth(currentDepth);
            }

            Game.LOGGER.debug("Exploring: " + state.getAsString() + " at depth " + currentDepth);

            //if goal
            if (state.isGoal()) {
                goal = node;
                goalDepth = currentDepth;
                solution = getSolFromTree(node);
                logSolution(solution);
                return true;
            }

            //add neighbours
            List<State> neighbours = state.getPossibleNextStates();
            for (State neighbour : neighbours) {
                if (!map.containsKey(neighbour)) {
                    frontier.add(neighbour);
                    map.put(neighbour, Pair.with(node, currentDepth + 1));
                }
            }
        }
        return false;
    }

}
