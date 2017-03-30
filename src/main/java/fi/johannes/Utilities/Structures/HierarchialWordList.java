/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author Johannes tÃ¶issÃ¤
 */
public class HierarchialWordList {
    // TODO Shouldn't this be hashmap for quick access with queue?
    ArrayList<HierarchialWord> words;
    Queue<Integer> idQueue;
    
    public HierarchialWordList(){
        words = new ArrayList();
        idQueue = new ArrayDeque();
    }
    public void addWord(HierarchialWord hw){
        this.words.add(hw);
        idQueue.add(hw.getID());
    }
    
}
