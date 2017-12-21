/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JTextArea;

/**
 *
 * @author aki
 */
public class Test {

    public static void checkInstalledPackages(List<String> installedPackages, Map<String, String> allPackages, JTextArea infoPanel) {
        List<String> badlyNamed = new ArrayList<>();

        for (String installed : installedPackages) {
            if (!allPackages.containsKey(installed)) {
                infoPanel.append("\nbadly named package: " + installed);
            }
        }
    }
  
}
