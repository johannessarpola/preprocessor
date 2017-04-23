/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class OutputFactoryTest {

    static OutputFactory of;

    public OutputFactoryTest() {
    }

    /**
     * Test of createUniverse method, of class OutputFactory.
     */
    @Test
    public void testCreateMaster() {

        of = new OutputFactory<String>();
        Multiset<String> ms = HashMultiset.create();
        ms.add("A");
        ms.add("B");
        ms.add("C");
        ms.add("A");
        ms.add("B");
        ms.add("C", 10);
        
        Multiset<String> ms2 = HashMultiset.create();
        ms.add("A");
        ms.add("D");
        ms.add("C");
        ms.add("A");
        ms.add("B");
        ms.add("D", 10);
        
        List<Multiset<String>> lMs = new ArrayList();
        lMs.add(ms); lMs.add(ms2);
        
        List<String> mstr = of.createUniverse(lMs);
        List<String> expect = new ArrayList<>();
        expect.add("A"); expect.add("B");  expect.add("C"); expect.add("D"); 
       
        for(int i = 0; i<mstr.size(); i++){
            assertThat(mstr.get(i), is(expect.get(i)));
        }

    }

    /**
     * Test of sortSet method, of class OutputFactory.
     */
    @Test
    public void testSortSet() {

        of = new OutputFactory<String>();
        Multiset<String> ms = HashMultiset.create();
        ms.add("A");
        ms.add("B");
        ms.add("C");
        ms.add("A");
        ms.add("B");
        ms.add("C", 10);


        Set<String> set = new HashSet<>(ms);
        Set<String> orig = new HashSet<>(ms);


        Set<String> sorted = of.sortSet(set);
        Assert.assertEquals ("List is not sorted", sorted, orig);


    }

}
