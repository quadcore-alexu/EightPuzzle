package quadcore.eightpuzzle.model.heuristics;

import quadcore.eightpuzzle.model.State;

import java.util.function.Function;

public class EuclideanHeuristic implements Function<State, Integer> {

    private static EuclideanHeuristic instance;

    private EuclideanHeuristic() {
    }

    public static synchronized EuclideanHeuristic getInstance() {
        if (instance == null) instance = new EuclideanHeuristic();
        return instance;
    }

    @Override
    public Integer apply(State state) {
        //todo: implementation
        return 1;
    }
}
