package quadcore.eightpuzzle.view;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class EightPuzzleController implements Initializable {
    private StatesManipulator statesManipulator;
    private TranslateTransition trans;
    private TranslateTransition backTrans;
    private boolean lock;

    @FXML
    private Pane boardPane;

    @FXML
    protected void nextButtonClicked() {
        if (lock) return;
        lock = true;
        if (statesManipulator.hasNext()) {
            int[] move = statesManipulator.nextMove();
            moveTile(move[0], move[1], move[2]);
        } else
            lock = false;
    }

    @FXML
    protected void backButtonClicked() {
        if (lock) return;
        lock = true;
        if (statesManipulator.hasPrev()) {
            if (!statesManipulator.hasNext()) colorBoard(false);
            int[] move = statesManipulator.previousMove();
            moveTile(move[0], move[1], move[2]);
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardPane.setMaxWidth(UIConstants.TILE_SIDE_LENGTH*3);
        boardPane.setMaxHeight(UIConstants.TILE_SIDE_LENGTH*3);
        trans = new TranslateTransition(Duration.seconds(1));
        backTrans = new TranslateTransition(Duration.millis(0.1));
        //TODO: replace the passed array of strings with path solution array
        statesManipulator = new StatesManipulator(new String[] { "125340678", "120345678", "102345678", "012345678" });
        boardPane.getChildren().add(createBoard("125340678"));
    }

    /**
     * Renders the board for initial state
     * And return gridPane carrying the board tiles
     * @param initialState initial state of the board
     */
    public GridPane createBoard(String initialState) {
        GridPane gameBoard = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle tile = new Rectangle(UIConstants.TILE_SIDE_LENGTH, UIConstants.TILE_SIDE_LENGTH);
                Text text = new Text(initialState.charAt(i*3+j) +"");
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
     * @param tileNumber moving tile index
     * @param xDir moved units on X axis
     * @param yDir moved units on Y axis
     */
    private void moveTile(int tileNumber, int xDir, int yDir) {
        StackPane tile = (StackPane)((GridPane)boardPane.getChildren().get(0)).getChildren().get(tileNumber);
        trans.setNode(tile);
        trans.setToX(UIConstants.TILE_SIDE_LENGTH*xDir);
        trans.setToY(UIConstants.TILE_SIDE_LENGTH*yDir);
        trans.setOnFinished(event -> {
            swapTileWithEmpty(tileNumber, tileNumber+xDir+3*yDir);
            backTrans.setNode(tile);
            backTrans.setToX(0);
            backTrans.setToY(0);
            backTrans.setOnFinished(e -> { lock = false; checkGoalState();});
            backTrans.play();
        });
        trans.play();
    }

    /**
     * Commits the board new state
     * It assign the moving tile to its new position
     * Swapping it with the empty tile
     * @param firstTileIndex moving tile index
     * @param secondTileIndex moving tile destination (empty tile index)
     */
    private void swapTileWithEmpty(int firstTileIndex, int secondTileIndex) {
        StackPane firstTile = (StackPane)((GridPane)boardPane.getChildren().get(0)).getChildren().get(firstTileIndex);
        StackPane secondTile = (StackPane)((GridPane)boardPane.getChildren().get(0)).getChildren().get(secondTileIndex);
        Text firstText = ((Text) firstTile.getChildren().get(1));
        Text secondText = ((Text) secondTile.getChildren().get(1));
        Rectangle firstBG = ((Rectangle) firstTile.getChildren().get(0));
        Rectangle secondBG = ((Rectangle) secondTile.getChildren().get(0));
        secondBG.setFill(UIConstants.TILE_COLOR);
        secondText.setText(firstText.getText());
        firstBG.setFill(Color.TRANSPARENT);
        firstText.setText("");
    }

    /**
     * Checks whether the goal state reached then color the board goal color
     */
    private void checkGoalState() {
        if (!statesManipulator.hasNext()) {colorBoard(true);}
    }

    /**
     * Color all tiles either by normal state color
     * Or by goal color
     * @param isGoal Determines whether goal state reached or not
     */
    private void colorBoard(boolean isGoal) {
        Color color = isGoal ? UIConstants.GOAL_COLOR : UIConstants.TILE_COLOR;
        Iterator<Node> iterator= ((GridPane)boardPane.getChildren().get(0)).getChildren().listIterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            Rectangle r = ((Rectangle)((StackPane)node).getChildren().get(0));
            Text text = ((Text)((StackPane)node).getChildren().get(1));
            if (text.getText().equals(""))
                r.setFill(Color.TRANSPARENT);
            else
                r.setFill(color);
        }
    }
}
