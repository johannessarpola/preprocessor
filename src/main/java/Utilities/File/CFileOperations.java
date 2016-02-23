/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.File;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 * TODO Test at some point
 * @author Johannes töissä
 */
public class CFileOperations {

    public static String fileContentToString(String filepath) throws IOException {
        Path p = Paths.get(filepath);
        byte[] contents = Files.readAllBytes(p);
        return new String(contents, StandardCharsets.UTF_8);
    }

    public static List<byte[]> getFileContentAsBytes(String f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        List<byte[]> res = getFileContentAsBytes(br);
        fr.close();
        return res;
    }

    public static List<byte[]> getFileContentAsBytes(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        List<byte[]> res = getFileContentAsBytes(br);
        fr.close();
        return res;
    }

    public static List<char[]> getFileContentAsChars(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        List<char[]> res = getFileContentAsChars(br);
        fr.close();
        return res;
    }

    public static List<char[]> getFileContentAsChars(String f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        List<char[]> res = getFileContentAsChars(br);
        fr.close();
        return res;
    }

    private static ArrayList<char[]> getFileContentAsChars(BufferedReader br) throws IOException {
        ArrayList<char[]> cList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            cList.add(line.toCharArray());
        }
        return cList;
    }

    public static List<String> getFileContentAsStrings(Path f) throws IOException {
        FileReader fr = new FileReader(f.toString());
        BufferedReader br = new BufferedReader(fr);
        List<String> res = getFileContentAsStrings(br);
        fr.close();
        return res;
    }

    public static List<String> getFileContentAsStrings(File f) throws IOException {
        FileReader fr = new FileReader(f.toString());
        BufferedReader br = new BufferedReader(fr);
        List<String> res = getFileContentAsStrings(br);
        fr.close();
        return res;
    }

    public static List<String> getFileContentAsStrings(String f) throws IOException {
        FileReader fr = new FileReader(f.toString());
        BufferedReader br = new BufferedReader(fr);
        List<String> res = getFileContentAsStrings(br);
        fr.close();
        return res;
    }

    private static ArrayList<String> getFileContentAsStrings(BufferedReader br) throws IOException {
        ArrayList<String> sList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            sList.add(line);
        }
        br.close();
        return sList;
    }

    private static ArrayList<byte[]> getFileContentAsBytes(BufferedReader br) throws IOException {
        ArrayList<byte[]> bList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            bList.add(line.getBytes(StandardCharsets.UTF_8));
        }

        br.close();
        return bList;
    }

    public static int countLines(File f) throws FileNotFoundException, IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(f));
        return countLines(is);
    }

    public static int countLines(String filepath) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filepath));
        return countLines(is);
    }

    public static int countLines(InputStream is) throws IOException {
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }


/*
    public static File[] getFilesInFolder(String path) {
        //TODO Throw exception if it's not folder
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public static List<String> getFilenamesInFolder(String path) {
        File[] files = getFilesInFolder(path);
        List<String> names = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                names.add(file.getName());
            }
        }
        return names;
    }
*/
    public static void createChunksByRowsAndBytes(String inputFile, String outputFolder, int bufferSize, int rows) throws FileNotFoundException, IOException {
        File f = new File(inputFile);
        String filename = f.getName();
        BufferedReader bw = new BufferedReader(new FileReader(f));
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        int j = 0;
        for (int i = 0; i <= rows; i++) {
            String lineStr = bw.readLine();
            // TODO Process each line
            if (lineStr != null) {
                byte[] line = lineStr.getBytes(StandardCharsets.UTF_8);
                if (i == rows) {
                    String outputfile = outputFolder + j + "-" + filename;
                    writeToFile(buffer, outputfile);
                    buffer.clear();
                    j++;
                    i = 0;
                }
                buffer.put(line);
                buffer.put(System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8));
            } else {
                break;
            }
        }
    }

    public static void createChunksByRowsChars(String inputFile, String outputFolder, int bufferSize, int rows) throws FileNotFoundException, IOException {
        File f = new File(inputFile);
        String filename = f.getName();
        BufferedReader bw = new BufferedReader(new FileReader(f));
        CharBuffer buffer = CharBuffer.allocate(bufferSize);
        int j = 0;
        for (int i = 0; i <= rows; i++) {
            String lineStr = bw.readLine();
            // TODO Process each line
            if (lineStr != null) {
                char[] line = lineStr.toCharArray();
                if (i == rows) {
                    String outputfile = outputFolder + j + "-" + filename;
                    writeChunk(buffer, outputfile);
                    buffer.clear();
                    j++;
                    i = 0;
                }
                buffer.put(line);
                buffer.put(System.getProperty("line.separator"));
            } else {
                break;
            }
        }
    }

    public static void writeToFile(char[] c, String outputFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new PrintWriter(outputFile))) {
            bw.write(String.valueOf(c));
        }
    }

    public static void writeToFile(byte[] bb, String outputFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new PrintWriter(outputFile, "UTF-8"))) {
            String chunk = new String(bb, StandardCharsets.UTF_8);
            bw.write(chunk);
        }
    }

    public static void writeToFile(ByteBuffer bb, String outputFile) throws IOException {
        bb.flip();
        try (BufferedWriter bw = new BufferedWriter(new PrintWriter(outputFile, "UTF-8"))) {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(bb);
            bw.write(charBuffer.toString());
            charBuffer.clear();
        }
    }

    public static void writeChunk(CharBuffer cb, String outputFile) throws IOException {
        cb.flip();
        try (BufferedWriter bw = new BufferedWriter(new PrintWriter(outputFile))) {
            bw.write(cb.toString());
            cb.clear();
        }
    }

    /**
     * 1.8 readAllLines
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> readLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    /**
     * 1.8 readAllLines
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> readLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }
    /**
     * Gets the file extension for a given file or throws exception of file not found
     * @param filepath
     * @return
     * @throws FileNotFoundException 
     */
    public static String getFileExtension(String filepath) throws FileNotFoundException{
        File f = new File(filepath);
        if(f.exists()) {
            return FilenameUtils.getExtension(filepath);
        }
        else {
            throw new FileNotFoundException();
        }
    }
}
