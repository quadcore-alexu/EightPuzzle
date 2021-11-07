package quadcore.eightpuzzle.model.datastructures;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

    private final T value;
    private TreeNode<T> parent;
    private final List<TreeNode<T>> children = new ArrayList<>();

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
        if (child == null) throw new NullPointerException("Cannot add null child to TreeNode");
        child.parent = this;
        children.add(child);
    }

    public TreeNode<T> getParent() {
        return parent;
    }


}
