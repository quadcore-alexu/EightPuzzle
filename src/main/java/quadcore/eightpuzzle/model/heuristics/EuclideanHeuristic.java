package quadcore.eightpuzzle.model.heuristics;

import quadcore.eightpuzzle.model.State;

import java.awt.*;
import java.util.function.Function;

public class EuclideanHeuristic implements Function<State, Double> {

    private static EuclideanHeuristic instance;

    private EuclideanHeuristic() {
    }

    public static synchronized EuclideanHeuristic getInstance() {
        if (instance == null) instance = new EuclideanHeuristic();
        return instance;
    }
    private Point[] stateToPoints(String state) {
        char[] nodes = new char[9];
        return ManhattanHeuristic.getPoints(state, nodes);
    }

    @Override
    public Double apply(State state) {
        String strState = state.getAsString();
        Point[] stateCoordinates = stateToPoints(strState);
        Point[] goalCoordinates = stateToPoints("012345678");
        double euclidean = 0;
        for (int i = 0; i < 9; i++) {
            int diffX = Math.abs(stateCoordinates[i].x - goalCoordinates[i].x);
            int diffY = Math.abs(stateCoordinates[i].y - goalCoordinates[i].y);
            double distance = Math.sqrt(Math.pow(diffX,2)+Math.pow(diffY,2));
            euclidean += distance;
        }
        return euclidean;
    }

}
