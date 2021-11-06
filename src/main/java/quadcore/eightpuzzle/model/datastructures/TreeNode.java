package quadcore.eightpuzzle.model.datastructures;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

    private final T value;
    private TreeNode<T> parent;
    private final List<TreeNode<T>> children = new ArrayList<>();
    private int depth = 0;

    public TreeNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<T> child) {
        child.parent = this;
        increaseDepth(1);
        children.add(child);
    }

    public int getDepth() {
        return depth;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    private void increaseDepth(int depth) {
        if (this.depth < depth) {
            this.depth = depth;
            if (parent != null) parent.increaseDepth(depth + 1);
        }
    }

}
