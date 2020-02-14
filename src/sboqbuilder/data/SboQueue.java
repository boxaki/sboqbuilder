/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.data;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author aki
 */
public class SboQueue {

    private final List<String> queue;
    
    public SboQueue() {
        this.queue = new ArrayList<>();
    }

    public void save(JLabel infoLabel) {
        if (queue.isEmpty()) {
            infoLabel.setText("The queue is empty");
            JOptionPane.showMessageDialog(null, "The queue is empty");
            return;
        }

        infoLabel.setText("saving...");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("./"));
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".sqf");
            }

            @Override
            public String getDescription() {
                return "Sbo queue file(.sqf)";
            }
        });
        int r = chooser.showSaveDialog(null);
        if (r != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file;
        String ext = null;
        String extension = chooser.getFileFilter().getDescription();
        if (extension.endsWith("(.sqf)")) {
            ext = ".sqf";
        }
        if (ext != null) {
            String fileName = chooser.getSelectedFile().toString() + ext;
            file = new File(fileName);
            infoLabel.setText(file.getName() + " saved");

        } else {
            file = chooser.getSelectedFile();
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter pw = new PrintWriter(file);

            for (String str : queue) {
                pw.print(str + "\n");
            }
            pw.flush();
            pw.close();
        } catch (Exception exc) {

        }

    }

    public void list(JTextArea testArea, JLabel infoLabel) {
        if (queue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The queue is empty");
            return;
        }

        infoLabel.setText("Listing queue");
        testArea.append("\nlistingQ\n\n");

        for (String str : queue) {
            testArea.append(str + "\n");
        }

    }

    public void delete(JLabel infoLabel) {
        if (queue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The queue is empty");
            return;
        }

        queue.clear();
        infoLabel.setText("Queue deleted");
    }

    public List<String> getQueue() {
        return queue;
    }
    
    

}
