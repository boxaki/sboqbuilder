/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import sboqbuilder.actions.DataController;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class MenuBar {

    JMenuBar menuBar = new JMenuBar();
    DataController controller;

    public MenuBar(DataController controller) {
        this.controller = controller;

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(event -> open());

        Action quitAction = new QuitAction("Quit");
        JMenuItem quitMenuItem = new JMenuItem(quitAction);

        InputMap imap = menuBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ctrl Q"), "app.quit");

        ActionMap amap = menuBar.getActionMap();
        amap.put("app.quit", quitAction);

        fileMenu.add(quitMenuItem);

    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void open() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/var/lib/sbopkg/queues"));
        chooser.setFileFilter(new FileNameExtensionFilter("Slackware queue files", "sqf"));
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            // infoLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    public class QuitAction extends AbstractAction {

        public QuitAction(String name) {
            putValue(Action.NAME, name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.quit();
        }

    }

}
