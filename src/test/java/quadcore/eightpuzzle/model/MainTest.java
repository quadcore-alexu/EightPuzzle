package quadcore.eightpuzzle.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import quadcore.eightpuzzle.model.strategies.PuzzleSolver;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MainTest {

    private static class Result {
        long time = 0;
        int nodesExpanded;
        int maxDepth;
        int goalDepth;
    }

    @Disabled("Takes too long")
    @Test
    void main() {

        PuzzleSolver dfs = PuzzleSolverFactory.createPuzzleSolver("DFS");
        PuzzleSolver bfs = PuzzleSolverFactory.createPuzzleSolver("BFS");
        PuzzleSolver starManhattan = PuzzleSolverFactory.createPuzzleSolver("aStar manhattan");
        PuzzleSolver starEuclidean = PuzzleSolverFactory.createPuzzleSolver("aStar Euclidean");

        Result dfsResult = new Result();
        Result bfsResult = new Result();
        Result manhattanResult = new Result();
        Result euclideanResult = new Result();
        long start;

        int solved = 0;
        float n = 100;
        while (solved < n) {
            String str = generateRandomState();
            State state = new EightPuzzleState(str);
            if (state.isSolvable()) {
                solved++;

                //dfs
                start = System.currentTimeMillis();
                dfs.solve(state);
                dfsResult.time += System.currentTimeMillis() - start;
                dfsResult.goalDepth += dfs.getGoalDepth();
                dfsResult.maxDepth += dfs.getMaxDepth();
                dfsResult.nodesExpanded += dfs.getNumberOfNodesExpanded();

                //bfs
                start = System.currentTimeMillis();
                bfs.solve(state);
                bfsResult.time += System.currentTimeMillis() - start;
                bfsResult.goalDepth += bfs.getGoalDepth();
                bfsResult.maxDepth += bfs.getMaxDepth();
                bfsResult.nodesExpanded += bfs.getNumberOfNodesExpanded();

                //star manhattan
                start = System.currentTimeMillis();
                starManhattan.solve(state);
                manhattanResult.time += System.currentTimeMillis() - start;
                manhattanResult.goalDepth += starManhattan.getGoalDepth();
                manhattanResult.maxDepth += starManhattan.getMaxDepth();
                manhattanResult.nodesExpanded += starManhattan.getNumberOfNodesExpanded();

                //star euclidean
                start = System.currentTimeMillis();
                starEuclidean.solve(state);
                euclideanResult.time += System.currentTimeMillis() - start;
                euclideanResult.goalDepth += starEuclidean.getGoalDepth();
                euclideanResult.maxDepth += starEuclidean.getMaxDepth();
                euclideanResult.nodesExpanded += starEuclidean.getNumberOfNodesExpanded();

                List<State> sol1 = dfs.getSolution();
                List<State> sol2 = bfs.getSolution();
                List<State> sol3 = starManhattan.getSolution();
                List<State> sol4 = starEuclidean.getSolution();
                assertEquals("012345678", sol1.get(sol1.size() - 1).getAsString());
                assertEquals("012345678", sol2.get(sol2.size() - 1).getAsString());
                assertEquals("012345678", sol3.get(sol3.size() - 1).getAsString());
                assertEquals("012345678", sol4.get(sol4.size() - 1).getAsString());
            }
        }

        Game.LOGGER.info("Average time taken for DFS is " + dfsResult.time / n + " ms.");
        Game.LOGGER.info("Average goal depth for DFS is " + dfsResult.goalDepth / n);
        Game.LOGGER.info("Average max depth for DFS is " + dfsResult.maxDepth / n);
        Game.LOGGER.info("Average nodes expanded for DFS is " + dfsResult.nodesExpanded / n);

        Game.LOGGER.info("Average time taken for BFS is " + bfsResult.time / n + " ms.");
        Game.LOGGER.info("Average goal depth for BFS is " + bfsResult.goalDepth / n);
        Game.LOGGER.info("Average max depth for BFS is " + bfsResult.maxDepth / n);
        Game.LOGGER.info("Average nodes expanded for BFS is " + bfsResult.nodesExpanded / n);

        Game.LOGGER.info("Average time taken for A* with manhattan is " + manhattanResult.time / n + " ms.");
        Game.LOGGER.info("Average goal depth for A* with manhattan is " + manhattanResult.goalDepth / n);
        Game.LOGGER.info("Average max depth for A* with manhattan is " + manhattanResult.maxDepth / n);
        Game.LOGGER.info("Average nodes expanded for A* with manhattan is " + manhattanResult.nodesExpanded / n);

        Game.LOGGER.info("Average time taken for A* with euclidean is " + euclideanResult.time / n + " ms.");
        Game.LOGGER.info("Average goal depth for A* with euclidean is " + euclideanResult.goalDepth / n);
        Game.LOGGER.info("Average max depth for A* with euclidean is " + euclideanResult.maxDepth / n);
        Game.LOGGER.info("Average nodes expanded for A* with euclidean is " + euclideanResult.nodesExpanded / n);

    }

    private static String generateRandomState() {
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> numbers = new LinkedList<>(List.of(arr));
        SecureRandom random = new SecureRandom();
        StringBuilder str = new StringBuilder();
        while (!numbers.isEmpty()) {
            int n = random.nextInt(numbers.size());
            str.append(numbers.get(n));
            numbers.remove(n);
        }
        return str.toString();
    }
}
