/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import sboqbuilder.actions.DataController;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class QueueEditor {

    private final JPanel queuePanel;
    private final DataController controller;
    private final JTextArea queueArea;
    private String selectedPackage;

    public QueueEditor(DataController controller) {
        this.controller = controller;
        this.selectedPackage = null;

        queueArea = new JTextArea(30, 40);
        queueArea.setEditable(false);
        queueArea.addMouseListener(new MouseHandler());
        JScrollPane queueAreaPane = new JScrollPane(queueArea);

        JPanel iconPanel = new JPanel();

        JButton upButton = new JButton("\u25b2");
        upButton.addActionListener(event -> controller.upInQueue(selectedPackage));
        iconPanel.add(upButton);

        JButton downButton = new JButton("\u25bc");
        downButton.addActionListener(event -> controller.downInQueue(selectedPackage));
        iconPanel.add(downButton);

        JButton removeButton = new JButton("D");
        removeButton.addActionListener(event -> {
            String selectedPackage1 = QueueEditor.this.selectedPackage;
            QueueEditor.this.selectedPackage = null;
            controller.removeFromQueue(selectedPackage1);
        });
        iconPanel.add(removeButton);

        queuePanel = new JPanel();
        queuePanel.setLayout(new BorderLayout());
        queuePanel.add(iconPanel, BorderLayout.SOUTH);
        queuePanel.add(queueAreaPane);

    }

    public JPanel getQueuePanel() {
        return queuePanel;
    }

    public void updateQueue() {
        queueArea.setText(null);
        List<String> queue = controller.getQueue();

        int line = 0;
        for (String pack : queue) {
            queueArea.append(pack + "\n");

            if (selectedPackage != null && selectedPackage.equals(pack)) {

                try {
                    int offset = queueArea.getLineStartOffset(line);
                    queueArea.setSelectionStart(offset);
                    queueArea.setSelectionEnd(offset + pack.length());

                } catch (BadLocationException ex) {
                    System.out.println("exception");

                }
            }
            line++;

        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            int position = queueArea.viewToModel(queueArea.getMousePosition());

            try {
                int line = queueArea.getLineOfOffset(position);
                queueArea.setSelectionStart(queueArea.getLineStartOffset(line));
                queueArea.setSelectionEnd(queueArea.getLineEndOffset(line) - 1);
                selectedPackage = queueArea.getSelectedText();
            } catch (BadLocationException ex) {

            }
        }

    }

}
