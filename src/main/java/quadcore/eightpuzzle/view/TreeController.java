package quadcore.eightpuzzle.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TreeController implements Initializable {
    private int[] levels;
    private int pivotalX;
    private AnchorPane treePane;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pivotalX = 2*(int)UIConstants.TREE_TILE_SIDE_LENGTH;
        // TODO: 8 to be replaced by tree max depth (from models)
        levels = new int[8];
        treePane = new AnchorPane();
        // TODO: replace buildDummyTree call with a call on solution model
        buildSearchTree(0, 0, buildDummyTree(), 0);
        scrollPane.setContent(treePane);
        scrollPane.setPannable(true);
    }

    /**
     * Called after showing stage to adjust scroll pane size
     * @param stage current stage
     */
    public void setStage(Stage stage) {
        scrollPane.setPrefHeight(stage.getHeight()-50);
        scrollPane.setPrefWidth(stage.getWidth()-50);
    }

    /**
     * Traverses the given tree and renders each of its states
     * Drawing lines between parent node and its children
     * @param parentX parent node X coordinate
     * @param parentY parent node Y coordinate
     * @param node current node to be rendered
     * @param level node level in tree
     */
    public void buildSearchTree(int parentX,int parentY,DummyNode node, int level){
        double sideLength = UIConstants.TREE_TILE_SIDE_LENGTH;
        int nodeY = parentY + (int)sideLength*3*3/2;
        int nodeX = pivotalX + (int)(sideLength*3+20) * levels[level];
        levels[level]++;
        Line line = new Line(nodeX, nodeY-sideLength*3/2, parentX, parentY+sideLength*3/2);
        if (level > 0)
            treePane.getChildren().add(line);
        drawState(nodeX, nodeY, node.state);
        for (int i = 0; i < node.children.size(); i++) {
            buildSearchTree(nodeX, nodeY, node.children.get(i), level+1);
        }
    }

    // TODO: remove this function
    public DummyNode buildDummyTree() {
        DummyNode n1 = new DummyNode();
        DummyNode n2 = new DummyNode();
        DummyNode n3 = new DummyNode();
        DummyNode n4 = new DummyNode();
        DummyNode n5 = new DummyNode();
        DummyNode n6 = new DummyNode();
        DummyNode n7 = new DummyNode();
        n1.children.add(n2);
        n1.children.add(n3);
        n1.children.add(n4);
        n2.children.add(n5);
        n5.children.add(n6);
        n5.children.add(n7);
        n1.state = "125340678";
        n2.state = "120345678";
        n3.state = "125348670";
        n4.state = "125304678";
        n5.state = "102345678";
        n6.state = "012345678";
        n7.state = "142305678";
        return n1;
    }

    /**
     * Renders the board for a certain state
     * Computes its coordinates in tree view
     * And add it to treePane to be shown
     * @param nodalX board center X coordinate
     * @param nodalY board center Y coordinate
     * @param state board state
     */
    private void drawState(double nodalX, double nodalY, String state) {
        GridPane gameBoard = new GridPane();
        double sideLength = UIConstants.TREE_TILE_SIDE_LENGTH;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle tile = new Rectangle(sideLength, sideLength);
                tile.setFill(UIConstants.TILE_COLOR);
                Text text = new Text(state.charAt(i*3+j)+"");
                text.setFill(Color.WHITESMOKE);
                text.setFont(Font.font("Sitka Banner", 12));
                if (text.getText().equals("0"))
                    text.setText("");
                StackPane pane = new StackPane(tile, text);
                gameBoard.add(pane, j, i);
            }
        }
        gameBoard.setLayoutX(nodalX-sideLength*3/2);
        gameBoard.setLayoutY(nodalY-sideLength*3/2);
        treePane.getChildren().add(gameBoard);
        treePane.setPrefHeight(Math.max(treePane.getHeight(), nodalY+sideLength*3/2));
    }

    //TODO: Enhancement may be added coloring the path
}
