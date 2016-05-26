/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VectorOutput.Vector;

import VectorOutput.Vector.TokenVector;
import Utilities.Structures.FinalizedPair;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TokenVectorTest {

    static Multiset<Integer> msi;
    static TokenVector tvector;
    static AtomicLong al = new AtomicLong();

    public TokenVectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        msi = HashMultiset.create();
        msi.add(10);
        msi.add(2, 10);
        msi.add(1, 2);
        msi.add(3, 5);
        msi.add(Integer.MAX_VALUE);
        msi.add(Integer.MIN_VALUE, Integer.MAX_VALUE);
        tvector = new TokenVector(msi, al);
    }

    /**
     * Test of getters methods, of class TokenVector.
     */
    @Test
    public void testGetters() {
        assertThat(tvector.getIndexes(), is(not(nullValue())));
        assertThat(tvector.getIndexes().length, is(msi.entrySet().size()));
        assertThat(tvector.getCounts(), is(not(nullValue())));
        assertThat(tvector.getCounts().length, is(msi.entrySet().size()));
        assertThat(tvector.getCounts().length, is(tvector.getIndexes().length));
    }

    /**
     * Test of toString method, of class TokenVector.
     */
    @Test
    public void testToString() {
        char s1 = tvector.fieldSeparator;
        char s2 = tvector.vectorItemSepartor;
        String expected = al.get()-1 + s1 + "10:1" + s2 + "2:10" + s2 + "1:2" + s2 + "3:5" + s2 + "" + Integer.MAX_VALUE + ":1" + s2 + "" + Integer.MIN_VALUE + ":" + Integer.MAX_VALUE;
        String result = tvector.toString();
        // mutiset is not ordered, can only measure length of strings for ==
        Assert.assertEquals(result.length(), expected.length());
    }

    /**
     * Test of getSerial method, of class TokenVector.
     */
    @Test
    public void testGetSerial() {
        assertThat(tvector.getSerial(), is(not(nullValue())));
        assertThat(tvector.getSerial(), is(al.get()-1));
    }

    /**
     * Test of iterator method, of class TokenVector.
     */
    @Test
    public void testIterator() {
        Iterator iter = tvector.iterator();
        int number = 0;
        while(iter.hasNext()){
            number++;
            Object next = iter.next();
            assertThat(next, is(not(nullValue())));
            Assert.assertTrue(next instanceof FinalizedPair );
        }
        assertThat(number, is(msi.entrySet().size()));
    }
    
}
