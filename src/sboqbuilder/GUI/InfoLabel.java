/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import javax.swing.JLabel;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class InfoLabel {

    private final JLabel infoLabel;

    public InfoLabel() {
        this.infoLabel = new JLabel("Hello Boss");
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(String message) {
        infoLabel.setText(message);
    }

}
