/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import sboqbuilder.actions.DataController;
import sboqbuilder.test.Test;
import sboqbuilder.data.Packages;
import static sboqbuilder.data.Packages.findAllPackages;
import sboqbuilder.data.SboQueue;

/**
 *
 * @author aki
 */
public class SboQBuilderFrame extends JFrame {
    
    //data structures
    private Map<String, String> allPackages;
    private List<String> installedSBos;
    private SboQTree sboQTree;
    
    private Set<String> obsoletePackages;
    
    private InfoLabel label;
    
    private static DataController controller;
            

    public SboQBuilderFrame() {
        label = new InfoLabel();
        JLabel infoLabel = label.getInfoLabel();
        
        long callTime = System.currentTimeMillis();
        allPackages = findAllPackages(infoLabel);
        //obsoletePackages = new HashSet<>();         
        installedSBos = Packages.findInstalledSBos(allPackages);
        //Test.listObsolete(obsoletePackages, testArea);
        callTime = System.currentTimeMillis() - callTime;

        //menu bar        
        setJMenuBar(new MenuBar().getMenuBar());

        JTextArea testArea = new TestArea().getTestArea();
        JScrollPane scrollPaneText = new JScrollPane(testArea);
        Dimension dimension = testArea.getPreferredSize();
        testArea.setMaximumSize(dimension);
        scrollPaneText.setPreferredSize(dimension);

        testArea.append("\nloaded in " + callTime + " ms");

        //input panel
        SboQueue squeue = new SboQueue();
        
        List<String> queue = squeue.getQueue(); // controller.getQueue();
        sboQTree = new SboQTree(installedSBos, allPackages, queue, testArea, infoLabel);
        
        controller = new DataController(infoLabel, sboQTree, testArea, squeue);

        InputPanel inputPanel = new InputPanel();
        inputPanel.setController(controller);

        //icon panel
        IconPanel iconPanel = new IconPanel(controller) ;
        
        JScrollPane scrollPaneTree = new JScrollPane(sboQTree.getTree());
        scrollPaneTree.setPreferredSize(dimension);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        add(inputPanel.getInputPanel(), new GBC(0, 0).setAnchor(GBC.NORTHWEST).setWeight(0, 0));
        add(iconPanel.getIconPanel(), new GBC(1, 0).setAnchor(GBC.WEST).setWeight(0, 0));
        add(scrollPaneText, new GBC(0, 1, 2, 1).setFill(GBC.BOTH).setWeight(60, 100).setInsets(10));
        add(scrollPaneTree, new GBC(2, 1, 2, 1).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10));
        add(infoLabel, new GBC(0, 2).setAnchor(GBC.SOUTHWEST).setFill(GBC.HORIZONTAL));

       
        Test.checkInstalledPackages(installedSBos, allPackages, testArea);

        pack();

    }

    private void listAllPackages() {
        for (Map.Entry<String, String> entry : allPackages.entrySet()) {
            System.out.println(entry.getKey() + "[ " + entry.getValue() + "]");

        }
    }
    
    /*
    private void removeTree() {
        sboQTree.removeTree();
        
    }
*/

}
