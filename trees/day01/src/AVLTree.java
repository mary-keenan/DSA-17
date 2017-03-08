public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    TreeNode<T> delete(TreeNode<T> n, T key) {
        n = super.delete(n,key);
        if(n != null) {
            // TODO: Update height and balance tree
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            n = balance(n);
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
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            n = balance(n);
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

//    TreeNode<T> adjustHeight(TreeNode<T> node, T key) {
//        if (node.isLeaf()) { //base case -- can't balance leaf, start going back up, balancing as you go
//            node.height = 0;
//        } else { //only one side of tree was changed with insert -- this check reduces runtime from n to logn
//            if (node.key.compareTo(key) <= 0) { //key is to right of node, only need to balance that side
//                node.rightChild = adjustHeight(node.rightChild, key);
//                if (height(node.rightChild) >= height(node.leftChild)) { //only change it if new one is greater
//                    node.height = node.rightChild.height + 1;
//                }
//            } else {
//                node.leftChild = adjustHeight(node.leftChild, key);
//                if (height(node.leftChild) >= height(node.rightChild)) {
//                    node.height = node.leftChild.height + 1;
//                }
//            }
//        }
//        return node;
//    }

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
        TreeNode<T> newHead = n.leftChild;
        n.leftChild = newHead.rightChild; //move child over, okay if null
        newHead.rightChild = n; //replace child with n
        n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
        newHead.height = 1 + Math.max(height(newHead.leftChild), height(newHead.rightChild));
        return newHead;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateLeft(TreeNode<T> n) { //TODO: I wrote this
        TreeNode<T> newHead = n.rightChild;
        n.rightChild = newHead.leftChild; //move child over, okay if null
        newHead.leftChild = n; //replace child with n
        n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
        newHead.height = 1 + Math.max(height(newHead.leftChild), height(newHead.rightChild));
        return newHead;
    }
}
