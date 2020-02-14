/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class MenuBar {
    JMenuBar menuBar = new JMenuBar();

    public MenuBar() {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        ActionListener openListener = EventHandler.create(ActionListener.class, this, "open");
        openMenuItem.addActionListener(openListener);
    }
    
    public JMenuBar getMenuBar() {
        return menuBar;
    }
    
    public void open() {
        //JOptionPane.showMessageDialog(this, "click \"Ok\" if you are stupid");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/var/lib/sbopkg/queues"));
        chooser.setFileFilter(new FileNameExtensionFilter("Slackware queue files", "sqf"));
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            // infoLabel.setText(selectedFile.getAbsolutePath());
        }
    }
      

}
