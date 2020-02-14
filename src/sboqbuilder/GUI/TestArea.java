/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class TestArea {

    private final JTextArea testArea;

    public TestArea() {
        testArea = new JTextArea(30, 40);
        JScrollPane scrollPaneText = new JScrollPane(testArea);        
    }

    public JTextArea getTestArea() {
        return testArea;
    }
    
    public void append(String text) {
        testArea.append(text);
    }
    
}
