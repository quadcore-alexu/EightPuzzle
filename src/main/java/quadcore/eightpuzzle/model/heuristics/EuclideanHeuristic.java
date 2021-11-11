package quadcore.eightpuzzle.model.heuristics;

import quadcore.eightpuzzle.model.State;

import java.awt.*;
import java.util.function.ToDoubleFunction;

public class EuclideanHeuristic implements ToDoubleFunction<State> {

    private static EuclideanHeuristic instance;

    private EuclideanHeuristic() {
    }

    public static synchronized EuclideanHeuristic getInstance() {
        if (instance == null) instance = new EuclideanHeuristic();
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
        double euclidean = 0.0;
        double[] heuristic = new double[9];

        for (int i = 0; i < 9; i++) {
            double diffX = Math.abs(stateCoordinates[i].x - goalCoordinates[i].x);
            double diffY = Math.abs(stateCoordinates[i].y - goalCoordinates[i].y);
            heuristic[Character.getNumericValue(stateArray[i])] = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
            euclidean += heuristic[Character.getNumericValue(stateArray[i])];
        }
        return euclidean - heuristic[0];
    }


}
