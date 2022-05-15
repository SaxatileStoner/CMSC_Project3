/**
 * CMSC 350 Project 3
 * Generates GUI to use BinaryTree.java
 * Input string to build binary tree: make tree
 * outputs: height, nodes, isBalanced, isProper, isFull, infix notation
 */

package Project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TreeGUI extends JFrame {

    BinaryTree tree = null;

    // All Text fields in GUI
    private JTextField input = new JTextField(20);
    private JTextField output = new JTextField(20);

    // All buttons in GUI
    private final JButton makeTreeButton = new JButton("Make Tree");
    private final JButton isBalancedButton = new JButton("Is Balanced?");
    private final JButton isFullButton = new JButton("Is Full?");
    private final JButton isProperButton = new JButton("Is Proper?");
    private final JButton heightButton = new JButton("Height");
    private final JButton numNodesButton = new JButton("Nodes");
    private final JButton inOrderButton = new JButton("Inorder");

    // Define Action Listeners
    private final ActionListener makeTreeListener = event -> {
        try {
            BinaryTree binaryTree = new BinaryTree(input.getText());
            this.tree = binaryTree;
            output.setText("" + tree.prefixNotation);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e1.getMessage());
        }
    };

    private final ActionListener isBalancedListener = event -> {
        try {
            String answer = String.valueOf(tree.isBalanced(tree.root));
            output.setText(answer);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e2.getMessage());
        }
    };

    private final ActionListener isFullListener = event -> {
        try {
            String answer = String.valueOf(tree.isFull(tree.root));
            output.setText(answer);
        } catch (Exception e3) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e3.getMessage());
        }
    };

    private final ActionListener isProperListener = event -> {
        try {
            String answer = String.valueOf(tree.isProper(tree.root));
            output.setText(answer);
        } catch (Exception e4) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e4.getMessage());
        }
    };

    private final ActionListener heightListener = event -> {
        try {
            String answer = Integer.toString(tree.height(tree.root));
            output.setText(answer);
        } catch (Exception e5) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e5.getMessage());
        }
    };

    private final ActionListener numNodesListener = event -> {
        try {
            String answer = Integer.toString(tree.numOfNodes(tree.root));
            output.setText(answer);
        } catch (Exception e6) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e6.getMessage());
        }
    };

    private final ActionListener inOrderListener = event -> {
        try {
            output.setText("" + tree.infixNotation);
        } catch (Exception e7) {
            JOptionPane.showMessageDialog(null, "An Exception has been thrown! -> " + e7.getMessage());
        }
    };

    // constructor
    TreeGUI() {
        super("Binary Tree");
        setSize(750, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        JComponent[] inputComponents = { new JLabel("Enter Tree: "), input };
        JComponent[] buttonComponents = { makeTreeButton, isBalancedButton,
                isFullButton, isProperButton,
                heightButton, numNodesButton, inOrderButton };
        JComponent[] outputComponents = { new JLabel("Output"), output };

        makeFlowPanel(inputComponents);
        makeFlowPanel(buttonComponents);
        makeFlowPanel(outputComponents);

        output.setEditable(false);
        makeTreeButton.addActionListener(makeTreeListener);
        isBalancedButton.addActionListener(isBalancedListener);
        isFullButton.addActionListener(isFullListener);
        isProperButton.addActionListener(isProperListener);
        heightButton.addActionListener(heightListener);
        numNodesButton.addActionListener(numNodesListener);
        inOrderButton.addActionListener(inOrderListener);
    }

    /**
     * Makes a flow panel for each row of the inner GUI window by creating a panel
     * with a flow layout for each component in JComponent array
     * 
     * @param components array of JComponents
     */
    private void makeFlowPanel(JComponent[] components) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for (Component component : components) {
            panel.add(component);
        }
        add(panel);
    }

    public static void main(String[] args) {
        TreeGUI frame = new TreeGUI();
        frame.setVisible(true);
    }

}
