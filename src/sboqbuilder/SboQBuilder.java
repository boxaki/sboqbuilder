/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder;

import java.awt.EventQueue;
import javax.swing.JFrame;
import sboqbuilder.GUI.SboQBuilderFrame;


/**
 *
 * @author aki
 */
public class SboQBuilder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SboQBuilderFrame frame = new SboQBuilderFrame();
            frame.setTitle("SboQBuilder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
