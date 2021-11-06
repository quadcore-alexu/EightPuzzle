package quadcore.eightpuzzle.model.strategies;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.*;

public class DFS implements PuzzleSolver {

    private TreeNode<State> searchTree;
    private List<State> solution;

    @Override
    public boolean solve(State initialState) {
        Deque<State> frontier = new ArrayDeque<>();
        Set<State> explored = new HashSet<>();
        Map<State, TreeNode<State>> parents = new HashMap<>();

        searchTree = new TreeNode<>(initialState);
        explored.add(initialState);
        parents.put(initialState, searchTree);
        if (initialState.isGoal()) {
            solution = getSolFromTree(searchTree);
            return true;
        }
        for (State neighbour : initialState.getPossibleNextStates()) {
            frontier.push(neighbour);
            parents.put(neighbour, searchTree);
        }

        while (!frontier.isEmpty()) {
            State state = frontier.pop();
            TreeNode<State> node = new TreeNode<>(state);
            explored.add(state);
            parents.get(state).addChild(node);

            if (state.isGoal()) {
                solution = getSolFromTree(node);
                return true;
            }
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

    @Override
    public List<State> getSolution() {
        if (solution == null) throw new NullPointerException("Please call `solve()` before getting solution.");
        return solution;
    }

    @Override
    public TreeNode<State> getSearchTree() {
        if (searchTree == null) throw new NullPointerException("Please call `solve()` before getting search tree.");
        return searchTree;
    }

    private List<State> getSolFromTree(TreeNode<State> goal) {
        List<State> sol = new LinkedList<>();
        TreeNode<State> node = goal;
        do {
            sol.add(0, node.getValue());
            node = node.getParent();
        } while (node != null);
        return sol;
    }
}
