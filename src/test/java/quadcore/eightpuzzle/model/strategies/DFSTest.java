package quadcore.eightpuzzle.model.strategies;

import org.junit.jupiter.api.Test;
import quadcore.eightpuzzle.model.EightPuzzleState;
import quadcore.eightpuzzle.model.State;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    @Test
    void solve() {
        PuzzleSolver puzzleSolver = new DFS();

        State state = new EightPuzzleState("105682347");
        boolean res = puzzleSolver.solve(state);
        assertTrue(res);
    }

}
