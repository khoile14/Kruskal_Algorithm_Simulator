import java.util.LinkedList; //only for the return of values(), do not use it anywhere else

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 *
 * @param <AnyT> the type of elements in the tree (must be comparable)
 * @author Mark Allen Weiss
 */
public class WeissBST<AnyT extends Comparable<? super AnyT>> {
    /**
     * The tree root.
     */
    private BinaryNode<AnyT> root;

    /**
     * Construct the tree.
     */
    public WeissBST() {
        root = null;
    }

    /**
     * Insert into the tree.
     *
     * @param x the item to insert.
     * @throws Exception if x is already present.
     */
    public void insert(AnyT x) {
        root = insert(x, root);
    }

    /**
     * Remove minimum item from the tree.
     *
     * @throws Exception if tree is empty.
     */
    public void removeMin() {
        root = removeMin(root);
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public AnyT findMin() {
        return elementAt(findMin(root));
    }


    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return the matching item or null if not found.
     */
    public AnyT find(AnyT x) {
        return elementAt(find(x, root));
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Internal method to get element field.
     *
     * @param t the node.
     * @return the element field or null if t is null.
     */
    private AnyT elementAt(BinaryNode<AnyT> t) {
        return t == null ? null : t.element;
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws Exception if x is already present.
     */
    private BinaryNode<AnyT> insert(AnyT x, BinaryNode<AnyT> t) {
        if (t == null)
            t = new BinaryNode<AnyT>(x);
        else if (x.compareTo(t.element) < 0)
            t.left = insert(x, t.left);
        else if (x.compareTo(t.element) > 0)
            t.right = insert(x, t.right);
        else
            throw new IllegalArgumentException("Duplicate Item: " + x);  // Duplicate
        return t;
    }


    /**
     * Internal method to remove minimum item from a subtree.
     *
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws Exception if t is empty.
     */
    private BinaryNode<AnyT> removeMin(BinaryNode<AnyT> t) {
        if (t == null)
            throw new IllegalArgumentException("Min Item Not Found");
        else if (t.left != null) {
            t.left = removeMin(t.left);
            return t;
        } else
            return t.right;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyT> findMin(BinaryNode<AnyT> t) {
        if (t != null)
            while (t.left != null)
                t = t.left;

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return node containing the matched item.
     */
    private BinaryNode<AnyT> find(AnyT x, BinaryNode<AnyT> t) {
        while (t != null) {
            if (x.compareTo(t.element) < 0)
                t = t.left;
            else if (x.compareTo(t.element) > 0)
                t = t.right;
            else
                return t;    // Match
        }

        return null;         // Not found
    }

    //--------------------------------------------------------
    // CODE THAT YOU MAY NEED TO CHANGE
    //--------------------------------------------------------
    // We need the BST removal to be "predecessor replacement".
    // Make necessary changes to match the expected behavior.
    // Feel free to add private helper methods as needed.
    //--------------------------------------------------------

    /**
     * Remove from the tree..
     *
     * @param x the item to remove.
     * @throws Exception if x is not found.
     */
    public void remove(AnyT x) {
        if (find(x) == null)
            throw new IllegalArgumentException("Item Not Found: " + x.toString());
        root = remove(x, root);
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws Exception if x is not found.
     */
    private BinaryNode<AnyT> remove(AnyT x, BinaryNode<AnyT> t) {
        if (t == null)
            throw new IllegalArgumentException("Item Not Found: " + x.toString());
        if (x.compareTo(t.element) < 0) {
            t.left = remove(x, t.left);
        } else if (x.compareTo(t.element) > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) { // Two children
            t.element = findMax(t.left).element;
            t.left = removeMax(t.left);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyT> findMax(BinaryNode<AnyT> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;

        return t;
    }

    /**
     * Internal method to remove maximum item from a subtree.
     *
     * @param t the node that roots the tree.
     * @return the new root.
     */
    private BinaryNode<AnyT> removeMax(BinaryNode<AnyT> t) {
        if (t == null)
            throw new IllegalArgumentException("Max Item Not Found");
        else if (t.right != null) {
            t.right = removeMax(t.right);
            return t;
        } else
            return t.left;
    }

    /**
     * Return the number of nodes in the tree.
     * O(N): N is the tree size.
     *
     * @return the number of nodes in the tree.
     */
    public int size() {
        return sizeHelper(root);
    }

    /**
     * Helper method for size().
     *
     * @param t the node that roots the tree.
     * @return the number of nodes in tree.
     */
    private int sizeHelper(BinaryNode<AnyT> t) {
        if (t == null)
            return 0;
        else {
            return 1 + sizeHelper(t.left) + sizeHelper(t.right);
        }
    }


    /**
     * Return a string representation of the tree follow IN-ORDER traversal to include all nodes.
     * Include one space after each node.
     * O(N): N is the tree size
     * Return empty string "" for null trees.
     *
     * @return a string representation of the tree.
     */
    public String toString() {
        return helper(root);
    }

    /**
     * Helper method for toString().
     *
     * @param t the node that roots the tree.
     * @return a string representation of the tree.
     */
    private String helper(BinaryNode<AnyT> t) {
        if (t == null) {
            return "";
        } else {
            return helper(t.left) + t.element + " " + helper(t.right);
        }
    }

    /**
     * Return a linked list of all values in the tree follow PRE-ORDER traversal to include all nodes.
     *
     * @return a linked list of all values in the tree.
     */
    public LinkedList<AnyT> values() {
        LinkedList<AnyT> list = new LinkedList<AnyT>();
        return preOrder(root, list);
    }

    /**
     * Helper method for values().
     *
     * @param t    the node that roots the tree.
     * @param list the linked list of all values in the tree.
     * @return a linked list of all values in the tree.
     */
    private LinkedList<AnyT> preOrder(BinaryNode<AnyT> t, LinkedList<AnyT> list) {
        if (t == null) {
            return list;
        } else {
            list.add(t.element);
            preOrder(t.left, list);
            preOrder(t.right, list);
        }
        return list;
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param <AnyT> the type of elements in the tree (must be comparable)
     */
    private class BinaryNode<AnyT> {
        /**
         * The data in the node.
         */
        AnyT element;
        /**
         * Left child.
         */
        BinaryNode<AnyT> left;
        /**
         * Right child.
         */
        BinaryNode<AnyT> right;

        /**
         * Construct the node.
         *
         * @param theElement the data to put in node.
         */
        BinaryNode(AnyT theElement) {
            element = theElement;
            left = right = null;
        }
    }

}
