package quadcore.eightpuzzle.model.datastructures;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

    private final T value;
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

    public void addChild(T value) {
        children.add(new TreeNode<>(value));
    }

}
