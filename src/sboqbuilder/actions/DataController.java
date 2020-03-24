/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.actions;

import java.util.List;
import java.util.Map;
import sboqbuilder.GUI.ViewController;
import sboqbuilder.data.Packages;
import sboqbuilder.data.SboFile;
import sboqbuilder.data.SboQueue;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public class DataController {

    private final SboQueue queue;
    private final Packages packages;
    private final ViewController view;

    private String lastSearchType;

    public DataController(ViewController view) {
        this.lastSearchType = "none";

        this.queue = new SboQueue();
        this.packages = new Packages();
        this.view = view;
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

    // interface treecontroller v csak a view-ba átrakni
    public void buildTreeAndQueue(String packageName, String type) {

        if ("none".equals(lastSearchType)) {
            lastSearchType = type;

        } else if (!lastSearchType.equals(type)) {

            if (!isEmptyQueue()) {
                queue.delete();
                view.showMessageDialog("The queue is not empty");
            }

            view.removeTree();
            lastSearchType = type;
        }

        if (type.equals("dep")) {  // ha elotte req volt es ki lett torolve a tree meg a q felugro ablakkal nyit
            view.buildDepsTree(packageName);
        } else if (type.equals("req")) {
            view.buildTree(packageName);
        }

        view.updateQueue();

    }

    public void deleteQueue() {
        // esetleg megerősités
        queue.delete();
        view.updateQueue();
    }

    public void saveQueue() {
        view.saveQueue(queue.getQueue());
    }

    public void removeTree() {
        view.removeTree();
    }

    public boolean isEmptyQueue() {
        return queue.getQueue().isEmpty();
    }

    public void upInQueue(String selectedPackage) {
        if (!selectedPackage.isEmpty()) {
            queue.up(selectedPackage);
            view.updateQueue();
        }
    }

    public void downInQueue(String selectedPackage) {
        if (!selectedPackage.isEmpty()) {
            queue.down(selectedPackage);
            view.updateQueue();
        }
    }

    public void removeFromQueue(String selectedPackage) {
        if (!selectedPackage.isEmpty()) {
            queue.remove(selectedPackage);
            view.updateQueue();
        }
    }

    public void quit() {
        System.exit(0);
    }

    public void showFile(String packageName, SboFile file) {
        List<String> readme = packages.getFile(packageName, file);
        
        if (readme != null) {
            view.showReadme(readme);
        }

    }

}
