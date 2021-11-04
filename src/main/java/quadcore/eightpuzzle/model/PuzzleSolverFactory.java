package quadcore.eightpuzzle.model;

import quadcore.eightpuzzle.model.heuristics.EuclideanHeuristic;
import quadcore.eightpuzzle.model.heuristics.ManhattanHeuristic;
import quadcore.eightpuzzle.model.strategies.AStar;
import quadcore.eightpuzzle.model.strategies.BFS;
import quadcore.eightpuzzle.model.strategies.DFS;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

public class PuzzleSolverFactory {

    private PuzzleSolverFactory() {
    }

    private enum Strategy {
        BFS,
        DFS,
        ASTAR
    }

    public static PuzzleSolver createPuzzleSolver(String type) {
        Strategy strategy = getType(type);
        switch (strategy) {
            case BFS:
                return new BFS();
            case DFS:
                return new DFS();
            case ASTAR:
                if (type.toLowerCase().contains("manhattan")) return new AStar(ManhattanHeuristic.getInstance());
                if (type.toLowerCase().contains("euclid")) return new AStar(EuclideanHeuristic.getInstance());
                throw new UnsupportedOperationException("Passed A* heuristic isn't supported");
            default:
                throw new UnsupportedOperationException("Passed strategy for puzzle solving isn't supported");
        }
    }

    private static Strategy getType(String type) {
        if (type == null) throw new IllegalArgumentException("Null strategy type passed");
        type = type.toLowerCase();
        if (type.matches("\\bbfs\\b")) return Strategy.BFS;
        if (type.matches("\\bdfs\\b")) return Strategy.DFS;
        if (type.matches("\\b(astar|a star)\\b") || type.contains("a*")) return Strategy.ASTAR;
        throw new UnsupportedOperationException("Passed strategy for puzzle solving isn't supported");
    }
}
