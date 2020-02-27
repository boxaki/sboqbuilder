/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.actions;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public interface DataActions {
    
    List<String> getQueue();
    void deleteQueue();
    void saveQueue();
    
    Map<String, String> getAllPackages();
    List<String> getInstalledPackages();
    
}
