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
     * Checks if the state can be solved.
     * This is achieved by counting the number of inversions in the string representation of the state.
     *
     * @return true if the state can be solved, false otherwise.
     */
    boolean isSolvable();

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
