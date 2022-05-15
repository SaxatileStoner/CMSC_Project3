/**
 * Contains static nested class to define the nodes of the binary tree,
 * builds a prefix and infix notiation representation of the tree,
 * finds height of tree,
 * checks if tree is balanced
 * 
 * CMSC 350 Data Structures and Analysis
 * @author Christopher Stoner
 */
package Project3;

/**
 * BinaryTree
 */
public class BinaryTree<T> {

    // BinaryTree values defined here
    Node<StringBuilder> root = null;
    StringBuilder prefixNotation = null;
    StringBuilder infixNotation = null;

    // Node definition
    public static class Node<T> {
        T data;
        Node<T> left, right;

        // construct empty node
        Node() {
            data = null;
            left = null;
            right = null;
        }

        // construct node with data, but null pointers
        Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    /**
     * construct binarytree from prefix string
     * 
     * @param str prefix string to evaluate and turn into a binary tree
     * @throws InvalidTreeSyntax string2Tree may throw an exception if string is not
     *                           formatted properly.
     */
    BinaryTree(String str) throws InvalidTreeSyntax {
        Node<StringBuilder> root = string2Tree(str);
        this.root = root;

        StringBuilder prefixSb = new StringBuilder();
        StringBuilder infixSb = new StringBuilder();

        prefixBuilder(prefixSb, root);
        this.prefixNotation = prefixSb;
        infixBuilder(infixSb, root);
        this.infixNotation = infixSb;

        if (str.compareTo(this.prefixNotation.toString()) != 0 && str.compareTo(this.infixNotation.toString()) != 0) {
            throw new InvalidTreeSyntax("Error, output string for tree does NOT match the input string!");
        }
        // System.out.println(sb.toString()); for debugging

    }

    /**
     * builder that builds a prefix string from the root of a tree
     * used for debugging and visualizing the tree as it was put in.
     * 
     * @param sb   stringbuilder object used to build the prefix string from the
     *             root node
     * @param node The root node of the binary tree
     */
    private void prefixBuilder(StringBuilder sb, Node<StringBuilder> node) {
        if (node == null) {
            return;
        }

        sb.append(node.data);

        if (node.left == null && node.right == null) {
            return;
        }

        sb.append("(");

        if (node.left != null) {
            prefixBuilder(sb, node.left);
        }

        sb.append(")");

        if (node.right != null) {
            sb.append("(");
            prefixBuilder(sb, node.right);
            sb.append(")");
        }
    }

    /**
     * Builder that builds a infix string from the root of a tree
     * used for debugging and visualizing the tree in infix notation
     * 
     * @param sb   stringbuilder object used to build the infix string from the root
     *             node
     * @param node The root node of the binary tree
     */
    private void infixBuilder(StringBuilder sb, Node<StringBuilder> node) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            sb.append("(");
            sb.append(node.data);
            sb.append(")");
            return;
        }

        if (node.left != null) {
            infixBuilder(sb, node.left);
        }

        sb.append(node.data);

        if (node.right != null) {
            sb.append("(");
            infixBuilder(sb, node.right);
            sb.append(")");
        }
    }

    int i = 0; // used to keep place in string2Tree, after building, reps total number of
               // characters in the inputted string

    /**
     * This function is called on when a binary tree object is created in order to
     * make a valid tree from an inputted string.
     * 
     * @param str String that is expected to be a prefix representation of a tree
     * @return Root of the tree to then be assigned to the binary tree in the
     *         constructor
     * @throws InvalidTreeSyntax If inputted string is not in the correct format to
     *                           identify this as a tree
     */
    public Node<StringBuilder> string2Tree(String str) throws InvalidTreeSyntax {
        if (str == null || str.length() == 0) {
            return null;
        }
        return string2TreeHelper(str.toCharArray());
    }

    /**
     * This helper function does most of the heavy lifting for string2Tree
     * Creates Root node and links all subnodes from root
     * 
     * @param strArr characters from inputted string
     * @return Node<StringBuilder> which is root
     * @throws InvalidTreeSyntax If inputted string is not in the correct format to
     *                           identify this as a tree
     */
    private Node<StringBuilder> string2TreeHelper(char[] strArr) throws InvalidTreeSyntax {

        // Check if string array is NOT 0
        if (i == strArr.length) {
            return null;
        }

        // Extract character
        StringBuilder character = new StringBuilder();
        while (i < strArr.length
                && strArr[i] != '('
                && strArr[i] != ')') {
            character.append(strArr[i]);
            i++;
        }

        // Create new node
        Node<StringBuilder> node = null;
        if (character.length() != 0) {
            node = new Node<StringBuilder>(character);

            // check parenthesis type
            if (i < strArr.length
                    && strArr[i] == '(') {
                // create left child
                i++;
                node.left = string2TreeHelper(strArr);
                i++;

                // create right child
                if (i < strArr.length
                        && strArr[i] == '(') {
                    i++;
                    node.right = string2TreeHelper(strArr);
                    i++;
                }
            }
            // if meets ')', return to parent node
            return node;

        }

        throw new InvalidTreeSyntax("InvalidTreeSyntax Detected!");

    }

    /**
     * Starting count from 0, so, one less than the product of calcHeight, which
     * starts at 1
     * 
     * @param node root node to be passed to calcHeight
     * @return height minus 1
     */
    public int height(Node node) {
        int height = calcHeight(node);
        return height - 1;
    }

    /**
     * Recursive function that finds the height of a Binary Tree
     * 
     * @param node root node to start the traversal
     * @return 1 + the larger of the two node's heights
     */
    private int calcHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }

        return Math.max(calcHeight(node.left), calcHeight(node.right)) + 1;
    }

    /**
     * Checks if the binary tree is balanced
     * Difference between left and right subtrees must be at most 1
     * 
     * @param node Root node
     * @return true, is balanced, otherwise not balanced
     */
    public boolean isBalanced(Node<T> node) {
        int leftHeight;
        int rightHeight;

        // If tree/subtree is empty, return true
        if (node == null) {
            return true;
        }

        leftHeight = height(node.left);
        rightHeight = height(node.right);

        // Difference between left and right heights must be at most 1
        // Recursively check if left and right nodes are balanced with the lower
        // subtrees
        if (Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right)) {
            return true;
        }

        return false;
    }

    /**
     * Check if all nodes have either node.left and node.right as null, or
     * filled. Cannot have a node with only one filled node to traverse to.
     * 
     * @param node Starting with the root node and recusively travels through tree
     * @return boolean, if true, then the tree is full, otherwise, the tree is NOT
     *         full
     */
    public boolean isProper(Node<T> node) {
        if (node == null) {
            return true;
        }

        if (node.left == null
                && node.right == null) {
            return true;
        }

        if ((node.left != null)
                && (node.right != null)) {
            return isProper(node.left) && isProper(node.right);
        }

        return false;
    }

    /**
     * The relationship between height and max number of nodes is:
     * (2 ^ (height(// Starting at 0 //) + 1) - 1) = Max Nodes
     * 
     * @param node
     * @return
     */
    public boolean isFull(Node node) {
        int h = height(node);
        int maxNodes = (int) (Math.pow(2, (h + 1)) - 1);
        int numOfNodes = numOfNodes(node);

        if (maxNodes == numOfNodes) {
            return true;
        }

        return false;
    }

    /**
     * returns the number of nodes in a tree
     * 
     * @param node root node to start the traversal
     * @return number of nodes in a tree
     */
    public int numOfNodes(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + numOfNodes(node.left) + numOfNodes(node.right);
    }

    /**
     * Used for debugging all BinaryTree function/method calls
     * 
     * @param args does not require args
     * @throws InvalidTreeSyntax If tree prefix notation string does not represent a
     *                           tree
     */
    // public static void main(String[] args) throws InvalidTreeSyntax {
    // String testString = "A(G(j)(1))(z(5)(f))";

    // BinaryTree t1 = new BinaryTree<>(testString);

    // System.out.println(t1.prefixNotation);
    // System.out.println(t1.infixNotation);
    // System.out.println("Tree Height: " + t1.height(t1.root));
    // System.out.println("Is Balanced: " + t1.isBalanced(t1.root));
    // System.out.println("Is Full: " + t1.isFull(t1.root));
    // System.out.println("Is Proper: " + t1.isProper(t1.root));
    // }
}