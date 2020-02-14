/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.actions;

import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import sboqbuilder.GUI.SboQTree;
import sboqbuilder.data.Packages;
import sboqbuilder.data.SboQueue;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class Controller {

    private final JLabel infoLabel;
    private final SboQueue queue;
    private final SboQTree tree; // ezt ki kéne szedni
    private final Packages packages;
    private final JTextArea testArea;

    private String lastSearchType;

    public Controller(JLabel infoLabel, SboQTree tree, JTextArea testArea) {
        this.lastSearchType = "none";

        this.infoLabel = infoLabel;
        this.tree = tree;
        this.testArea = testArea;
        this.queue = new SboQueue();
        this.packages = new Packages(infoLabel);

    }

    public List<String> getQueue() {
        return queue.getQueue();
    }

    public Map<String, String> getAllSbos() {
        return packages.getAllSBos();
    }

    public List<String> getInstalledSbos() {
        return packages.getInstalledSBos();
    }

    public void buildTreeAndQueue(String packageName, String type) {

        if ("none".equals(lastSearchType)) {
            lastSearchType = type;

        } else if (!lastSearchType.equals(type)) {
            queue.delete(infoLabel); // deleteQ-bol kiszedni az info labelt, inkább itt állítsa be
            tree.removeTree();
            lastSearchType = type;
        }

        if (type.equals("dep")) {  // ha elotte req volt es ki lett torolve a tree meg a q felugro ablakkal nyit
            tree.buildDepsTree(packageName);
        } else if (type.equals("req")) {
            tree.buildTree(packageName);
        }

    }

    public void deleteQ() {
        queue.delete(infoLabel);
    }

    public void listQ() {
        queue.list(testArea, infoLabel);
    }

    public void saveQueue() {
        queue.save(infoLabel);
    }

    public void removeTree() {
        tree.removeTree();
    }

}
