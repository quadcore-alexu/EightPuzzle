package quadcore.eightpuzzle.model;

import java.util.List;

public interface State {

    /**
     * Checks if the passed string can make a valid state.
     *
     * @param state string to be validated.
     * @return true if the passed string can make a valid state, false otherwise.
     */
    static boolean isValid(String state) {
        if (state == null) throw new NullPointerException("State to be validated is null");
        //this regex ensures that it has only nine digits [0-8] and that each digit occurs only once
        return state.matches("^(?!.*(.).*\\1)[0-8]{9}");
    }

    /**
     * Checks if the passed state can be solved.
     * This is achieved by counting the number of inversions in the string representation of the state.
     *
     * @param state to be tested if solvable.
     * @return true if the passed state can be solved, false otherwise.
     */
    static boolean isSolvable(State state) {
        if (state == null) throw new NullPointerException("Initial state is null");
        String stateStr = state.getAsString();
        int invCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                int c1 = Character.getNumericValue(stateStr.charAt(i));
                int c2 = Character.getNumericValue(stateStr.charAt(j));
                if (c1 != 0 && c2 != 0 && c1 > c2) invCount++;
            }
        }
        return invCount % 2 == 0;
    }

    /**
     * Checks whether this state is a goal.
     *
     * @return true if this is a goal state, false otherwise.
     */
    boolean isGoal();

    /**
     * Gets all next states that can be reached with one move from this state.
     *
     * @return a list of all possible next states.
     */
    List<State> getPossibleNextStates();

    /**
     * @return the string representation of the state.
     */
    String getAsString();

}
