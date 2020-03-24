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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import sboqbuilder.actions.DataController;
import sboqbuilder.data.SboFile;

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
        this.selectedPackage = "";

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
            QueueEditor.this.selectedPackage = "";
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
        
        if(queue.isEmpty()) {
            unselectPackage();
        }
        
        int lineNumber = 0;
        for (String pack : queue) {
            queueArea.append(pack + "\n");

            if (!selectedPackage.isEmpty() && selectedPackage.equals(pack)) {

                try {
                    int offset = queueArea.getLineStartOffset(lineNumber);
                    queueArea.setSelectionStart(offset);
                    queueArea.setSelectionEnd(offset + pack.length());

                } catch (BadLocationException ex) {
                    System.out.println("exception");
                }
                
            }
            lineNumber++;

        }
    }
    
    public void unselectPackage() {
        selectedPackage = "";
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            selectPackage();
        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if (event.getButton() == 3 && !selectedPackage.isEmpty()) {
                RightClickMenu menu = new RightClickMenu();
                menu.show(event.getComponent(), event.getX(), event.getY());

            }
        }

        private void selectPackage() {
            
            int position = queueArea.viewToModel(queueArea.getMousePosition());
            

            try {             
                int line = queueArea.getLineOfOffset(position);            
            
                queueArea.select(queueArea.getLineStartOffset(line), queueArea.getLineEndOffset(line) -1 );
                selectedPackage = queueArea.getSelectedText();
                
            } catch (BadLocationException ex) {
                System.out.println("bad location exception");
            }
        }

    }

    private class RightClickMenu extends JPopupMenu {

        public RightClickMenu() {
            createMenuItem("README", SboFile.README);
            createMenuItem(".info", SboFile.info);
            createMenuItem("slack-desc", SboFile.slackdesc);
            createMenuItem(".SlackBuild", SboFile.SlackBuild);
        }

        private void createMenuItem(String itemName, SboFile file) {
            JMenuItem menuItem = new JMenuItem(itemName);
            add(menuItem);
            menuItem.addActionListener(event
                    -> QueueEditor.this.controller.showFile(QueueEditor.this.selectedPackage, file));
        }

    }

}
