package quadcore.eightpuzzle.view;

import quadcore.eightpuzzle.model.State;
import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.List;

public class GraphicTreeNode {
    private final int parentX;
    private final int parentY;
    private final TreeNode<State> node;
    private final int level;
    private int parentSlot;

    public GraphicTreeNode(int parentX, int parentY, TreeNode<State> node, int level, int parentSlot) {
        this.parentX = parentX;
        this.parentY = parentY;
        this.node = node;
        this.level = level;
        this.parentSlot = parentSlot;
    }

    public int getParentX() {
        return parentX;
    }

    public int getParentY() {
        return parentY;
    }

    public TreeNode<State> getNode() {
        return node;
    }

    public int getLevel() {
        return level;
    }

    public State getState() {
        return node.getValue();
    }

    public boolean isMarked() {
        return node.isMarked();
    }

    public List<TreeNode<State>> getChildren() {
        return node.getChildren();
    }

    public int getParentSlot() {
        return parentSlot;
    }
}
