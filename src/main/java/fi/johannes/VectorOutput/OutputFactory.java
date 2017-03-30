/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput;

import fi.johannes.Utilities.File.CFolderOperations;
import fi.johannes.VectorOutput.Vector.TokenVector;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.*;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class OutputFactory<E extends Comparable<E>> {
    List<E> universe;
    List<Multiset<Integer>> vectors;
    String output;
    private int vectorChunks = 25_000;
    
    public OutputFactory(){}
    // NOTE Multisets are already weighed according to preprocessing stuff before this
    /**
     * Creates and outputs vectors to a set folder
     * @param outputFolder
     * @param tokens 
     */
    public void createVectors(List<Multiset<E>> tokens){
        universe = createUniverse(tokens);
        vectors = new ArrayList<>();
        for(Multiset ms : tokens){
            Multiset<Integer> vector = createIntegerVector(ms, universe);
            vectors.add(vector);
        }
        
        
        // TODO Output to file 
        // TODO Verify the results
        
    }
    public void writeVectors(String outputFolder) throws NotDirectoryException{
        this.output = outputFolder;
        List<TokenVector> chunks = new ArrayList<>();
        File folder = CFolderOperations.createFolder(outputFolder);
        for(int i=0; i<vectorChunks && i<vectors.size(); i++){
            Multiset m = vectors.get(i);
            //chunks.add(new TokenVector(m));
            if(chunks.size()==vectorChunks){
                // TODO Create filewrite
            }
        }
        
    }
    /**
     * Creates the from master to Indexed vectors multiset e.g. 1:3 (index:count)
     * @param ms
     * @param sortedMaster
     * @return 
     */
    public Multiset<Integer> createIntegerVector(Multiset<E> ms, List<E> sortedMaster){
        Multiset<Integer> multiset = HashMultiset.create();
        for(Multiset.Entry<E> e : ms.entrySet()){
            multiset.add(sortedMaster.indexOf(e.getElement()), e.getCount());
        }
        return multiset;
    }
    // Take in a line and output a vector
    public List<E> createUniverse(List<Multiset<E>> tokens){
        Set<E> setMaster = new HashSet<>();
        for(Multiset<E> ms : tokens){
            Set<Multiset.Entry<E>> entrySet = ms.entrySet();
            for(Multiset.Entry<E> entry : entrySet){
                setMaster.add(entry.getElement());
                // Would be improved with sorted listed
            }
        }
        List<E> sortedMaster = sortSet(setMaster);
        setMaster.clear();
        return sortedMaster;
    }
    
    public List<E> sortSet(Set<E> set){
        ArrayList<E> arrList = new ArrayList<>();
        arrList.addAll(set);
        Collections.sort(arrList);
        //Collections.sort(arrList, StringComparators.ALPHABETICAL_ORDER);
        return arrList;
    }

    public int getVectorChunks() {
        return vectorChunks;
    }

    public void setVectorChunks(int vectorChunks) {
        this.vectorChunks = vectorChunks;
    }
    
}
