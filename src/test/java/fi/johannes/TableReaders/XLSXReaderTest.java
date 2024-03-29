/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.TableReaders;

import fi.johannes.Core.App;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class XLSXReaderTest {

    static XLSXReader xlsx;
    static String filep;

    public XLSXReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        filep = App.getResource("test.xlsx").getFile().getAbsolutePath();
        xlsx = new XLSXReader(filep);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of retrieveRows method, of class XLSXReader.
     */
    @Test
    public void testRetrieveRows() {
        System.out.println("Rows");
        int expectLength = 3;
        List<String> firstRow = new ArrayList<>();
        firstRow.add("1.0");
        firstRow.add("2.0");
        firstRow.add("3.0");
        List<String> secondRow = new ArrayList<>();
        secondRow.add("3.0");
        secondRow.add("ääää");
        secondRow.add("1.0");
        List<List<String>> rows = xlsx.retrieveRows();
        assertThat(rows.size(), is(expectLength));
        int i = 0;
        for (String s : rows.get(0)) {
            assertThat(s, is(firstRow.get(i)));
            i++;
        }
        i = 0;
        for (String s : rows.get(1)) {
            assertThat(s, is(secondRow.get(i)));
            i++;
        }
        for(List<String> ls : rows){
            for(String s : ls){
                assertThat(s, is(not(nullValue())));
            }
        }
    }

    /**
     * Test of retrieveHeaders method, of class XLSXReader.
     */
    @Test
    public void testRetrieveHeaders() {
        System.out.println("Headers");
        List<String> expect = new ArrayList<>();
        expect.add("H1");
        expect.add("H2");
        expect.add("H3");
        List<String> res = xlsx.retrieveHeaders();
        Iterator<String> i1 = expect.iterator();
        Iterator<String> i2 = res.iterator();

        while (i1.hasNext()) {
            assertThat(i1.next(), is(i2.next()));
        }

    }

    /**
     * Test of reset method, of class XLSXReader.
     */
    @Test
    public void testReset() {
    }

    /**
     * Test of getName method, of class XLSXReader.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of selectSheet method, of class XLSXReader.
     */
    @Test
    public void testSelectSheet() {
    }

    /**
     * Test of getNextSheet method, of class XLSXReader.
     */
    @Test
    public void testGetNextSheet() {
    }

}
