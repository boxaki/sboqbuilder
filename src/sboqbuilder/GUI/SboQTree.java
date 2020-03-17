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
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import sboqbuilder.actions.DataController;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class SboQTree {

    private final JTree tree;
    private final DefaultTreeModel model;
    private final DefaultMutableTreeNode root;
    private final Set<String> treeNodeElements;

    private final DataController controller;

    // megpróbálni nem instance-ként használni őket
    private Map<String, String> allPackages;
    private List<String> installedSBos;
    private List<String> queue;

    private JLabel infoLabel;

    public SboQTree(DataController controller, JLabel infoLabel) {
        this.controller = controller;

        root = new DefaultMutableTreeNode("SBos");
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        this.installedSBos = controller.getInstalledSbos();
        SBoQTreeCellRenderer renderer = new SBoQTreeCellRenderer(installedSBos);
        tree.setCellRenderer(renderer);

        treeNodeElements = new HashSet<>();
        this.allPackages = controller.getAllSbos();
        this.queue = controller.getQueue();

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
            String depsString = allPackages.get(installed);
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

        if (packageName.isEmpty()) {
            infoLabel.setText("no package name given");
            JOptionPane.showMessageDialog(null, "No package name given!");
            return;
        }
        //if elseket atnezni, atalakítani
        if (!allPackages.containsKey(packageName)) {
            JOptionPane.showMessageDialog(null, "Package doesn't exist");
            return;
        } else {
            infoLabel.setText(packageName);
            if (!treeNodeElements.contains(packageName)) {
                addPackage(packageName, root);
            }

        }

        displayTree();
    }

    private void addPackage(String packageName, DefaultMutableTreeNode startNode) {

        DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode(packageName);
        startNode.add(packageNode);
        if (packageName.equals("%README%")) {
            return;
        }
        String depsString = allPackages.get(packageName);

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
        } else if (!allPackages.containsKey(packageName)) {
            JOptionPane.showMessageDialog(null, "package doesn't exist");
            return;
        }

        addDependent(packageName, root);

        displayTree();

    }

    public void removeTree() {
        if (root.getChildCount() == 0) {
            JOptionPane.showMessageDialog(null, "The tree doesn't exist");
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
