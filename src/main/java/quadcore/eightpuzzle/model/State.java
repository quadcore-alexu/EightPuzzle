package quadcore.eightpuzzle.model;

import java.util.List;

public interface State {

    boolean isValid();

    boolean isGoal();

    List<State> getPossibleNextStates();

    String getAsString();

}
