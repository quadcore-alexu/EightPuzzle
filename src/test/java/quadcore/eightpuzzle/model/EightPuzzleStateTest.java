package quadcore.eightpuzzle.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EightPuzzleStateTest {

    @Test
    void invalid() {
        assertTrue(State.isValid("087654321"));
        assertTrue(State.isValid("012345678"));
        assertFalse(State.isValid("012345677"));
        assertFalse(State.isValid("012345678p"));
        assertFalse(State.isValid("01234578"));
        assertFalse(State.isValid("012345789"));
    }

    @Test
    void isGoal() {
        assertTrue(new EightPuzzleState("012345678").isGoal());
        assertFalse(new EightPuzzleState("012346578").isGoal());
    }

    @Test
    void getPossibleNextStates1() {
        String str = "105682347";
        State state = new EightPuzzleState(str);
        List<State> nextStates = state.getPossibleNextStates();
        assertTrue(nextStates.contains(new EightPuzzleState("185602347")));
        assertTrue(nextStates.contains(new EightPuzzleState("150682347")));
        assertTrue(nextStates.contains(new EightPuzzleState("015682347")));
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
        String str = "087654231";
        State state = new EightPuzzleState(str);
        assertEquals(str, state.getAsString());
        str = "012345678";
        state = new EightPuzzleState(str);
        assertEquals(str, state.getAsString());
    }
}
