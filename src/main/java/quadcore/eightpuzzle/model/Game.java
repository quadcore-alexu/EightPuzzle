package quadcore.eightpuzzle.model;

import quadcore.eightpuzzle.model.datastructures.TreeNode;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

import java.util.List;

public class Game {

    private PuzzleSolver puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("BFS");
    private State initialState;

    public void setInitialState(String stateRepresentation) throws IllegalArgumentException {
        State state = new EightPuzzleState(stateRepresentation);
        if (!state.isValid()) throw new IllegalArgumentException("State is inconsistent");
        this.initialState = state;
        puzzleSolver.setInitialState(state);
    }

    public void setPuzzleSolver(PuzzleSolver puzzleSolver) {
        this.puzzleSolver = puzzleSolver;
        puzzleSolver.setInitialState(initialState);
    }

    public boolean solve() throws NullPointerException {
        if (initialState == null) throw new NullPointerException("Initial state not set");
        return puzzleSolver.solve();
    }

    public List<State> getSolution() {
        return puzzleSolver.getSolution();
    }

    public TreeNode<State> getSearchTree() {
        return puzzleSolver.getSearchTree();
    }

}
