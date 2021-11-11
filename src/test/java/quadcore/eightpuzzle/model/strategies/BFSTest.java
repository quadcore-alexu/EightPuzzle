package quadcore.eightpuzzle.model.strategies;

import org.junit.jupiter.api.Test;
import quadcore.eightpuzzle.model.EightPuzzleState;
import quadcore.eightpuzzle.model.Game;
import quadcore.eightpuzzle.model.PuzzleSolverFactory;
import quadcore.eightpuzzle.model.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    @Test
    void solve() {
        PuzzleSolver puzzleSolver = new BFS();

        String str = "105682347"; //solvable
        State state = new EightPuzzleState(str);
        boolean res = puzzleSolver.solve(state);
        assertTrue(res);
        List<State> solution = puzzleSolver.getSolution();
        assertEquals(str, solution.get(0).getAsString());
        assertEquals("012345678", solution.get(solution.size() - 1).getAsString());

        str = "432650781"; //unsolvable
        state = new EightPuzzleState(str);
        res = puzzleSolver.solve(state);
        assertFalse(res);
        assertThrows(NullPointerException.class, puzzleSolver::getGoal);
        assertThrows(NullPointerException.class, puzzleSolver::getSolution);
        assertThrows(NullPointerException.class, puzzleSolver::getSearchTree);
        assertThrows(NullPointerException.class, puzzleSolver::getGoalDepth);
        assertThrows(NullPointerException.class, puzzleSolver::getMaxDepth);
    }

    @Test
    void getDepthTest() {
        PuzzleSolver puzzleSolver = new BFS();
        String str = "142305678";
        State state = new EightPuzzleState(str);
        boolean res = puzzleSolver.solve(state);
        assertTrue(res);
        assertEquals(2, puzzleSolver.getMaxDepth());
    }

    @Test
    void ali() {
        String str = "413270568";
        State state = new EightPuzzleState(str);
        PuzzleSolver puzzleSolver = PuzzleSolverFactory.createPuzzleSolver("BFS");
        boolean res = puzzleSolver.solve(state);
        assertTrue(res);
        Game.LOGGER.info("nodes expanded = " + puzzleSolver.getNumberOfNodesExpanded());
        Game.LOGGER.info("goal cost = " + puzzleSolver.getGoalDepth());
        Game.LOGGER.info("max depth = " + puzzleSolver.getMaxDepth());

    }
}
