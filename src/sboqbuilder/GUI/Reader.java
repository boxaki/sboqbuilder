/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class Reader {

    private final JFrame readerFrame;
    private final JTextArea textArea;

    public Reader() {
        textArea = new JTextArea(30, 50);
        textArea.setEditable(false);

        JScrollPane scrollPaneText = new JScrollPane(textArea);

        readerFrame = new JFrame();
        readerFrame.add(scrollPaneText);
    }

    public JFrame getReaderFrame() {
        return readerFrame;
    }
    
    public void append(String text) {
        textArea.append(text);
    }

}
