package quadcore.eightpuzzle.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class EightPuzzleStateTest {

    @Test
    void isValidTest() {

        assertTrue(State.isValid("087654321"));
        assertTrue(State.isValid("628570431"));
        assertTrue(State.isValid("012345678"));
        assertFalse(State.isValid("012345677"));
        assertFalse(State.isValid("012345678p"));
        assertFalse(State.isValid("01234578"));
        assertFalse(State.isValid("012345789"));
        assertFalse(State.isValid("812043765"));
    }

    @Test
    void isSolvableTest() {
        assertFalse(State.isSolvable(new EightPuzzleState("812043765")));
        assertTrue(State.isSolvable(new EightPuzzleState("087654321")));
    }

    @Test
    void isGoal() {
        assertTrue(new EightPuzzleState("012345678").isGoal());
        assertFalse(new EightPuzzleState("102345678").isGoal());
    }

    @Test
    void getPossibleNextStates1() {
        String str = "628570431";
        State state = new EightPuzzleState(str);
        List<State> nextStates = state.getPossibleNextStates();
        assertTrue(nextStates.contains(new EightPuzzleState("620578431")));
        assertTrue(nextStates.contains(new EightPuzzleState("628507431")));
        assertEquals(3, nextStates.size());
    }

    @Test
    void getPossibleNextStates2() {
        String str = "185602347";
        State state = new EightPuzzleState(str);
        List<State> nextStates = state.getPossibleNextStates();
        assertTrue(nextStates.contains(new EightPuzzleState("105682347")));
        assertTrue(nextStates.contains(new EightPuzzleState("185620347")));
        assertTrue(nextStates.contains(new EightPuzzleState("185062347")));
        assertTrue(nextStates.contains(new EightPuzzleState("185642307")));
    }

    @Test
    void getPossibleNextStates3() {
        String str = "150682347";
        State state = new EightPuzzleState(str);
        List<State> nextStates = state.getPossibleNextStates();
        assertTrue(nextStates.contains(new EightPuzzleState("105682347")));
        assertTrue(nextStates.contains(new EightPuzzleState("152680347")));
    }

    @Test
    void getAsString() {
        String str = "087654321";
        State state = new EightPuzzleState(str);
        assertEquals(str, state.getAsString());
        str = "012345678";
        state = new EightPuzzleState(str);
        assertEquals(str, state.getAsString());
    }
}
