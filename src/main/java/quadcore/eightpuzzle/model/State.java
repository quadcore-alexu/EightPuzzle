package quadcore.eightpuzzle.model;

import java.util.List;

public interface State {

    static boolean isValid(String state) {
        if (state == null) throw new NullPointerException("State to be validated is null");
        //this regex ensures that it has only nine digits [0-8] and that each digit occurs only once
        return state.matches("^(?!.*(.).*\\1)[0-8]{9}") && isSolvable(state);
    }

    private static boolean isSolvable(String state) {
        int invCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                int c1 = Integer.parseInt(String.valueOf(state.charAt(i)));
                int c2 = Integer.parseInt(String.valueOf(state.charAt(j)));
                if (c1 != 0 && c2 != 0 && c1 > c2) invCount++;
            }
        }
        return invCount % 2 == 0;
    }

    boolean isGoal();

    List<State> getPossibleNextStates();

    String getAsString();

}
