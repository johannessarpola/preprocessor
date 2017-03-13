/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class GuavaTest {

    public GuavaTest() {
    }

    @Test
    public void MultisetTest() {
        Multiset<String> ms = HashMultiset.create();
        ms.add("String");
        ms.add("String");
        ms.add("string");
        ms.add("STRING");
        ms.add("STring");
        ms.add("Ten", 10);
        List<String> s = new ArrayList();
        s.add("string");
        s.add("String");
        s.add("STRING");
        s.add("STring");
        s.add("Ten");
        Stream<String> stre = ms.elementSet().stream();
        stre.forEach((Object t) -> {
            System.out.println(t.toString());
            assertThat(s.contains(t.toString()), is(true));
        });
        
        Assert.assertEquals(5, ms.entrySet().size());

    }
}
