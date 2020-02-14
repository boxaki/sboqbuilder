/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author aki
 */
public class SBoQTreeCellRenderer implements TreeCellRenderer {

    private JLabel label;
    private List<String> installedSBos;

    public SBoQTreeCellRenderer(List<String> installedSBos) {
        label = new JLabel();
        this.installedSBos = installedSBos;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        String pkg = (String) ((DefaultMutableTreeNode) value).getUserObject();
        /*
        URL imageUrl = getClass().getResource("/Images/s.png");
        label.setIcon(new ImageIcon(imageUrl));
        label.setForeground(Color.red);

        for (String str : installedSBos) {
            if (pkg.equals(str)) {
                imageUrl = getClass().getResource("/Images/s2.png");
                label.setIcon(new ImageIcon(imageUrl));
                label.setForeground(Color.blue);
                break;
            }
        }
         */

        if (installedSBos.contains(pkg)) {
            //label.setForeground(Color.red);            
            URL imageUrl = getClass().getResource("/Images/installed.png");
            label.setIcon(new ImageIcon(imageUrl));
            label.setForeground(Color.black);

        } else {
            URL imageUrl = getClass().getResource("/Images/notInstalled.png");
            label.setIcon(new ImageIcon(imageUrl));
            label.setForeground(Color.red);
        }

        label.setText(pkg);
        return label;

    }

}
