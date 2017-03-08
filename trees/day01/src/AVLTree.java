public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    TreeNode<T> delete(TreeNode<T> n, T key) {
        n = super.delete(n,key);
        if(n != null) {
            // TODO: Update height and balance tree
            if (Math.abs(balanceFactor(n)) > 1) { //if tree is now unbalanced, we need to balance it
                n = checkBalance(n, key);
            }
            n = adjustHeight(n, key);
        }
        return n;
    }
    /**
     * Insert a key into the tree rooted at the given node.
     */
    @Override
    TreeNode<T> insert(TreeNode<T>  n, T key) {
        n = super.insert(n,key);
        if (n != null) {
            // TODO: update height and balance tree
            if (Math.abs(balanceFactor(n)) > 1) { //if tree is now unbalanced, we need to balance it
                n = checkBalance(n, key);
            }
            n = adjustHeight(n, key);
        }
        return n;
    }

    TreeNode<T> checkBalance(TreeNode<T> node, T key) {
        if (node.isLeaf()) { //base case -- can't balance leaf, start going back up, balancing as you go
            return node;
        } else { //only one side of tree was changed with insert -- this check reduces runtime from n to logn
            //TODO: compareTo --> a.compareTo(b) = a - b
            if (node.key.compareTo(key) <= 0) { //key is to right of node, only need to balance that side
                node.rightChild = checkBalance(node.rightChild, key);
            } else {
                node.leftChild = checkBalance(node.leftChild, key);
            }
            return balance(node);
        }
    }

    TreeNode<T> adjustHeight(TreeNode<T> node, T key) {
        if (node.isLeaf()) { //base case -- can't balance leaf, start going back up, balancing as you go
            node.height = 0;
        } else { //only one side of tree was changed with insert -- this check reduces runtime from n to logn
            if (node.key.compareTo(key) <= 0) { //key is to right of node, only need to balance that side
                node.rightChild = adjustHeight(node.rightChild, key);
                if (height(node.rightChild) >= height(node.leftChild)) { //only change it if new one is greater
                    node.height = node.rightChild.height + 1;
                }
            } else {
                node.leftChild = adjustHeight(node.leftChild, key);
                if (height(node.leftChild) >= height(node.rightChild)) {
                    node.height = node.leftChild.height + 1;
                }            }
        }
        return node;
    }

    /**
     * Delete the minimum descendant of the given node.
     */
    @Override
    TreeNode<T> deleteMin(TreeNode<T> n){
        n = super.deleteMin(n);
        if(n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            return balance(n);
        }
        return null;
    }

    // Return the height of the given node. Return -1 if null.
    private int height(TreeNode<T> n) { //TODO: I wrote this
        if (n != null) { //check to make sure n exists -- if it does, find height recursively
            return n.height;
        } else { //n doesn't exist
            return -1;
        }
    }

    public int height() {
        return Math.max(height(root),0);
    }

    // Restores the AVL tree property of the subtree.
    TreeNode<T> balance(TreeNode<T> n) { //TODO: I wrote this
        if (balanceFactor(n) > 1) {//n is very right heavy
            if (balanceFactor(n.rightChild) < 0) { //right child is left heavy
                n.rightChild = rotateRight(n.rightChild);
            }
            n = rotateLeft(n);
        }
        if (balanceFactor(n) < -1) { //n is very left heavy
            if (balanceFactor(n.leftChild) > 0) { //left child is right heavy
                n.leftChild = rotateLeft(n.leftChild);
            }
            n = rotateRight(n);
        }
        return n;
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     */
    private int balanceFactor(TreeNode<T> n) { //TODO: I wrote this
        if (n != null) { //throws nullExceptionError when height is called on null.child
            return height(n.rightChild) - height(n.leftChild);
        } else {
            return 0;
        }
    }

    /**
     * Perform a right rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateRight(TreeNode<T> n) { //TODO: I wrote this
        System.out.println("ROTATE RIGHT");
        System.out.println(n.key);
        if (n.hasLeftChild()) {
            if (n.leftChild.hasRightChild()) {
                TreeNode<T> newHead = n.leftChild;
                TreeNode<T> problemChild = n.leftChild.rightChild; //move to n.left
                n.leftChild.rightChild = n;
                n.leftChild = problemChild;
                newHead.height = n.height + 1;
                n = newHead;
            } else {
                TreeNode<T> leftKid = n.leftChild;
                n.leftChild = n;
                n = leftKid;
                n.height++;
            }
            return n;
        }
        return null;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateLeft(TreeNode<T> n) { //TODO: I wrote this
        System.out.println("ROTATE LEFT");
        System.out.println(n.key);
        if (n.hasRightChild()) {
            if (n.rightChild.hasLeftChild()) {
                TreeNode<T> newHead = n.rightChild;
                TreeNode<T> problemChild = n.rightChild.leftChild; //move to n.right
                n.rightChild.leftChild = n;
                n.rightChild = problemChild;
                newHead.height = n.height + 1;
                n = newHead;
            } else { //n.right doesn't have a left child
                TreeNode<T> rightKid = n.rightChild;
                n.rightChild = n;
                n = rightKid;
                n.height++;
            }
            return n;
        }
        return null;
    }
}
