/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.Dimension;
import java.awt.Insets;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import sboqbuilder.actions.DataController;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class IconPanel {

    private final JPanel iconPanel;

    public DataController controller;

    public IconPanel(DataController controller) {

        this.controller = controller;

        JButton saveButton = makeButton("/Images/save24.png", "Save queue");
        saveButton.addActionListener(event -> controller.saveQueue());

        JButton listQButton = makeButton("/Images/listQueue24.png", "List queue");
        listQButton.addActionListener(event -> controller.listQ());

        JButton deleteQButton = makeButton("/Images/deleteQueue.png", "Delete queue");
        deleteQButton.addActionListener(event -> controller.deleteQ());

        JButton removeTreeButton = makeButton("/Images/removeTree.png", "Remove tree");
        removeTreeButton.addActionListener(event -> controller.removeTree());

        iconPanel = new JPanel();
        iconPanel.add(saveButton);
        iconPanel.add(listQButton);
        iconPanel.add(deleteQButton);
        iconPanel.add(removeTreeButton);
    }

    public JPanel getIconPanel() {
        return iconPanel;
    }

    private JButton makeButton(String iconName, String tooltip) {
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

        button.setToolTipText(tooltip);
        return button;
    }

}
