package quadcore.eightpuzzle.model;

import quadcore.eightpuzzle.model.strategies.BFS;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

public class PuzzleSolverFactory {

    private PuzzleSolverFactory() {
    }

    public static PuzzleSolver createPuzzleSolver(String type) throws UnsupportedOperationException {
        //todo: implementation
        return new BFS();
    }
}
