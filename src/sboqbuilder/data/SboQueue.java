/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboqbuilder.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aki
 */
public class SboQueue {

    private final List<String> queue;

    public SboQueue() {
        this.queue = new ArrayList<>();
    }

    public void delete() {
        queue.clear();
    }

    public List<String> getQueue() {
        return queue;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void up(String selectedPackage) {
        int index = queue.indexOf(selectedPackage);
        if (index > 0) {
            queue.set(index, queue.get(index - 1));
            queue.set(index - 1, selectedPackage);
        }
    }

    public void down(String selectedPackage) {
        int index = queue.indexOf(selectedPackage.trim());

        if (index < queue.size() - 1) {
            queue.set(index, queue.get(index + 1));
            queue.set(index + 1, selectedPackage);
        }
    }

    public void remove(String selectedPackage) {
        queue.remove(selectedPackage);
    }

}
