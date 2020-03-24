/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.data;

/**
 *
 * @author Akos Varga <boxakos at gmail.com>
 */
public enum SboFile {
        
    README("README"), info("info"), slackdesc("slack-desc"), SlackBuild("SlackBuild");      
    
    SboFile(String file){
        this.file = file;        
    }
    
    private final String file;
    
    public String getName() {
        return file;
    }
}
