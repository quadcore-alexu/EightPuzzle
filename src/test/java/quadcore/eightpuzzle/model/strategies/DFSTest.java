package quadcore.eightpuzzle.model.strategies;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import quadcore.eightpuzzle.model.EightPuzzleState;
import quadcore.eightpuzzle.model.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    @Disabled("takes too long")
    @Test
    void solve() {
        PuzzleSolver puzzleSolver = new DFS();

        String str = "105682347";
        State state = new EightPuzzleState(str);
        boolean res = puzzleSolver.solve(state);
        assertTrue(res);

        List<State> solution = puzzleSolver.getSolution();
        assertEquals(str, solution.get(0).getAsString());
        assertEquals("012345678", solution.get(solution.size() - 1).getAsString());
    }

}
