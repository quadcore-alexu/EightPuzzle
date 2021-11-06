package quadcore.eightpuzzle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class EightPuzzleState implements State {

    private final BitSet bitSet = new BitSet(36);
    private int zeroPos;

    public EightPuzzleState(String stateRepresentation) {
        zeroPos = stateRepresentation.indexOf('0');
        //todo
    }

    @Override
    public boolean isValid() {
        //this regex ensures that it has only ten digits and that each digit occurs only once
        return getAsString().matches("^(?!.*(.).*\\1)\\d{10}");
    }

    @Override
    public boolean isGoal() {
        return getAsString().equals("0123456789");
    }

    @Override
    public List<State> getPossibleNextStates() {
        List<State> next = new ArrayList<>(4);
        return null;
    }

    @Override
    public String getAsString() {
        //todo: implementation
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EightPuzzleState)
            return Arrays.equals(this.bitSet.toLongArray(), ((EightPuzzleState) obj).bitSet.toLongArray());
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(bitSet.toLongArray());
    }

}
