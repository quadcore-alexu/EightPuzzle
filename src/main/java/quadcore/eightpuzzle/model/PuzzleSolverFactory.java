package quadcore.eightpuzzle.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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

    /**
     * Creates a PuzzleSolver instance based on the string passed.
     *
     * @param type the string specifying the type of PuzzleSolver to create.
     * @return a new PuzzleSolver instnce.
     */
    @Contract("_ -> new")
    public static @NotNull PuzzleSolver createPuzzleSolver(String type) {
        Strategy strategy = getType(type);
        switch (strategy) {
            case BFS:
                return new BFS();
            case DFS:
                return new DFS();
            case ASTAR:
                if (type.toLowerCase().contains("manhattan")) return new AStar(ManhattanHeuristic.getInstance());
                if (type.toLowerCase().contains("euclidean")) return new AStar(EuclideanHeuristic.getInstance());
                throw new UnsupportedOperationException("This A* heuristic isn't supported");
            default:
                throw new UnsupportedOperationException("This strategy for puzzle solving isn't supported");
        }
    }

    private static Strategy getType(String type) {
        if (type == null) throw new IllegalArgumentException("Null strategy type passed");
        type = type.toLowerCase().trim();
        if (type.matches(".*\\bbfs\\b.*")) return Strategy.BFS;
        if (type.matches(".*\\bdfs\\b.*")) return Strategy.DFS;
        if (type.matches(".*\\b(astar|a star)\\b.*") || type.contains("a*") || type.contains("A*")) return Strategy.ASTAR;
        throw new UnsupportedOperationException("This strategy for puzzle solving isn't supported");
    }
}
