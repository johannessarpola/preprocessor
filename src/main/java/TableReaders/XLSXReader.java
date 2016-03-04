/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableReaders;

import Abstractions.TableReader;
import Global.Options;
import Global.Options.SupportedTableStrategy;
import TableReaders.Internal.Transformer;
import Utilities.Logging.GeneralLogging;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XLSXReader extends TableReader {
    // hierarchy Sheet name > left to right
    // TODO add correct logger

    Workbook workbook;
    int activeSheet = 0;
    SupportedTableStrategy strategy = Options.SupportedTableStrategy.xlsx;

    /**
     * XLSX Reader
     *
     * @param filep
     * @throws FileNotFoundException
     */
    public XLSXReader(String filep) throws FileNotFoundException {
        super(filep);
        try {
            init();
        } catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    @Override
    public List<List<String>> retrieveRows() {
        Sheet s = workbook.getSheetAt(activeSheet);
        int maxrow = s.getLastRowNum();
        List<List<String>> wb = new ArrayList<>();
        for (int i = 1; i <= maxrow; i++) {
            Row r = s.getRow(i);
            wb.add(Transformer.rowToList(r));
        }
        return wb;
    }

    @Override
    public List<String> retrieveHeaders() {
        Sheet s = workbook.getSheetAt(activeSheet);
        Row r = s.getRow(0);
        Iterator i = r.iterator();
        List<String> headers = new ArrayList<>();
        while (i.hasNext()) {
            String str = i.next().toString();
            headers.add(str);
        }
        return headers;
    }

    @Override
    public void reset() {
        try {
            try {
                workbook.close();
            } catch (IOException ex) {
                GeneralLogging.logStackTrace_Error(getClass(), ex);
            }
            init();
        } catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    @Override
    public Options.SupportedTableStrategy getName() {
        return strategy;
    }

    private void init() throws IOException, EncryptedDocumentException, InvalidFormatException {
        workbook = WorkbookFactory.create(file);
        activeSheet = 0;
    }

    public Sheet selectSheet(int index) {
        this.activeSheet = index;
        return workbook.getSheetAt(index);
    }

    public Sheet getNextSheet() {
        activeSheet++;
        return workbook.getSheetAt(activeSheet);
    }

}
