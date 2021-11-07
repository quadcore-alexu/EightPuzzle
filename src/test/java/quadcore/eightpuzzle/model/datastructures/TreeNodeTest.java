package quadcore.eightpuzzle.model.datastructures;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTest {

    static TreeNode<Integer> treeNode = new TreeNode<>(1);

    @BeforeAll
    static void beforeAll() {
        TreeNode<Integer> node2 = new TreeNode<>(2);
        TreeNode<Integer> node3 = new TreeNode<>(3);
        TreeNode<Integer> node4 = new TreeNode<>(4);
        TreeNode<Integer> node5 = new TreeNode<>(5);
        TreeNode<Integer> node6 = new TreeNode<>(6);
        treeNode.addChild(node2);
        treeNode.addChild(node3);
        node3.addChild(node4);
        node2.addChild(node5);
        node4.addChild(node6);
    }

    @Test
    void getValue() {
        assertEquals(1, treeNode.getValue());
    }

}
