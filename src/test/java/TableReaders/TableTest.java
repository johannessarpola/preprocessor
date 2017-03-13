/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableReaders;

import Global.Options;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableTest {

    static String filep = System.getProperty("user.dir") + "/src/test/resources/TestXLSX.xlsx";
    static TableContainer t;

    public TableTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        t = new TableContainer(Options.SupportedTableStrategy.xlsx, filep);

    }

    @AfterClass
    public static void tearDownClass() {
    }

    
    /**
     * Test of getHeader method, of class TableContainer.
     */
    @Test
    public void testGetHeader() {
        System.out.println("Headers");
        List<String> expect = new ArrayList<>();
        expect.add("H1");
        expect.add("H2");
        expect.add("H3");
        List<String> res = t.getHeader();
        Iterator<String> i1 = expect.iterator();
        Iterator<String> i2 = res.iterator();
        while (i1.hasNext()) {
            assertThat(i1.next(), is(i2.next()));
        }

    }

    /**
     * Test of getRows method, of class TableContainer.
     */
    @Test
    public void testGetRows() {
        System.out.println("Rows");
        int expectLength = 4;
        List<String> firstRow = new ArrayList<>();
        firstRow.add("1.0");
        firstRow.add("2.0");
        firstRow.add("3.0");
        List<String> secondRow = new ArrayList<>();
        secondRow.add("3.0");
        secondRow.add("ääää");
        secondRow.add("1.0");
        List<List<String>> rows = t.getRows();
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
        for (List<String> ls : rows) {
            ls.stream().forEach((s) -> {
                assertThat(s, is(not(nullValue())));
            });
        }
    }

}
