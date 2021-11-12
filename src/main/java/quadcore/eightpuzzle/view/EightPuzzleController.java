package quadcore.eightpuzzle.view;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import quadcore.eightpuzzle.model.Game;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EightPuzzleController implements Initializable {
    private StatesManipulator statesManipulator;
    private TranslateTransition trans;
    private TranslateTransition backTrans;
    private String strategy;
    private String heuristic;
    private String initialState = "";
    private boolean lock;
    private Map<String, String> initialBoard;
    private Map<Integer, String> orderedTiles;
    private Game game;
    private TreeNode root;
    private final String tileStyle = "-fx-font: 30px \"Sitka Banner\"; -fx-background-color: #ff347f  ; -fx-border-color: #e0ffcd; -fx-border-width: 2; -fx-padding: 2 ;-fx-text-alignment: center";

    @FXML
    private VBox vBox;
    @FXML
    private Pane boardPane;
    @FXML
    private Label errorLabel;
    @FXML
    private Label counter;
    @FXML
    private Button reset;
    @FXML
    private Button solve;
    @FXML
    private ImageView backImage;
    @FXML
    private ImageView nextImage;
    @FXML
    private Label goalDepth;

    @FXML
    private Label maxDepth;
    @FXML
    private Label expandedNodes;


    @FXML
    private ComboBox strategies;
    @FXML
    private ComboBox heuristics;
    @FXML
    private TextField tileOne;
    @FXML
    private TextField tileTwo;
    @FXML
    private TextField tileThree;
    @FXML
    private TextField tileFour;
    @FXML
    private TextField tileFive;
    @FXML
    private TextField tileSix;
    @FXML
    private TextField tileSeven;
    @FXML
    private TextField tileEight;
    @FXML
    private TextField tileNine;

    public EightPuzzleController() {
    }

    @FXML
    protected void nextButtonClicked() {
        if (lock) return;
        lock = true;
        if (statesManipulator.hasNext()) {
            int[] move = statesManipulator.nextMove();
            moveTile(move[0], move[1], move[2]);
            counter.setText(String.valueOf(Integer.valueOf(counter.getText()) - 1));
        } else
            lock = false;
    }

    @FXML
    protected void setInitialState(Event event) {
        TextField tile = (TextField) event.getSource();
        if (tile.getText().equals(null)) {
            issueError("INVALID STATE");
        }
        initialBoard.put(tile.getId(), tile.getText());
        System.out.printf((String) initialBoard.get(tile.getId()));
    }

    @FXML
    protected void onStrategySelected() {
        strategy = (String) strategies.getValue();

        if (strategy.equals("A*"))
            heuristics.setVisible(true);
        else
            heuristics.setVisible(false);

    }

    @FXML
    protected void onHeuristicSelected() {
        heuristic = (String) heuristics.getValue();
        ;
        System.out.printf(heuristic);
    }


    @FXML
    private void reset() {
        boardPane.setVisible(false);
        backImage.setVisible(false);
        nextImage.setVisible(false);
        counter.setVisible(false);
        clearText();
        initialBoard = new HashMap<>();
        initialState = "";
        strategy = "";
        heuristic = "";
        counter.setText("");
        expandedNodes.setText("");
        maxDepth.setText("");
        goalDepth.setText("");
    }

    @FXML
    protected void backButtonClicked() {
        if (lock) return;
        lock = true;
        if (statesManipulator.hasPrev()) {
            if (!statesManipulator.hasNext()) colorBoard(false);
            int[] move = statesManipulator.previousMove();
            moveTile(move[0], move[1], move[2]);
            counter.setText(String.valueOf(Integer.valueOf(counter.getText()) + 1));
        } else
            lock = false;

    }

    @FXML
    protected void showTree() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EightPuzzleApplication.class.getResource("/quadcore/eightpuzzle/tree-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage = new Stage();
        stage.setTitle("Tree!");
        stage.setScene(scene);
        stage.setMaximized(true);
        TreeController controller = fxmlLoader.getController();
        stage.show();
        controller.setStage(stage);
        controller.initializeRoot(root, game.getNumberOfNodesExpanded());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardPane.setMaxWidth(UIConstants.TILE_SIDE_LENGTH * 3);
        boardPane.setMaxHeight(UIConstants.TILE_SIDE_LENGTH * 3);
        trans = new TranslateTransition(Duration.seconds(1));
        backTrans = new TranslateTransition(Duration.millis(0.1));

        //TODO: replace the passed array of strings with path solution array
        setStyles();

        strategies.getItems().clear();
        strategies.getItems().addAll("BFS", "DFS", "A*");
        heuristics.getItems().clear();
        heuristics.getItems().addAll("manhattan", "euclidean");
        heuristics.setVisible(false);
        initialBoard = new HashMap<>();
        errorLabel.setVisible(false);
        boardPane.setVisible(false);
        backImage.setVisible(false);
        nextImage.setVisible(false);
        counter.setVisible(false);
        initializeOrderedTiles();
        game = new Game();

    }

    /**
     * Renders the board for initial state
     * And return gridPane carrying the board tiles
     *
     * @param initialState initial state of the board
     */
    public GridPane createBoard(String initialState) {
        GridPane gameBoard = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle tile = new Rectangle(UIConstants.TILE_SIDE_LENGTH, UIConstants.TILE_SIDE_LENGTH);
                Text text = new Text(initialState.charAt(i * 3 + j) + "");
                tile.setFill(UIConstants.TILE_COLOR);
                text.setFill(Color.WHITESMOKE);
                text.setFont(Font.font("Sitka Banner", 40));
                if (text.getText().equals("0")) {
                    text.setText("");
                    tile.setFill(Color.TRANSPARENT);
                }
                StackPane pane = new StackPane(tile, text);
                gameBoard.add(pane, j, i);
            }
        }
        return gameBoard;
    }

    /**
     * Computes the moving tile new coordinate
     * The tile moves with a transition
     * A callback function is called at the end of transition
     * to commit board new state
     *
     * @param tileNumber moving tile index
     * @param xDir       moved units on X axis
     * @param yDir       moved units on Y axis
     */
    private void moveTile(int tileNumber, int xDir, int yDir) {
        StackPane tile = (StackPane) ((GridPane) boardPane.getChildren().get(0)).getChildren().get(tileNumber);
        trans.setNode(tile);
        trans.setToX(UIConstants.TILE_SIDE_LENGTH * xDir);
        trans.setToY(UIConstants.TILE_SIDE_LENGTH * yDir);
        trans.setOnFinished(event -> {
            swapTileWithEmpty(tileNumber, tileNumber + xDir + 3 * yDir);
            backTrans.setNode(tile);
            backTrans.setToX(0);
            backTrans.setToY(0);
            backTrans.setOnFinished(e -> {
                lock = false;
                checkGoalState();
            });
            backTrans.play();
        });
        trans.play();
    }

    private void initializeOrderedTiles() {
        orderedTiles = new HashMap<>();
        orderedTiles.put(1, "tileOne");
        orderedTiles.put(2, "tileTwo");
        orderedTiles.put(3, "tileThree");
        orderedTiles.put(4, "tileFour");
        orderedTiles.put(5, "tileFive");
        orderedTiles.put(6, "tileSix");
        orderedTiles.put(7, "tileSeven");
        orderedTiles.put(8, "tileEight");
        orderedTiles.put(9, "tileNine");

    }


    /**
     * Commits the board new state
     * It assign the moving tile to its new position
     * Swapping it with the empty tile
     *
     * @param firstTileIndex  moving tile index
     * @param secondTileIndex moving tile destination (empty tile index)
     */
    private void swapTileWithEmpty(int firstTileIndex, int secondTileIndex) {
        StackPane firstTile = (StackPane) ((GridPane) boardPane.getChildren().get(0)).getChildren().get(firstTileIndex);
        StackPane secondTile = (StackPane) ((GridPane) boardPane.getChildren().get(0)).getChildren().get(secondTileIndex);
        Text firstText = ((Text) firstTile.getChildren().get(1));
        Text secondText = ((Text) secondTile.getChildren().get(1));
        Rectangle firstBG = ((Rectangle) firstTile.getChildren().get(0));
        Rectangle secondBG = ((Rectangle) secondTile.getChildren().get(0));
        secondBG.setFill(UIConstants.TILE_COLOR);
        secondText.setText(firstText.getText());
        firstBG.setFill(Color.TRANSPARENT);
        firstText.setText("");
    }

    private void setStyles() {
        tileOne.setStyle(tileStyle);
        tileTwo.setStyle(tileStyle);
        tileThree.setStyle(tileStyle);
        tileFour.setStyle(tileStyle);
        tileFive.setStyle(tileStyle);
        tileSix.setStyle(tileStyle);
        tileSeven.setStyle(tileStyle);
        tileEight.setStyle(tileStyle);
        tileNine.setStyle(tileStyle);
        heuristics.setStyle("-fx-font: 20px \"Sitka Banner\";-fx-background-color: #ffdbc5 ");
        strategies.setStyle("-fx-font: 20px \"Sitka Banner\"; -jfx-unfocus-color: white;-fx-background-color: #ffdbc5;-fx-text-alignment: center; -fx-border-radius: 3");
        tileOne.setAlignment(Pos.CENTER);
        tileTwo.setAlignment(Pos.CENTER);
        tileThree.setAlignment(Pos.CENTER);
        tileFour.setAlignment(Pos.CENTER);
        tileFive.setAlignment(Pos.CENTER);
        tileSix.setAlignment(Pos.CENTER);
        tileSeven.setAlignment(Pos.CENTER);
        tileEight.setAlignment(Pos.CENTER);
        tileNine.setAlignment(Pos.CENTER);
        counter.setStyle("-fx-background-color:  #ffdbc5; -fx-background-radius: 30; -fx-text-alignment: center; -fx-font: 20px \"Sitka Banner\"  ");
        counter.setAlignment(Pos.CENTER);
        maxDepth.setStyle("-fx-background-color:  #ffdbc5; -fx-background-radius: 30; -fx-text-alignment: center; -fx-font: 20px \"Sitka Banner\"  ");
        goalDepth.setStyle("-fx-background-color:  #ffdbc5; -fx-background-radius: 30; -fx-text-alignment: center; -fx-font: 20px \"Sitka Banner\"  ");
        expandedNodes.setStyle("-fx-background-color:  #ffdbc5; -fx-background-radius: 30; -fx-text-alignment: center; -fx-font: 20px \"Sitka Banner\"  ");

        vBox.setStyle("-fx-background-color:linear-gradient(to right,rgb(161, 255, 206) 0%, rgb(250, 255, 209) 90%)");
        solve.setStyle("-fx-font: 20px \"Sitka Banner\"; -fx-background-color:  #ffdbc5; -fx-background-radius: 40");
        //radial-gradient(circle at 7.5% 24%, rgb(237, 161, 193) 0%, rgb(250, 178, 172) 25.5%, rgb(190, 228, 210) 62.3%, rgb(215, 248, 247) 93.8%)

    }

    /**
     * Checks whether the goal state reached then color the board goal color
     */
    private void checkGoalState() {
        if (!statesManipulator.hasNext()) {
            colorBoard(true);
        }
    }

    /**
     * Color all tiles either by normal state color
     * Or by goal color
     *
     * @param isGoal Determines whether goal state reached or not
     */
    private void colorBoard(boolean isGoal) {
        Color color = isGoal ? UIConstants.GOAL_COLOR : UIConstants.TILE_COLOR;
        Iterator<Node> iterator = ((GridPane) boardPane.getChildren().get(0)).getChildren().listIterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            Rectangle r = ((Rectangle) ((StackPane) node).getChildren().get(0));
            Text text = ((Text) ((StackPane) node).getChildren().get(1));
            if (text.getText().equals(""))
                r.setFill(Color.TRANSPARENT);
            else
                r.setFill(color);
        }
    }

    /**
     * check whether value in tile is valid
     * @param value
     * @return
     */

    private boolean isValidTileValue(String value) {
        if (value.length() == 0 || value.equals(null) || value.length() > 1) return false;
        if (Character.isDigit(value.charAt(0))) {
            return Integer.parseInt(value) < 9 && Integer.parseInt(value) >= 0 ? true : false;
        }
        return false;
    }

    /**
     * check if tiles have repeated numbers
     * @param value
     * @return
     */

    private boolean isDuplicateValue(String value) {
        return initialState.contains(value);
    }

    /**
     * check if initial state is valid
     * @return
     */
    @FXML
    private boolean isValidInitialState() {
        if (initialBoard.size() < 9) {
            issueError("INVALID STATE");

            return false;
        }
        String value = "";
        for (int i = 1; i <= 9; i++) {
            if (initialBoard.get(orderedTiles.get(Integer.valueOf(i))).equals(null)) {
                issueError("INVALID STATE");
                return false;
            }
            value = initialBoard.get(orderedTiles.get(Integer.valueOf(i)));
            if (isValidTileValue(value) && !isDuplicateValue(value)) {
                initialState += value;
            } else {
                issueError("INVALID STATE");


                return false;

            }
        }
        solve();
        return true;
    }


    private void clearText() {
        tileOne.clear();
        tileTwo.clear();
        tileThree.clear();
        tileFour.clear();
        tileFive.clear();
        tileSix.clear();
        tileSeven.clear();
        tileEight.clear();
        tileNine.clear();
    }

    /**
     * this function is called when the initial state is invalid or unsolvable
     * @param message
     */
    public void issueError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        clearText();
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(3)
        );
        visiblePause.setOnFinished(
                event -> errorLabel.setVisible(false)
        );
        visiblePause.play();
        initialBoard = new HashMap<>();
        initialState = "";

    }

    private boolean isValidStrategy() {
        if (strategy == null) {
            issueError("SELECT A STRATEGY");
            return false;
        }
        return true;
    }

    private void solve() {

        Boolean isSolvable;
        if (isValidStrategy()) {
            System.out.println("INTIAL STATE " + initialState);
            boardPane.getChildren().clear();
            boardPane.getChildren().add(createBoard(initialState));
            boardPane.setVisible(true);
            backImage.setVisible(true);
            nextImage.setVisible(true);
            counter.setVisible(true);
            if (strategy.equals("A*"))
                game.setPuzzleSolver(heuristic + " " + strategy);
            else
                game.setPuzzleSolver(strategy);
            isSolvable = game.solve(initialState);
            if (!isSolvable) {
                issueError("UNSOLVABLE INITIAL STATE");
                return;
            }

            String[] states;
            states = game.getSolution().stream().map(state -> state.getAsString()).toArray(String[]::new);
            counter.setText(String.valueOf(states.length - 1));
            statesManipulator = new StatesManipulator(states);
            root = game.getSearchTree();
            maxDepth.setText(String.valueOf(game.getMaxDepth()));
            goalDepth.setText(String.valueOf(game.getGoalDepth()));
            expandedNodes.setText(String.valueOf(game.getNumberOfNodesExpanded()));
        }
    }


}
