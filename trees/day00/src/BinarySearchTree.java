import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    TreeNode<T> root;
    private int size;

    public int size() {
        return size;
    }

    public boolean contains(T key) {
        return find(root, key) != null;
    }

    /**
     * Add a node to the BST. Internally calls insert to recursively find the new node's place
     */
    public boolean add(T key) {
        if (find(root, key) != null) return false;
        root = insert(root, key);
        size++;
        return true;
    }

    public void addAll(T[] keys) {
        for (T k : keys)
            add(k);
    }

    public List<T> inOrderTraversal() { // O(n) runtime -- going through each node once; O(n) space -- creating new list with every element
        return traverse(root);
    }

    public List<T> traverse(TreeNode<T> currNode) {
        List<T> leftList = new ArrayList<>();
        List<T> rightList = new ArrayList<>();
        if (currNode == null) {
            return new ArrayList<>();
        }
        if (currNode.hasLeftChild()) {
            leftList = traverse(currNode.leftChild);
        }
        if (currNode.hasRightChild()) {
            rightList = traverse(currNode.rightChild);
        }
        leftList.add(currNode.key);
        for (int i = 0; i < rightList.size(); i++) {
            leftList.add(rightList.get(i));
        }
        return leftList;
    }

    /**
     * Deletes a node from the BST using the following logic:
     * 1. If the node has a left child, replace it with its predecessor
     * 2. Else if it has a right child, replace it with its successor
     * 3. If it has no children, simply its parent's pointer to it
     */
    public boolean delete(T key) {
        TreeNode<T> toDelete = find(root, key);
        if (toDelete == null) {
            System.out.println("Key does not exist");
            return false;
        }
        TreeNode<T> deleted = delete(toDelete);
        if (toDelete == root) {
            root = deleted;
        }
        size--;
        return true;
    }

    private TreeNode<T> delete(TreeNode<T> n) {
        // Recursive base case
        if (n == null) return null;

        TreeNode<T> replacement;

        if (n.isLeaf())
            // Case 1: no children
            replacement = null;
        else if (n.hasRightChild() != n.hasLeftChild())
            // Case 2: one child
            replacement = (n.hasRightChild()) ? n.rightChild : n.leftChild; // replacement is the non-null child
        else {
            // Case 3: two children
            TreeNode<T> swapNode = findSuccessor(n);
            replacement = swapNode;
            delete(replacement);
            replacement.moveChildrenFrom(n);
        }

        // Put the replacement in its correct place, and set the parent.
        n.replaceWith(replacement);
        return replacement;
    }

    public T findPredecessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> predecessor = findPredecessor(n);
            if (predecessor != null)
                return predecessor.key;
        }
        return null;
    }

    public T findSuccessor(T key) {
        TreeNode<T> n = find(root, key);
        if (n != null) {
            TreeNode<T> successor = findSuccessor(n);
            if (successor != null)
                return successor.key;
        }
        return null;
    }

    private TreeNode<T> findPredecessor(TreeNode<T> n) { //worst runtime would be O(logn)
        TreeNode<T> pred = n;
        if (n.hasLeftChild()) {
            pred = n.leftChild;
            if (pred.hasRightChild()) {
                pred = pred.rightChild;
                while (pred.hasRightChild()) {
                    pred = pred.rightChild;
                }
            }
            return pred;
        } else { //no left or right children -- go up to parents, same thing -- see what side of tree its on
            while (pred.parent != null) { //while not root
                pred = pred.parent;
                if (n.key.compareTo(pred.key) > 0) {
                    return pred;
                }
            }

        }
        return null;
    }

    private TreeNode<T> findSuccessor(TreeNode<T> n) { //worst runtime would be O(n -- or logn if implemented like predecessor)
            List<T> inOrderTraversal = inOrderTraversal();
            T nVal = n.key;
            T nextVal = null;
            for (int i = 0; i < inOrderTraversal.size(); i++) {
                T currVal = inOrderTraversal.get(i);
                if (currVal == nVal) {
                    if (i < inOrderTraversal.size() - 1) {
                        nextVal = inOrderTraversal.get(i + 1);
                    }
                    break;
                }
            }
            if (nextVal != null) {
                TreeNode<T> currNode = root;
                for (int i = 0; i < inOrderTraversal.size(); i++) {
                    if (currNode.key == nextVal) {
                        return currNode;
                    } else if (nextVal.compareTo(currNode.key) < 0) { //decide whether to go left or right
                        currNode = currNode.leftChild;
                    } else {
                        currNode = currNode.rightChild;
                    }
                }
            }
            return null;
        }

    /**
     * Returns a node with the given key in the BST, or null if it doesn't exist.
     */
    private TreeNode<T> find(TreeNode<T> currentNode, T key) {
        if (currentNode == null)
            return null;
        int cmp = key.compareTo(currentNode.key);
        if (cmp < 0)
            return find(currentNode.leftChild, key);
        else if (cmp > 0)
            return find(currentNode.rightChild, key);
        return currentNode;
    }

    /**
     * Recursively insert a new node into the BST
     */
    private TreeNode<T> insert(TreeNode<T> node, T key) {
        if (node == null) return new TreeNode<>(key);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftChild = insert(node.leftChild, key);
            node.leftChild.parent = node;
        } else {
            node.rightChild = insert(node.rightChild, key);
            node.rightChild.parent = node;
        }
        return node;
    }
}
