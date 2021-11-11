package quadcore.eightpuzzle.model.strategies;

import org.javatuples.Pair;
import quadcore.eightpuzzle.model.Game;
import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class AStar extends PuzzleSolver{

    private final ToDoubleFunction<State> heuristic;

    public AStar(ToDoubleFunction<State> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public boolean solve(State initialState) {
        reset();
        if (!initialState.isSolvable()) return false;

        Game.LOGGER.info("Starting " + this.getClass().getSimpleName() + " with " + heuristic.getClass().getSimpleName());

        Map<State, Pair<TreeNode<State>, Integer>> map = new HashMap<>();

        PriorityQueue<Pair<State, Double>> frontier = new PriorityQueue<>((o1, o2) -> {
            double cost1 = o1.getValue1();
            double cost2 = o2.getValue1();
            if (cost1 > cost2)
                return 1;
            else if (cost1 < cost2)
                return -1;
            return 0;

        });

        Set<State> explored = new HashSet<>();

        frontier.add(Pair.with(initialState, heuristic.applyAsDouble(initialState)));

        while (!frontier.isEmpty()){
            State state = frontier.poll().getValue0();
            numberOfNodesExpanded++;
            explored.add(state);
            TreeNode<State> node = new TreeNode<>(state);
            int currentDepth = 0;
            if (state == initialState) {
                map.put(initialState, Pair.with(node, currentDepth));
                searchTree = node;
            } else {
                map.get(state).getValue0().addChild(node);
                currentDepth = map.get(state).getValue1();
                updateMaxDepth(currentDepth);
            }

            Game.LOGGER.debug("Exploring: " + state.getAsString() + " at depth " + currentDepth);

            if (state.isGoal()) {
                goal = node;
                goalDepth = currentDepth;
                solution = getSolFromTree(node);
                logSolution(solution);
                return true;
            }
            List<State> neighbours = state.getPossibleNextStates();
            for (State neighbour : neighbours) {
                if (!map.containsKey(neighbour)){
                    map.put(neighbour, Pair.with(node, currentDepth+1));
                    frontier.add(Pair.with(neighbour, heuristic.applyAsDouble(neighbour)+ currentDepth+1));
                }
                else if (!explored.contains(neighbour)){
                    int oldCost = map.get(neighbour).getValue1();
                    if (oldCost > currentDepth+1){
                        map.put(neighbour, Pair.with(node, currentDepth+1));
                        frontier.remove(Pair.with(neighbour, oldCost));
                        frontier.add(Pair.with(neighbour, heuristic.applyAsDouble(neighbour)+ currentDepth+1));
                    }
                }
            }
        }
        return false;
    }


}
