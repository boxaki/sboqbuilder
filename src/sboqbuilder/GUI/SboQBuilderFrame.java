/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import sboqbuilder.actions.DataController;

/**
 *
 * @author aki
 */
public class SboQBuilderFrame extends JFrame implements ViewController {

    private final SboQTree sboQTree;
    private final QueueEditor queueEditor;
    private final InfoLabel label;
    private final DataController controller;

    private Set<String> obsoletePackages;

    public SboQBuilderFrame() {
        controller = new DataController(this);

        label = new InfoLabel();
        JLabel infoLabel = label.getInfoLabel();

        //obsoletePackages = new HashSet<>();         
        //Test.listObsolete(obsoletePackages, testArea);
        setJMenuBar(new MenuBar(controller).getMenuBar());

        sboQTree = new SboQTree(controller, infoLabel);

        queueEditor = new QueueEditor(controller);
        JPanel queuePane = queueEditor.getQueuePanel();
        Dimension dimension = queuePane.getPreferredSize();

        InputPanel inputPanel = new InputPanel();
        inputPanel.setController(controller);

        //icon panel
        IconPanel iconPanel = new IconPanel(controller);

        JScrollPane scrollPaneTree = new JScrollPane(sboQTree.getTree());
        scrollPaneTree.setPreferredSize(dimension);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        // inputPanel és iconPanel egy topPanel-be?
        add(inputPanel.getInputPanel(), new GBC(0, 0).setAnchor(GBC.NORTHWEST).setWeight(0, 0));
        add(iconPanel.getIconPanel(), new GBC(1, 0).setAnchor(GBC.WEST).setWeight(0, 0));
        add(queuePane, new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setWeight(60, 100).setInsets(10));

        add(scrollPaneTree, new GBC(2, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10));
        add(label.getInfoLabel(), new GBC(0, 2).setAnchor(GBC.SOUTHWEST).setFill(GBC.HORIZONTAL));

        // Test.checkInstalledPackages(installedSBos, allPackages, testArea);
        pack();

    }

    @Override
    public void updateQueue() {
        queueEditor.updateQueue();
    }

    @Override
    public void removeTree() {
        sboQTree.removeTree();
    }

    @Override
    public void buildTree(String packageName) {
        sboQTree.buildTree(packageName);
    }

    @Override
    public void buildDepsTree(String packageName) {
        sboQTree.buildDepsTree(packageName);
    }

    @Override
    public void setInfoLabel(String message) {
        label.setInfoLabel(message);
    }

    @Override
    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void saveQueue(List<String> queue) {
        SaveFileChooser.save(queue);
    }

    /*
    private void listAllPackages() {
        for (Map.Entry<String, String> entry : allPackages.entrySet()) {
            System.out.println(entry.getKey() + "[ " + entry.getValue() + "]");

        }
    }
     */
}
