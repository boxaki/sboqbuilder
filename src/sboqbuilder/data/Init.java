/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author aki
 */
public class Init {

    /*
    public static List<String> findInstalledSBosOld(JTextArea testArea) {
        List<String> installedSBos = new ArrayList<>();

        File dir = new File("/var/log/packages");

        String[] files = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("SBo");
            }
        });
        for (String str : files) {
            testArea.append("\n " + str);
            int index = 0;
            int hyphenPos;
            int counter = 0;
            do {
                hyphenPos = str.indexOf("-", index);
                testArea.append("\nhyphen_position: " + hyphenPos);
                char ch = str.charAt(hyphenPos + 1);
                testArea.append(" character after hyphen: " + ch);
                char ch2;
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                    testArea.append("(character)");
                    index = hyphenPos + 1;
                    if ((ch2 = str.charAt(hyphenPos + 2)) >= '0' && ch2 <= '9') {
                        testArea.append(" number after char: " + ch2);
                        testArea.append("\nnumber after letter!!\n");
                        break;
                    }
                } else {
                    testArea.append("\nbreaking!!");
                    break;
                }
                counter++;
            } while (true);
            installedSBos.add((str.substring(0, hyphenPos).trim()));

        }
        for (String str : installedSBos) {
            testArea.append("\ninstalled " + str);

        }
        testArea.append("\nnumber of sbos:" + installedSBos.size());
        return installedSBos;
    }
*/

    public static List findInstalledSBos(Map<String, String> allPackages) {
        //Set<String> installedSBos = new HashSet<>();
        List<String> installedSBos = new ArrayList<>();
       

        File dir = new File("/var/log/packages");

        String[] files = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("SBo");
            }
        });

        for (String file : files) {

            StringBuilder fileName = new StringBuilder(file);
            int fromIndex = fileName.lastIndexOf("-");
            int toIndex = fileName.length();
            fileName.delete(fromIndex, toIndex);
            do {

                if (allPackages.containsKey(fileName.toString())) {
                    installedSBos.add(fileName.toString());
                    break;
                } else {
                    fromIndex = fileName.lastIndexOf("-");
                    if (fromIndex > 0) {
                        fileName.delete(fromIndex, fileName.length());
                    } else {
                        JOptionPane.showMessageDialog(null, "obsolete package found: " + file);                       
                        break;
                    }

                }
            } while (true);
        }
      
        return installedSBos;
    }

    public static Map<String, String> loadInfoFile(JLabel infoLabel) {
        //esetleg try with resource

        Map<String, String> allSBos = new TreeMap<>();
        File packageInfoFile = new File("/var/lib/sbopkg/SBo/14.2/SLACKBUILDS.TXT");
        BufferedReader in = null;
        try {

            in = new BufferedReader(new InputStreamReader(new FileInputStream(packageInfoFile)));

        } catch (FileNotFoundException exc) {
            infoLabel.setText("SLACKBUILDS.TXT not found");
            JOptionPane.showMessageDialog(null, "SLACKBUILDS.TXT not found");
        }

        String lineToRead;

        //atirni try with resource-ra
        try {
            String packageName = "";
            String depLine = "";

            while ((lineToRead = in.readLine()) != null) {
                if (lineToRead.startsWith("SLACKBUILD NAME")) {
                    packageName = lineToRead.replace("SLACKBUILD NAME: ", "");

                } else if (lineToRead.startsWith("SLACKBUILD REQUIRES")) {
                    depLine = (lineToRead.replace("SLACKBUILD REQUIRES: ", "")).trim();
                    allSBos.put(packageName, depLine);

                }
            }
            in.close();
            infoLabel.setText("info file loaded");
        } catch (IOException exc) {
            infoLabel.setText("problem with reading the file");
        }

        return allSBos;
    }
}
