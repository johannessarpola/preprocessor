/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.ChunkedInput;

import Utilities.File.CFileOperations;
import Utilities.Logging.GeneralLogging;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class FileChunks {

    private List<File> chunks;
    private int numberOfChunks;
    
    public FileChunks(String path) {
        chunks = new ArrayList<>();
        try {
            init(path);
        } catch (Exception ex) {
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
    }
    private void init(String path) throws Exception{
        File[] fs = new File(path).listFiles();
        if(fs.length==0) {
            throw new Exception("Unable to locate chunks");
        }
        for(File f : fs) {
            chunks.add(f);
        }
        this.numberOfChunks = chunks.size();
    }   
    public List<char[]> getChunkContent(int index) throws IOException{
        List<char[]> c= new ArrayList<>();
        c = CFileOperations.getFileContentAsChars(chunks.get(index));
        return c;
    }
    public File getChunk(int index){
        return chunks.get(index);
    }

    public int getNumberOfChunks() {
        return numberOfChunks;
    }
}