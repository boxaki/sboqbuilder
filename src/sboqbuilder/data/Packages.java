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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aki
 */
public class Packages {

    private final static String BASE_DIR_PATH = "/var/lib/sbopkg/SBo/14.2";
    private final Map<String, String> allSBos;
    private final List<String> installedSBos;

    public Packages() {
        allSBos = findAllPackages();
        installedSBos = findInstalledSBos(allSBos);
    }

    private static List findInstalledSBos(Map<String, String> allPackages) {
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

    private static Map<String, String> findAllPackages() {
        //esetleg try with resource

        Map<String, String> allSBos = new TreeMap<>();
        File packageInfoFile = new File("/var/lib/sbopkg/SBo/14.2/SLACKBUILDS.TXT");
        BufferedReader in = null;

        try {

            in = new BufferedReader(new InputStreamReader(new FileInputStream(packageInfoFile)));

        } catch (FileNotFoundException exc) {
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

        } catch (IOException exc) {

        }

        return allSBos;
    }

    public List<String> getFile(String packageName, SboFile sboFile) {

        String[] categoryDirs = getCategoryDirs();
        String categoryPath = findCategoryDir(packageName, categoryDirs);

        String packagePath = categoryPath + "/" + packageName + "/";
        switch (sboFile) {
            case README:
            case slackdesc:
                packagePath += sboFile.getName();
                break;
            case SlackBuild:
            case info:
                packagePath += packageName + "." + sboFile.getName();
                break;
        }

        File file = new File(packagePath);
        if (file.isFile()) {
            try {
                List<String> fileAsList = Files.readAllLines(Paths.get(packagePath));
                return fileAsList;

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Packages.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Packages.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    private String[] getCategoryDirs() {
        File baseDir = new File(BASE_DIR_PATH);

        return baseDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
    }

    private String findCategoryDir(String packageName, String[] categoryDirs) {
        for (String subDir : categoryDirs) {
            String categoryPath = BASE_DIR_PATH + "/" + subDir;
            File categoryDir = new File(categoryPath);

            String[] packageDir = categoryDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.equals(packageName) && new File(dir, name).isDirectory();
                }
            });

            if (packageDir.length > 0) {
                return categoryPath;
            }
        }

        return null;
    }

    public Map<String, String> getAllSBos() {
        return allSBos;
    }

    public List<String> getInstalledSBos() {
        return installedSBos;
    }

}
