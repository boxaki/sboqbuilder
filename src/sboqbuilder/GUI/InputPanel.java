/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import sboqbuilder.actions.DataController;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class InputPanel {

    private final JPanel inputPanel;

    private final JComboBox<String> searchType;
    private final JTextField inputField;
        
    private DataController controller;
    
    private String lastSearchType;


    public InputPanel() {
        
        inputField = new JTextField(20);
        inputField.setText("calibre");

        inputPanel = new JPanel();

        searchType = new JComboBox<>();
        searchType.addItem("req");
        searchType.addItem("dep");
        //tooltipet adni az itemekhez
        //searchType.setToolTipText("REQ: list requirement packages DEP: list dependent packages");

        JButton okButton = new JButton("Ok");
        
        okButton.addActionListener(event -> search());

        inputPanel.add(inputField);
        inputPanel.add(searchType);
        inputPanel.add(okButton);

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "package name");
        inputPanel.setBorder(titled);
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }
   
    public void search() {  // rename buildTreeAndQueue        

        String type = searchType.getItemAt(searchType.getSelectedIndex());
        
        controller.buildTreeAndQueue(inputField.getText().trim(), type);      
    }

    public void setController(DataController controller) {
        this.controller = controller;
    }
    
    /*
    public void buildTreeAndQueue(String packageName, String type) {

        if ("none".equals(lastSearchType)) {
            lastSearchType = type;

        } else if (!lastSearchType.equals(type)) {
            
            if(controller.getQueue().isEmpty()) {
                controller.deleteQ();// deleteQ-bol kiszedni az info labelt, inkább itt állítsa be
            }
            tree.removeTree();
            lastSearchType = type;
        }

        if (type.equals("dep")) {  // ha elotte req volt es ki lett torolve a tree meg a q felugro ablakkal nyit
            tree.buildDepsTree(packageName);
        } else if (type.equals("req")) {
            tree.buildTree(packageName);
        }

    }
*/
    
}
