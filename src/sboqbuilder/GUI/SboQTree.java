/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class SboQTree{

    private JTree tree;
    // tree model, root node
    private DefaultTreeModel model;
    private DefaultMutableTreeNode root;

    private Set<String> treeNodeElements;

    private Map<String, String> allpackage;
    private List<String> installedSBos;
    
    private List<String> queue;
    
    private JTextArea testArea;
    private JLabel infoLabel;

    public SboQTree(List<String> installedSBos, Map<String, String> allpackage, List<String> queue,
            JTextArea testArea, JLabel infoLabel) {
        root = new DefaultMutableTreeNode("SBos");
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        this.installedSBos= installedSBos;
        SBoQTreeCellRenderer renderer = new SBoQTreeCellRenderer(installedSBos);
        tree.setCellRenderer(renderer);

        treeNodeElements = new HashSet<>();
        this.allpackage = allpackage;
        this.queue = queue;
        this.testArea = testArea;
        this.infoLabel = infoLabel;
    }
    
    public JTree getTree() {
        return tree;
    }
    
    private void addDependent(String packageName, DefaultMutableTreeNode startNode) { // felbontani a fv-t mini fv-ekre. es kiszedni a q-t
        queue.add(packageName);
        DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode(packageName);
        startNode.add(packageNode);

        for (String installed : installedSBos) {
            String depsString = allpackage.get(installed);
            if (depsString == null) {
                continue;
            }
            if (!depsString.isEmpty()) {
                String[] deps = depsString.split(" ");
                for (String dep : deps) {
                    if (dep.equals(packageName)) {
                        addDependent(installed, packageNode);
                        break;
                    }
                }
            }
        }
    }

    public void buildTree(String packageName) { //throws Exception {

        testArea.setText(null);

        //String packageName = inputField.getText().trim();
        if (packageName.isEmpty()) {
            infoLabel.setText("no package name given");
            JOptionPane.showMessageDialog(null, "No package name given!");
            return;
        }
        //if elseket atnezni, atalakítani
        if (!allpackage.containsKey(packageName)) {
            JOptionPane.showMessageDialog(null, "Package doesn't exist");
            return;
        } else {
            infoLabel.setText(packageName);
            if (!treeNodeElements.contains(packageName)) {
                addPackage(packageName, root);
            }

        }

        for (String str : queue) {
            testArea.append("\nqueue " + str);
        }

        displayTree();
    }

    private void addPackage(String packageName, DefaultMutableTreeNode startNode) {
        testArea.append(packageName + "\n");
        DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode(packageName);
        startNode.add(packageNode);
        if (packageName.equals("%README%")) {
            return;
        }
        String depsString = allpackage.get(packageName);

        if (!depsString.isEmpty()) {

            String[] deps = depsString.split(" ");
            for (String dep : deps) {
                addPackage(dep, packageNode);
            }
        }
        if (!treeNodeElements.contains(packageName)) {
            treeNodeElements.add(packageName);
        }
        if (!queue.contains(packageName)) {
            queue.add(packageName);

        }

    }
    
    public void buildDepsTree(String packageName) {
        
        if (packageName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "no package name given");
            return;
        } else if (!allpackage.containsKey(packageName)) {
            JOptionPane.showMessageDialog(null, "package doesn't exist");
            return;
        }

        addDependent(packageName, root);

        displayTree();

    }
    
    public void removeTree() {
        if (root.getChildCount() == 0) {
            // tesztelni, hogy néz ki
            JOptionPane.showMessageDialog(null, "The tree doesn't exist"); // a tree helyett null?
            return;
        }

        root.removeAllChildren();
       
        displayTree();
        treeNodeElements.clear();
        infoLabel.setText("Tree has been removed");
    }

    private void displayTree() {
        model.reload();
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }

}
