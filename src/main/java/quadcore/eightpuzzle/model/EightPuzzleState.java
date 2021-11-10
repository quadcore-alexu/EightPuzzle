package quadcore.eightpuzzle.model;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EightPuzzleState implements State {

    private final String state;
    private final Point zeroPos;

    /**
     * Creates a valid eight puzzle state from the given state representation.
     *
     * @param stateRepresentation the string representation of the state.
     */
    public EightPuzzleState(String stateRepresentation) {
        System.out.println(stateRepresentation);
        if (!State.isValid(stateRepresentation)) throw new IllegalArgumentException("Initial state is inconsistent");
        state = stateRepresentation;
        int index = stateRepresentation.indexOf('0');
        zeroPos = new Point(index % 3, index / 3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGoal() {
        return getAsString().equals("012345678");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<State> getPossibleNextStates() {
        List<State> nextStates = new ArrayList<>(4);

        int x = zeroPos.x;
        int y = zeroPos.y;

        //left
        if (x > 0) nextStates.add(getNextState(new Point(x, y), new Point(x - 1, y)));
        //right
        if (x < 2) nextStates.add(getNextState(new Point(x, y), new Point(x + 1, y)));
        //up
        if (y > 0) nextStates.add(getNextState(new Point(x, y), new Point(x, y - 1)));
        //down
        if (y < 2) nextStates.add(getNextState(new Point(x, y), new Point(x, y + 1)));

        return nextStates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsString() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EightPuzzleState)
            return state.equals(((EightPuzzleState) obj).state);
        return false;
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Contract("_, _ -> new")
    private @NotNull State getNextState(@NotNull Point p1, @NotNull Point p2) {
        StringBuilder stringBuilder = new StringBuilder(getAsString());
        int firstIndex = 3 * p1.y + p1.x;
        char first = stringBuilder.charAt(firstIndex);
        int secondIndex = 3 * p2.y + p2.x;
        char second = stringBuilder.charAt(secondIndex);
        stringBuilder.replace(firstIndex, firstIndex + 1, String.valueOf(second));
        stringBuilder.replace(secondIndex, secondIndex + 1, String.valueOf(first));
        return new EightPuzzleState(stringBuilder.toString());
    }

}
