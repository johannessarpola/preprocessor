package fi.johannes.VectorOutput;

import fi.johannes.Utilities.Shorthands.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Johannes on 22.5.2017.
 */
public class OutputBuffer<T> {

    private Deque<T> buffer;
    private AtomicInteger index;
    private Integer bufferSize;

    private String fileStem = "out";
    private String stemDelimeter = "_";
    private String fileExtension = "txt";
    private String outputFolder = "output";

    public OutputBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        init();
    }

    public OutputBuffer() {
        this.bufferSize = 25_000;
        init();
    }

    private void init() {
        this.buffer = new ArrayDeque<>(bufferSize);
        this.index = new AtomicInteger(0);
    }

    public void append(T element){
        if(this.buffer.size() == bufferSize) {
            try {
                output();
            } catch (IOException e) {
                e.printStackTrace(); // todo logging
            }
            this.buffer.clear();
        }
        this.buffer.push(element);
    }

    public void end() {
        try {
            output();
        } catch (IOException e) {
            e.printStackTrace(); // todo logging
        }
    }

    private void output() throws IOException {
        Path path = buildPath();
        Files.write(path, (Iterable<String>) buffer.stream().map(Object::toString)::iterator, Charset.defaultCharset());
        Log.info(this.getClass(), "Written output to file: "+path.toString());
    }

    private Path buildPath() {
        return Paths.get(outputFolder, (fileStem + stemDelimeter + this.index.incrementAndGet() + "." + fileExtension));
    }

    public OutputBuffer<T> withFileStem(String fileStem) {
        this.fileStem = fileStem;
        return this;
    }
    public OutputBuffer<T> withFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }

    public OutputBuffer<T> withOutputFolder(String folder) {
        this.outputFolder = folder;
        return this;
    }

    public OutputBuffer<T> withStemDelimeter(String delimeter)  {
        this.stemDelimeter = delimeter;
        return this;
    }

    public OutputBuffer<T> withBufferSize(int size) {
        this.bufferSize = size;
        return this;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }
}
