/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import sboqbuilder.test.Test;
import sboqbuilder.data.Init;
import static sboqbuilder.data.Init.loadInfoFile;

/**
 *
 * @author aki
 */
public class SboQBuilderFrame extends JFrame {

    //GUI
    private final JTextField inputField;
    private final JTextArea testArea;
    private final JLabel infoLabel;
    private final JComboBox<String> searchType;
    private final JTree tree;
    // tree model, root node
    private DefaultTreeModel model;
    private final DefaultMutableTreeNode root;
    //data structures
    private Map<String, String> allpackage;
    private List<String> installedSBos;
    private Set<String> treeNodeElements;
    private List<String> queue;

    private String lastSearch;
    private Set<String> obsoletePackages;

    public SboQBuilderFrame() {
        //menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);

        ActionListener openListener = EventHandler.create(ActionListener.class, this, "open");
        openItem.addActionListener(openListener);

        inputField = new JTextField(20);
        inputField.setText("calibre");

        JPanel inputPanel = new JPanel();

        searchType = new JComboBox<>();
        searchType.addItem("req");
        searchType.addItem("dep");
        //tooltipet adni az itemekhez
        //searchType.setToolTipText("REQ: list requirement packages DEP: list dependent packages");

        JButton okButton = new JButton("Ok");

        ActionListener listener = EventHandler.create(ActionListener.class, this, "search");
        okButton.addActionListener(listener);

        inputPanel.add(inputField);
        inputPanel.add(searchType);
        inputPanel.add(okButton);

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "package name");
        inputPanel.setBorder(titled);
        

        ActionListener saveListener = EventHandler.create(ActionListener.class, this, "saveQueue");
        JButton saveButton = makeButton("/Images/save24.png", saveListener, "Save queue");

        ActionListener listQListener = EventHandler.create(ActionListener.class, this, "listQ");
        JButton listQButton = makeButton("/Images/listQueue24.png", listQListener, "List queue");

        ActionListener deleteQListener = EventHandler.create(ActionListener.class, this, "deleteQ");
        JButton deleteQButton = makeButton("/Images/deleteQueue.png", deleteQListener, "Delete queue");

        ActionListener removeTreeListener = EventHandler.create(ActionListener.class, this, "removeTree");
        JButton removeTreeButton = makeButton("/Images/removeTree.png", removeTreeListener, "Remove tree");
      
        JPanel iconPanel = new JPanel();
        iconPanel.add(saveButton);
        iconPanel.add(listQButton);
        iconPanel.add(deleteQButton);
        iconPanel.add(removeTreeButton);

        infoLabel = new JLabel("Hi Boss");
       

        testArea = new JTextArea(30, 40);
        JScrollPane scrollPaneText = new JScrollPane(testArea);
        Dimension dimension = testArea.getPreferredSize();
        testArea.setMaximumSize(dimension);
        scrollPaneText.setPreferredSize(dimension);

        long callTime = System.currentTimeMillis();

        allpackage = loadInfoFile(infoLabel);
        //obsoletePackages = new HashSet<>();         
        installedSBos = Init.findInstalledSBos(allpackage);
        //Test.listObsolete(obsoletePackages, testArea);
        callTime = System.currentTimeMillis() - callTime;
        testArea.append("\nloaded in " + callTime + " ms");

        //installedSBos = Init.findInstalledSBos2();
        root = new DefaultMutableTreeNode("SBos");
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        JScrollPane scrollPaneTree = new JScrollPane(tree);
        scrollPaneTree.setPreferredSize(dimension);

        SBoQTreeCellRenderer renderer = new SBoQTreeCellRenderer(installedSBos);
        tree.setCellRenderer(renderer);

        //initTree(dimension);
        queue = new ArrayList<>();
        treeNodeElements = new HashSet<>();

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        add(inputPanel, new GBC(0, 0).setAnchor(GBC.NORTHWEST).setWeight(0, 0));
        add(iconPanel, new GBC(1, 0).setAnchor(GBC.WEST).setWeight(0, 0));
        add(scrollPaneText, new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setWeight(60, 100).setInsets(10));
        add(scrollPaneTree, new GBC(2, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10));
        add(infoLabel, new GBC(0, 2).setAnchor(GBC.SOUTHWEST).setFill(GBC.HORIZONTAL));

        /*
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.WEST);
        add(new JScrollPane(tree));
        add(infoLabel, BorderLayout.SOUTH);
         */
        Test.checkInstalledPackages(installedSBos, allpackage, testArea);

        pack();

    }

    private JButton makeButton(String iconName, ActionListener listener, String tooltip) {
        URL url = getClass().getResource(iconName);
        JButton button = new JButton(new ImageIcon(url));
        button.setMargin(new Insets(0, 0, 0, 0));
        //Border noBorder = BorderFactory.createEmptyBorder();
        //button.setBorder(noBorder);
        button.setBorderPainted(false);
        //button.tunteseel a hatterkepet
        button.setContentAreaFilled(false);
        //button.setRolloverEnabled(true);
        Dimension ds = button.getMinimumSize();
        button.setMaximumSize(ds);
        button.addActionListener(listener);
        button.setToolTipText(tooltip);
        return button;

    }

    /*
    private void initTree(Dimension dimension) {
        
        root = new DefaultMutableTreeNode("SBos");
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
         
        JTree tree = new SBoTree(installedSBos).getSBoTree();
        JScrollPane scrollPaneTree = new JScrollPane(tree);
        scrollPaneTree.setPreferredSize(dimension);

        
        SBoQTreeCellRenderer renderer = new SBoQTreeCellRenderer(installedSBos);
        tree.setCellRenderer(renderer);
        
    }
     */
    public void open() {
        //JOptionPane.showMessageDialog(this, "click \"Ok\" if you are stupid");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/var/lib/sbopkg/queues"));
        chooser.setFileFilter(new FileNameExtensionFilter("Slackware queue files", "sqf"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            infoLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    public void search() {

        String command = searchType.getItemAt(searchType.getSelectedIndex());

        if (lastSearch == null) {
            lastSearch = command;
        } else if (!lastSearch.equals(command)) {
            deleteQ();
            removeTree();
            lastSearch = command;

        }

        if (command.equals("dep")) {
            buildDepsTree();
        } else {
            buildTree();
        }
    }

    public void removeTree() {
        if (root.getChildCount() == 0) {
            JOptionPane.showMessageDialog(this, "The tree doesn't exist");
            return;
        }

        root.removeAllChildren();
       
        displayTree();
        treeNodeElements.clear();
        infoLabel.setText("Tree has been removed");
    }

    public void deleteQ() {
        sboqbuilder.data.SboQueue.deleteQ(queue, infoLabel);
    }

    public void listQ() {
        sboqbuilder.data.SboQueue.listQueue(queue, testArea, infoLabel);
    }

    public void saveQueue() {
        sboqbuilder.data.SboQueue.saveQueue(queue, infoLabel);
    }

    public void buildTree() { //throws Exception {

        testArea.setText(null);

        String packageName = inputField.getText().trim();

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

    public void buildDepsTree() {
        String packageName = inputField.getText().trim();

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

    private void addDependent(String packageName, DefaultMutableTreeNode startNode) {
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

    private void displayTree() {
        model.reload();
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

    }

    private void listAllPackages() {
        for (Map.Entry<String, String> entry : allpackage.entrySet()) {
            System.out.println(entry.getKey() + "[ " + entry.getValue() + "]");

        }

    }

}
