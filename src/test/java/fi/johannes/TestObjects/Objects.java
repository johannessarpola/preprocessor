/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.TestObjects;

import fi.johannes.VectorOutput.Vector.TokenVector;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Just some objects to use in tests
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class Objects {

    public static final Multiset<Integer> testIntegerMultiSet, testIntegerMultiSet2;
    public static final TokenVector testTokenVector, testTokenVector2;
    static {
        AtomicLong al =new AtomicLong();
        
        testIntegerMultiSet  = HashMultiset.create();
        testIntegerMultiSet2  = HashMultiset.create();
        testIntegerMultiSet.add(10);
        testIntegerMultiSet.add(2, 10);
        testIntegerMultiSet.add(1, 2);
        testIntegerMultiSet.add(3, 5);
        testIntegerMultiSet.add(Integer.MAX_VALUE);
        testIntegerMultiSet.add(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        testIntegerMultiSet2.add(555, 2);
        testIntegerMultiSet2.add(444, 6);
        testIntegerMultiSet2.add(777, 8);
        testIntegerMultiSet2.add(8080, 80);
        
        testTokenVector = new TokenVector(testIntegerMultiSet, al);
        testTokenVector2 = new TokenVector(testIntegerMultiSet2, al);
    }
    
    
}
