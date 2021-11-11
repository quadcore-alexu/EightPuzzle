package quadcore.eightpuzzle.model.heuristics;
import quadcore.eightpuzzle.model.State;

import java.awt.*;
import java.util.function.ToDoubleFunction;

public class ManhattanHeuristic implements ToDoubleFunction<State> {

    private static ManhattanHeuristic instance;

    private ManhattanHeuristic() {
    }

    public static synchronized ManhattanHeuristic getInstance() {
        if (instance == null) instance = new ManhattanHeuristic();
        return instance;
    }

    private Point[] getPoints(char[] state) {
        Point[] stateCoordinates = new Point[9];
        for (int i = 0; i < 9; i++) {
            int x = Character.getNumericValue(state[i]) % 3;
            int y = Character.getNumericValue(state[i]) / 3;
            stateCoordinates[i] = new Point(x, y);
        }
        return stateCoordinates;
    }




    @Override
    public double applyAsDouble(State state) {
        String strState = state.getAsString();
        char[] stateArray = strState.toCharArray();
        Point[] stateCoordinates = getPoints(stateArray);
        Point[] goalCoordinates = getPoints(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'});
        double manhattan = 0.0;
        double [] heuristic = new double[9];
        for (int i = 0; i < 9; i++) {
            int diffX = Math.abs(stateCoordinates[i].x - goalCoordinates[i].x);
            int diffY = Math.abs(stateCoordinates[i].y - goalCoordinates[i].y);
            heuristic[Character.getNumericValue(stateArray[i])] = diffX + diffY;
            manhattan +=  heuristic[Character.getNumericValue(stateArray[i])];
        }
        return manhattan - heuristic[0];
    }


}

