import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraversal {

    public static List<Integer> levelOrderTraversal(TreeNode<Integer> n) {
        List traversalList = new ArrayList();
        Queue queue = new LinkedList();
        queue.offer(n);
        while (!queue.isEmpty()) {
            TreeNode oldestNum = (TreeNode) queue.poll();
            if (oldestNum.hasLeftChild()) {
                queue.offer(oldestNum.leftChild);
            }
            if (oldestNum.hasRightChild()) {
                queue.offer(oldestNum.rightChild);
            }
            traversalList.add(oldestNum.key);
        }

        return traversalList;
    }
}
