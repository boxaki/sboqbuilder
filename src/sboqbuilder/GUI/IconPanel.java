/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import sboqbuilder.actions.Controller;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class IconPanel {

    private final JPanel iconPanel;
    
    public Controller controller;

    public IconPanel(Controller controller) {
    
        this.controller = controller;
        
        ActionListener saveListener = makeActionListener("saveQueue");
        JButton saveButton = makeButton("/Images/save24.png", saveListener, "Save queue");

        ActionListener listQListener = makeActionListener("listQ");
        JButton listQButton = makeButton("/Images/listQueue24.png", listQListener, "List queue");

        ActionListener deleteQListener = makeActionListener("deleteQ");
        JButton deleteQButton = makeButton("/Images/deleteQueue.png", deleteQListener, "Delete queue");

        ActionListener removeTreeListener = makeActionListener("removeTree");
        JButton removeTreeButton = makeButton("/Images/removeTree.png", removeTreeListener, "Remove tree");

        iconPanel = new JPanel();
        iconPanel.add(saveButton);
        iconPanel.add(listQButton);
        iconPanel.add(deleteQButton);
        iconPanel.add(removeTreeButton);    
        
    }

    public JPanel getIconPanel() {
        return iconPanel;
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
    
    private ActionListener makeActionListener(String action) {
        return EventHandler.create(ActionListener.class, controller, action);
    }
    
}
