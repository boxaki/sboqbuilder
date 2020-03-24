/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.GUI;

import java.util.List;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public interface ViewController {

    public void updateQueue();

    public void removeTree();

    public void buildTree(String packageName);

    public void buildDepsTree(String packageName);

    public void setInfoLabel(String message);

    public void showMessageDialog(String message);

    public void saveQueue(List<String> queue);
    
    public void showReadme(List<String> readme);
}
