package quadcore.eightpuzzle.model;

import org.junit.jupiter.api.Test;
import quadcore.eightpuzzle.model.strategies.AStar;
import quadcore.eightpuzzle.model.strategies.BFS;
import quadcore.eightpuzzle.model.strategies.DFS;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleSolverFactoryTest {

    @Test
    void createPuzzleSolver() {
        PuzzleSolver puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("DFS");
        assertTrue(puzzleSolver instanceof DFS);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("dfs");
        assertTrue(puzzleSolver instanceof DFS);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("BFS");
        assertTrue(puzzleSolver instanceof BFS);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("bfs");
        assertTrue(puzzleSolver instanceof BFS);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("A* with manhattan");
        assertTrue(puzzleSolver instanceof AStar);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("A* with euclidean");
        assertTrue(puzzleSolver instanceof AStar);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("astar with manhattan");
        assertTrue(puzzleSolver instanceof AStar);
        puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("A star with euclidean");
        assertTrue(puzzleSolver instanceof AStar);
        assertThrows(UnsupportedOperationException.class,
                () -> PuzzleSolverFactory.createPuzzleSolver("A* with test"));

    }
}
