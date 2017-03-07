import java.util.*;

public class Problems {

    public static BinarySearchTree<Integer> minimalHeight(List<Integer> values) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        return addMiddle(binarySearchTree, values);
    }

    public static BinarySearchTree<Integer> addMiddle(BinarySearchTree currentTree, List<Integer> values) {
        BinarySearchTree<Integer> fullTree = currentTree;
        if (values.size() > 1) {
            Collections.sort(values); //sort to find median
            int middle = values.get(values.size() / 2); //TODO: does this round correctly?
            currentTree.add(middle);
            List<Integer> leftValues = new ArrayList<>(); //will be less than middle value
            List<Integer> rightValues = new ArrayList<>(); //will be more than middle value
            for (int i = 0; i < values.size() / 2; i++) {
                leftValues.add(values.get(i));
            }
            for (int j = values.size() / 2 + 1; j < values.size(); j++) {
                rightValues.add(values.get(j));
            }
            BinarySearchTree<Integer> leftTree = addMiddle(currentTree, leftValues);
            fullTree = addMiddle(leftTree, leftValues);
        } else if (values.size() == 1) {
            fullTree.add(values.get(0));
        }
        return fullTree;
    }

    public static boolean isIsomorphic(TreeNode n1, TreeNode n2) {
        Boolean checkFurtherL = false;
        Boolean checkFurtherR = false;
        if (n1.key == n2.key) {
            if (n1.hasRightChild() && n1.hasLeftChild() && n2.hasRightChild() && n2.hasLeftChild()) {
                if (n1.leftChild.key == n2.leftChild.key && n1.rightChild.key == n2.rightChild.key) { //match, no switching
                    checkFurtherL = isIsomorphic(n1.leftChild, n2.leftChild);
                    checkFurtherR = isIsomorphic(n1.rightChild, n2.rightChild);
                } else if (n1.leftChild.key == n2.rightChild.key && n1.rightChild.key == n2.leftChild.key) {//match, need switching
                    checkFurtherL = isIsomorphic(n1.leftChild, n2.rightChild);
                    checkFurtherR = isIsomorphic(n1.rightChild, n2.leftChild);
                }
            } else if (n1.hasLeftChild() && n2.hasLeftChild() && !n1.hasRightChild() && !n2.hasRightChild()) {
                if (n1.leftChild.key == n2.leftChild.key) {
                    checkFurtherL = isIsomorphic(n1.leftChild, n2.leftChild);
                    checkFurtherR = true;
                }
            } else if (n1.hasRightChild() && n2.hasRightChild() && !n1.hasLeftChild() && !n2.hasLeftChild()){
                if (n1.rightChild.key == n2.rightChild.key) {
                    checkFurtherL = true;
                    checkFurtherR = isIsomorphic(n1.rightChild, n2.rightChild);
                }
            } else if (n1.hasLeftChild() && n2.hasRightChild() && !n1.hasRightChild() && !n2.hasLeftChild()) {
                if (n1.leftChild.key == n2.rightChild.key) {
                    checkFurtherL = isIsomorphic(n1.leftChild, n2.rightChild);;
                    checkFurtherR = true;
                }
            } else if (n1.hasRightChild() && n2.hasLeftChild() && !n1.hasLeftChild() && !n2.hasRightChild()) {
                if (n1.rightChild.key == n2.leftChild.key) {
                    checkFurtherL = true;
                    checkFurtherR = isIsomorphic(n1.rightChild, n2.leftChild);
                }
            } else if (!n1.hasLeftChild() && !n2.hasLeftChild() && !n1.hasRightChild() && !n2.hasRightChild()) {
                checkFurtherL = true;
                checkFurtherR = true;
            }
        }
        if (!checkFurtherL|| !checkFurtherR) { //if either is false, return false
            return false;
        }
        return true;
    }
}
