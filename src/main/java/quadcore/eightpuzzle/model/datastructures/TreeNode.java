package quadcore.eightpuzzle.model.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode<T> {

    private final T value;
    private TreeNode<T> parent;
    private final List<TreeNode<T>> children = new ArrayList<>();
    private boolean marked = false;

    /**
     * Creates a new tree node using the passed value.
     *
     * @param value the value that this node will hold.
     */
    public TreeNode(T value) {
        this.value = value;
    }

    /**
     * @return the value held by this node.
     */
    public T getValue() {
        return value;
    }

    /**
     * @return a list of tree nodes that are children for this node.
     */
    public List<TreeNode<T>> getChildren() {
        return children;
    }

    /**
     * As well as adding the passed child to this node, the method adds this node as a parent for the passed child.
     *
     * @param child tree node to be added.
     */
    public void addChild(TreeNode<T> child) {
        if (child == null) throw new NullPointerException("Cannot add null child to TreeNode");
        child.parent = this;
        children.add(child);
    }

    /**
     * @return the parent node for this node. Null if this node is the root.
     */
    public TreeNode<T> getParent() {
        return parent;
    }

    public boolean isMarked() {
        return marked;
    }

    public void markPathToParent() {
        TreeNode<T> node = this;
        while (node != null) {
            node.marked = true;
            node = node.parent;
        }
    }
}
