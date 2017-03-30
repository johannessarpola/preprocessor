/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput.Internal;

import fi.johannes.VectorOutput.Vector.TokenVector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class VectorIO {

    BufferedWriter bw;
    File folder;
    private String extension = ".csv";

    /**
     * Constructor where a folder is required
     *
     * @param folder
     * @throws NotDirectoryException
     */
    public VectorIO(File folder) throws NotDirectoryException {
        if (folder.isDirectory()) {
            this.folder = folder;
        } else {
            throw new NotDirectoryException(folder.getAbsolutePath());
        }
    }

    /**
     * Writes TokenVectors to set range of files on a disk (already divided from
     * all vectors)
     *
     * @param vectors
     */
    public void writeVectors(List<TokenVector> vectors) throws IOException {
        String range = getRange(vectors.get(0), vectors.get(vectors.size() - 1));
        String filename = folder + "/" + range + extension;
        File f = new File(filename);
        if(!f.exists()) {
            f.createNewFile();
        }
        try (FileWriter fw = new FileWriter(f, true)) {
            bw = new BufferedWriter(fw);
            for (TokenVector vector : vectors) {
                write(vector);
            }
            bw.close();
        }
    }

    private String getRange(TokenVector first, TokenVector last) {
        return "" + first.getSerial() + "..." + last.getSerial();
    }

    private void write(TokenVector vector) throws IOException {
        String towrite = vector.toString();
        bw.write(towrite);
        bw.newLine();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        // TODO You could control this with enums
        this.extension = extension;
    }

}
