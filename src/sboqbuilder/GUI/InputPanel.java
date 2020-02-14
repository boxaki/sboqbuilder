/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.event.ActionListener;
import java.beans.EventHandler;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import sboqbuilder.actions.Controller;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class InputPanel {

    private final JPanel inputPanel;

    private final JComboBox<String> searchType;
    private final JTextField inputField;
        
    private Controller controller;

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
        
        ActionListener listener = getActionListener();
        okButton.addActionListener(listener);

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

    // az actionlistenerek, meg hívott fv-ek kulon class-ba, az lehetne a controller
    private ActionListener getActionListener() {
        return EventHandler.create(ActionListener.class, this, "search");
    }

    public void search() {  // buildTreeAndQueue        

        String type = searchType.getItemAt(searchType.getSelectedIndex());
        
        controller.buildTreeAndQueue(inputField.getText().trim(), type);
      
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
}
