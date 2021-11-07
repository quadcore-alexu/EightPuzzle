package quadcore.eightpuzzle.model;

import java.util.List;

public interface State {

    static boolean isValid(String state) {
        if (state == null) throw new NullPointerException("State to be validated is null");
        //this regex ensures that it has only nine digits [0-8] and that each digit occurs only once
        return state.matches("^(?!.*(.).*\\1)[0-8]{9}");
    }

    boolean isGoal();

    List<State> getPossibleNextStates();

    String getAsString();

}
