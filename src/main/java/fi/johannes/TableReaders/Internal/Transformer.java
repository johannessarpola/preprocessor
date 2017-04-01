/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.TableReaders.Internal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class Transformer {

    public static List<String> rowToList(Row r) {
        int max = r.getLastCellNum();
        List<String> l = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            Cell c = r.getCell(i);
            String s = getCellAsString(c);
            l.add(s);
        }
        return l;
    }

    public static String getCellAsString(Cell c) {
        int ct = c.getCellTypeEnum().getCode();
        if (ct == 2) {
            ct = c.getCachedFormulaResultType();
        }
        switch (ct) {
            case 3:
                return "";
            case 0:
                return "" + c.getNumericCellValue();
            case 1:
                return c.getStringCellValue();
            case 4:
                if (c.getBooleanCellValue()) {
                    return "1";
                } else {
                    return "0";
                }
            case 5: return "Error in spreadsheet";
        }
        return "";
    }
}
