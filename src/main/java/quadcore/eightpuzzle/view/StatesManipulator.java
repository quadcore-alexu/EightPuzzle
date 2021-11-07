package quadcore.eightpuzzle.view;

public class StatesManipulator {

    private String[] states;
    private int currentState;

    public StatesManipulator(String[] states) {
        this.states = states;
        currentState = 0;
    }

    /**
     * Detects whether there is a next state or not
     */
    public boolean hasNext() {
        if (currentState < states.length-1)
            return true;
        return false;
    }

    /**
     * Detects whether there is a previous state or not
     */
    public boolean hasPrev() {
        if (currentState > 0)
            return true;
        return false;
    }

    /**
     * Get moving tile index and its direction of motion
     * To return to next state
     */
    public int[] nextMove() {
        if (!hasNext())
            return null;
        int[] move = move(states[currentState], states[currentState+1]);
        currentState++;
        return move;
    }

    /**
     * Get moving tile index and its direction of motion
     * To return to previous state
     */
    public int[] previousMove() {
        if (!hasPrev())
            return null;
        int[] move = move(states[currentState], states[currentState-1]);
        currentState--;
        return move;
    }

    /**
     * Given two states determines the moving tile and its direction of motion
     * @param currentState board current state
     * @param nextState board upcoming state
     */
    private int[] move(String currentState, String nextState) {
        int fromIndex = getZeroPosition(nextState);
        int toIndex = getZeroPosition(currentState);
        int yDir =  (toIndex-fromIndex)/3;
        int xDir =  yDir == 0 ? toIndex-fromIndex : 0;
        return new int[] {fromIndex, xDir, yDir};
    }

    /**
     * Given a string returns zero's index (empty tile index)
     * @param state board state
     */
    private int getZeroPosition(String state) {
        for (int i = 0; i < state.length(); i++) {
            if (state.charAt(i) == '0')
                return i;
        }
        return -1;
    }
}
