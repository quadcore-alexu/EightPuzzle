package quadcore.eightpuzzle.view;

import quadcore.eightpuzzle.model.datastructures.TreeNode;

import java.util.List;

public class GraphicTreeNode {
    private final int parentX;
    private final int parentY;
    private final TreeNode<String> node;
    private final int level;

    public GraphicTreeNode(int parentX, int parentY, TreeNode<String> node, int level) {
        this.parentX = parentX;
        this.parentY = parentY;
        this.node = node;
        this.level = level;
    }

    public int getParentX() {
        return parentX;
    }

    public int getParentY() {
        return parentY;
    }

    public TreeNode<String> getNode() {
        return node;
    }

    public int getLevel() {
        return level;
    }

    public String getState() {
        return node.getValue();
    }

    public boolean isMarked() {
        return node.isMarked();
    }

    public List<TreeNode<String>> getChildren() {
        return node.getChildren();
    }
}
