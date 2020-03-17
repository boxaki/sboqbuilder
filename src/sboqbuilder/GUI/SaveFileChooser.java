/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class SaveFileChooser {
    public static void save(List<String> queue) {
        
        if (queue.isEmpty()) {            
            JOptionPane.showMessageDialog(null, "The queue is empty");
            return;
        }
        
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
    
}
