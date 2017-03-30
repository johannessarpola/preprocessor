/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

/**
 * 
 *
 * @author Johannes töissä
 */

// TODO Redo thisf class. Interface is not needed
public class HierarchialWord {
    String stemmedWord;
    int idNumber;
    int parentIdNumber;
    
    private HierarchialWord(String stemmedWord, int id){
        this.stemmedWord = stemmedWord;
        this.idNumber = id;
    }

    public HierarchialWord buildWord(String word, int id) {
        return new HierarchialWord(word, id);
    }

    public String getWord() {
        return stemmedWord;
    }

    public int getParentID() {
        return parentIdNumber;
    }

    public int getID() {
        return idNumber;
    }

    public void linkToParent(int parentID) {
        this.parentIdNumber = parentID;
    }
    
}
