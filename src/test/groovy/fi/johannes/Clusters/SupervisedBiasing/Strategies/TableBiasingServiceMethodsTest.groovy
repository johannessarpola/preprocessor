/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing.Strategies

import fi.johannes.Clusters.SupervisedBiasing.Internal.StringTableHierarchy
import fi.johannes.Utilities.Structures.Table
import org.junit.Test

import static junit.framework.TestCase.assertTrue
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*;
/**
 * @author Johannes Sarpola <johannes.sarpola at gmail.com>
 */
class TableBiasingServiceMethodsTest {

    /**
     * Test of getWeightsForLine method, of class TableBiasingServiceMethods.
     */
    @Test
    public void testGetWeightsForLine() {
        Table<String> table = new Table<>(
                ["h1", "h2"],
                [
                        ["row1-c1", "row1-c2"],
                        ["row2-c1", "row2-c2"],
                        ["row3-c1", "row3-c2"]
                ])
        assertTrue(table.header.size() > 1)
        assertTrue(table.rows.size() > 1)
        StringTableHierarchy hierarchy = new StringTableHierarchy(table)
        def lines = ["row1-c1", "row2-c2", "row3-c1"]
        List<Double> weights = TableBiasingServiceMethods.getWeightsForLine(lines, [hierarchy])
        assertThat(weights.findAll { it == 1.0 }.size(), is(2))
        assertThat(weights.findAll { it == 0.5 }.size(), is(1))
    }

}
