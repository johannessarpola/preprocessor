/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.Files;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.StringCharacterIterator;
import java.util.*;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class OutputFactoryTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    static OutputFactory<String> outputFactory;

    public OutputFactoryTest() {
    }

    /**
     * Test outputFactory createUniverse method, outputFactory class OutputFactory.
     */
    @Test
    public void testCreateMaster() {

        outputFactory = new OutputFactory<String>();
        Multiset<String> ms = HashMultiset.create();
        ms.add("A");
        ms.add("B");
        ms.add("C");
        ms.add("A");
        ms.add("B");
        ms.add("C", 10);
        
        Multiset<String> ms2 = HashMultiset.create();
        ms2.add("A");
        ms2.add("D");
        ms2.add("C");
        ms2.add("A");
        ms2.add("B");
        ms2.add("D", 10);
        
        List<Multiset<String>> multisetList = new ArrayList();
        multisetList.add(ms);
        multisetList.add(ms2);
        
        List<String> mstr = outputFactory.createUniverse(multisetList);
        List<String> expect = new ArrayList<>();
        expect.add("A"); expect.add("B");  expect.add("C"); expect.add("D"); 
       
        for(int i = 0; i<mstr.size(); i++){
            assertThat(mstr.get(i), is(expect.get(i)));
        }

    }

    @Test
    public void testWriteEntityVectors() throws IOException {

        File file = temporaryFolder.newFolder();
        String path = file.getAbsolutePath();

        outputFactory = new OutputFactory<String>(path);

        Multiset<String> ms = HashMultiset.create();
        ms.add("A");
        ms.add("A");
        ms.add("A");
        ms.add("D");
        ms.add("C");
        ms.add("A");
        ms.add("B");
        ms.add("D", 10);

        Multiset<String> ms2 = HashMultiset.create();
        ms2.add("A");
        ms2.add("D");
        ms2.add("C");
        ms2.add("A");
        ms2.add("B");
        ms2.add("D", 10);

        Multiset<String> ms3 = HashMultiset.create();
        ms3.add("A");
        ms3.add("D");
        ms3.add("C");
        ms3.add("A");
        ms3.add("B");
        ms3.add("D", 99);

        List<Multiset<String>> multisetList = new ArrayList<>();
        multisetList.add(ms);
        multisetList.add(ms2);
        multisetList.add(ms3);

        outputFactory.writeEntityVectors(multisetList, 1);
        assertThat(file.listFiles().length, is(3));
        List<String> contents = new ArrayList<>();
        Arrays.stream(file.listFiles()).forEach(f -> {
            try {
                contents.add(Files.readFirstLine(f, Charset.defaultCharset()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        assertThat(contents.size(), is(3));
        assertTrue(contents.contains("A:4,B:1,C:1,D:11"));
        assertTrue(contents.contains("A:2,B:1,C:1,D:11"));
        assertTrue(contents.contains("A:2,B:1,C:1,D:100"));

    }

    @Test
    public void testMultisetToLine() {
        outputFactory = new OutputFactory<String>();
        Multiset<String> ms = HashMultiset.create();
        ms.add("A");
        ms.add("C", 10);

        String result = outputFactory.multisetToLine(ms);
        assertTrue(result.contains("A:1"));
        assertTrue(result.contains("C:10"));
    }

    /**
     * Test outputFactory sortSet method, outputFactory class OutputFactory.
     */
    @Test
    public void testSortSet() {

        outputFactory = new OutputFactory<String>();
        Multiset<String> ms = HashMultiset.create();
        ms.add("A");
        ms.add("B");
        ms.add("C");
        ms.add("A");
        ms.add("B");
        ms.add("C", 10);


        Set<String> set = new HashSet<>(ms);
        Set<String> orig = new HashSet<>(ms);


        Set<String> sorted = outputFactory.sortSet(set);
        Assert.assertEquals ("List is not sorted", sorted, orig);


    }

}
