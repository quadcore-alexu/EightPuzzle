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
import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class TreeController implements Initializable {
    private int[] levels;
    private int pivotalX;
    private AnchorPane treePane;
    protected TreeNode<State> root;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pivotalX = 2*(int)UIConstants.TREE_TILE_SIDE_LENGTH;
        // TODO: 8 to be replaced by tree max depth (from models)
        levels = new int[8];
        treePane = new AnchorPane();
        System.out.println("ROOT INITIALIZE " + root);
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

    public void initializeRoot(TreeNode<State> root)
    {
        this.root = root;
        buildSearchTree(0,0,root,0);
    }

    /**
     * Traverses the given tree and renders each of its states
     * Drawing lines between parent node and its children
     * @param root current node to be rendered
     */

    public void buildSearchTree(TreeNode<String> root){
        double sideLength = UIConstants.TREE_TILE_SIDE_LENGTH;
        Stack<GraphicTreeNode> stack = new Stack<>();
        stack.push(new GraphicTreeNode(pivotalX, 0, root, 0, 0));
        while (!stack.empty()) {
            GraphicTreeNode node = stack.pop();
            int nodeY = node.getParentY() + (int)sideLength*3*3/2;
            int slot = Math.max(node.getParentSlot(), levels[node.getLevel()]);
            int nodeX = pivotalX + (int)(sideLength*3+20) * slot;
            levels[node.getLevel()] = slot+1;
            Line line = new Line(nodeX, nodeY-sideLength*3/2,
                                 node.getParentX(), node.getParentY()+sideLength*3/2);
            if (node.getLevel() > 0)
                treePane.getChildren().add(line);
            drawState(nodeX, nodeY, node.getState(), node.isMarked());

            for (int i = 0; i < node.getChildren().size(); i++) {
                stack.push(new GraphicTreeNode(nodeX, nodeY, node.getChildren().get(i), node.getLevel()+1, slot));
            }
        }
    }

    // TODO: remove this function
    public TreeNode<String> buildDummyTree() {
        TreeNode<String> n1 = new TreeNode<String>("125340678");
        TreeNode<String> n2 = new TreeNode<String>("120345678");
        TreeNode<String> n3 = new TreeNode<String>("125348670");
        TreeNode<String> n4 = new TreeNode<String>("125304678");
        TreeNode<String> n5 = new TreeNode<String>("102345678");
        TreeNode<String> n6 = new TreeNode<String>("012345678");
        TreeNode<String> n7 = new TreeNode<String>("142305678");
        n1.addChild(n2);
        n1.addChild(n3);
        n1.addChild(n4);
        n2.addChild(n5);
        n5.addChild(n6);
        n5.addChild(n7);
        n6.markPathToParent();
        return n1;
    }

    /**
     * Renders the board for a certain state
     * Computes its coordinates in tree view
     * And add it to treePane to be shown
     * @param nodalX board center X coordinate
     * @param nodalY board center Y coordinate
     * @param state board state
     * @param isMarked whether the node on goal path
     */
    private void drawState(double nodalX, double nodalY, String state, boolean isMarked) {
        Color color = UIConstants.TILE_COLOR;
        if (isMarked) color = UIConstants.GOAL_COLOR;
        GridPane gameBoard = new GridPane();
        double sideLength = UIConstants.TREE_TILE_SIDE_LENGTH;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle tile = new Rectangle(sideLength, sideLength);
                tile.setFill(color);
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
}
