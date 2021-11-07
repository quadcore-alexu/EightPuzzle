package quadcore.eightpuzzle.model.heuristics;

import org.jetbrains.annotations.NotNull;
import quadcore.eightpuzzle.model.State;

import java.awt.*;
import java.util.function.Function;

public class ManhattanHeuristic implements Function<State, Double> {

    private static ManhattanHeuristic instance;

    private ManhattanHeuristic() {
    }

    public static synchronized ManhattanHeuristic getInstance() {
        if (instance == null) instance = new ManhattanHeuristic();
        return instance;
    }

    private Point[] stateToPoints(String state) {
        char[] nodes = new char[9];
        return getPoints(state, nodes);
    }

    @NotNull
    static Point[] getPoints(String state, char[] nodes) {
        Point[] stateCoordinates = new Point[9];

        for (int i = 0; i < 9; i++) {
            nodes[i] = state.charAt(i);
            int x = nodes[i] % 3;
            int y = nodes[i] / 3;
            stateCoordinates[i] = new Point(x, y);
        }
        return stateCoordinates;
    }

    @Override
    public Double apply(State state) {
        String strState = state.getAsString();
        Point[] stateCoordinates = stateToPoints(strState);
        Point[] goalCoordinates = stateToPoints("012345678");
        double manhattan = 0;
        for (int i = 0; i < 9; i++) {
            int diffX = Math.abs(stateCoordinates[i].x - goalCoordinates[i].x);
            int diffY = Math.abs(stateCoordinates[i].y - goalCoordinates[i].y);
            int distance = diffX + diffY;
            manhattan += distance;
        }
        return manhattan;
    }

}

