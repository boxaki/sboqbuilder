/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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

        Action searchAction = new SearchAction("Ok");

        inputPanel.add(inputField);
        inputPanel.add(searchType);
        inputPanel.add(new JButton(searchAction));

        InputMap imap = inputPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ENTER"), "panel.search");

        ActionMap amap = inputPanel.getActionMap();
        amap.put("panel.search", searchAction);

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

    public class SearchAction extends AbstractAction {

        public SearchAction(String name) {
            putValue(Action.NAME, name);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            InputPanel.this.search();
        }
    }

}
