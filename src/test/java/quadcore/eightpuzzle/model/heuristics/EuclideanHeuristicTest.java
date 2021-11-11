package quadcore.eightpuzzle.model.heuristics;

import org.junit.jupiter.api.Test;
import quadcore.eightpuzzle.model.EightPuzzleState;
import quadcore.eightpuzzle.model.State;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EuclideanHeuristicTest {

    @Test
    void applyAsDouble() {
        String str = "102345678";
        State state = new EightPuzzleState(str);
        assertEquals(1, EuclideanHeuristic.getInstance().applyAsDouble(state));
    }

}
