package quadcore.eightpuzzle.model.heuristics;

import quadcore.eightpuzzle.model.State;

import java.util.function.Function;

public class ManhattanHeuristic implements Function<State, Integer> {

    private static ManhattanHeuristic instance;

    private ManhattanHeuristic() {
    }

    public static synchronized ManhattanHeuristic getInstance() {
        if (instance == null) instance = new ManhattanHeuristic();
        return instance;
    }

    @Override
    public Integer apply(State state) {
        //todo: implementation
        return 1;
    }
}
