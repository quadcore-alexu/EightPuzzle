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

    @Disabled("Takes too long and creates large log file")
    @Test
    void main() {

        PuzzleSolver dfs = PuzzleSolverFactory.createPuzzleSolver("DFS");
        PuzzleSolver bfs = PuzzleSolverFactory.createPuzzleSolver("BFS");

        Result dfsResult = new Result();
        Result bfsResult = new Result();
        long start;

        int solved = 0;
        while (solved < 10) {
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

                List<State> sol1 = dfs.getSolution();
                List<State> sol2 = bfs.getSolution();
                assertEquals("012345678", sol1.get(sol1.size() - 1).getAsString());
                assertEquals("012345678", sol2.get(sol2.size() - 1).getAsString());
            }
        }

        Game.LOGGER.info("Average time taken for DFS is " + dfsResult.time / 10.0 + " ms.");
        Game.LOGGER.info("Average goal depth for DFS is " + dfsResult.goalDepth / 10.0);
        Game.LOGGER.info("Average max depth for DFS is " + dfsResult.maxDepth / 10.0);
        Game.LOGGER.info("Average nodes expanded for DFS is " + dfsResult.nodesExpanded / 10.0);

        Game.LOGGER.info("Average time taken for BFS is " + bfsResult.time / 10.0 + " ms.");
        Game.LOGGER.info("Average goal depth for BFS is " + bfsResult.goalDepth / 10.0);
        Game.LOGGER.info("Average max depth for BFS is " + bfsResult.maxDepth / 10.0);
        Game.LOGGER.info("Average nodes expanded for BFS is " + bfsResult.nodesExpanded / 10.0);

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
