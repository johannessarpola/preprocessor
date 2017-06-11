/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import fi.johannes.VectorOutput.Vector.TokenVector;
import org.apache.commons.lang3.NotImplementedException;

import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static fi.johannes.Utilities.Shorthands.Log.info;
import static fi.johannes.Utilities.Shorthands.Str.fmt;

/**
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class OutputFactory<E extends Comparable<E>> {

    private Collection<E> universe;
    private Collection<Multiset<Integer>> intVectors; // used in compressed
    private Collection<Multiset<E>> entityVectors; // used without compressed

    private boolean compressed = false;
    private boolean chunkedOutput = false;
    private boolean sortedOutput = true;
    private final String defaultOutput;
    private final int defaultChunkSize = 25_000;

    private OutputBuffer<String> outputBuffer;


    public OutputFactory() {
        this.defaultOutput = ".out";
        init();
    }

    public OutputFactory(String defaultOutput) {
        this.defaultOutput = defaultOutput;
        init();
    }

    private void init() {
        outputBuffer = new OutputBuffer<>();
        confBuffer();
    }

    private void confBuffer() {
        outputBuffer
                .withBufferSize(defaultChunkSize)
                .withFileExtension("txt")
                .withFileStem("out")
                .withOutputFolder(defaultOutput)
                .withStemDelimeter("_");
    }

    /**
     * Creates and outputs intVectors to a set folder
     *
     * @param tokens
     */
    public void createVectors(Collection<Multiset<E>> tokens) {
        if (compressed) {
            universe = createUniverse(tokens);
            intVectors = new ArrayList<>();
            // todo intvectors in which there's glossary of words and then vector is the word index and frequency
            //Multiset<Integer> vector = createIntegerVector(ms, universe);
            //intVectors.add(vector);
        } else {
            if(sortedOutput) {
                // sort multiset
                entityVectors = tokens.stream().map(Multisets::copyHighestCountFirst).collect(Collectors.toList());
            }
            else {
                entityVectors = tokens;
            }
            try {
                writeVectors();
            } catch (NotDirectoryException e) {
                // todo do something smart info guess
                e.printStackTrace();
            }
        }


    }

    public void writeVectors() throws NotDirectoryException {
        Collection<TokenVector> chunks = new ArrayList<>();
        info(this.getClass(), "Started outputting vectors to " + outputBuffer.getOutputFolder());
        if (compressed) {
            info(this.getClass(), "Using compressed vector defaultOutput");
            writeIntegerVectors();
        } else {
            info(this.getClass(), "Using entity vector defaultOutput");
            writeEntityVectors(entityVectors);
        }
    }

    void writeEntityVectors(Collection<Multiset<E>> entityVectors, int chunkSize) {
        outputBuffer.withBufferSize(chunkSize);
        writeEntityVectors(entityVectors);
    }

    void writeEntityVectors(Collection<Multiset<E>> entityVectors) {
        entityVectors.stream().map(this::multisetToLine).forEach(s -> outputBuffer.append(s));
        outputBuffer.end();
        info(this.getClass(), fmt("Vector defaultOutput done to files in %s folder", defaultOutput));
    }

    void writeIntegerVectors() {
        // todo defaultOutput integer vectors to files
        throw new NotImplementedException("writeIntegerVectors not done");
    }

    /**
     * Creates the from master to Indexed intVectors multiset err.g. 1:3 (index:count)
     *
     * @param ms
     * @param sortedMaster
     * @return
     */
    public Multiset<Integer> createIntegerVector(Multiset<E> ms, List<E> sortedMaster) {
        Multiset<Integer> multiset = HashMultiset.create();
        for (Multiset.Entry<E> e : ms.entrySet()) {
            multiset.add(sortedMaster.indexOf(e.getElement()), e.getCount());
        }
        return multiset;
    }

    // Take in a line and defaultOutput a vector
    public List<E> createUniverse(Collection<Multiset<E>> tokens) {
        Set<E> setMaster = tokens.parallelStream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return new ArrayList<>(sortSet(setMaster));
    }

    public String multisetToLine(Multiset<?> multiset) {
        String collect = multiset.entrySet()
                .stream()
                .map(entry -> fmt("%s:%d", entry.getElement().toString(), entry.getCount()))
                .collect(Collectors.joining(","));
        return collect;
    }

    public Set<E> sortSet(Set<E> set) {
        return set.parallelStream().sorted().collect(Collectors.toSet());
    }

    public boolean isChunkedOutput() {
        return chunkedOutput;
    }

    public void setChunkedOutput(boolean chunkedOutput) {
        this.chunkedOutput = chunkedOutput;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public OutputFactory<E> withChunkedOutput(boolean chunkedOutput) {
        this.chunkedOutput = chunkedOutput;
        return this;
    }

    public OutputFactory<E> withCompression(boolean compressed) {
        this.compressed = compressed;
        return this;
    }

    public OutputFactory<E> withOutput(String output) {
        outputBuffer.withOutputFolder(output);
        return this;
    }

    public OutputFactory<E> withChunkSize(int chunkSize) {
        outputBuffer.setBufferSize(chunkSize);
        return this;
    }

    public OutputFactory<E> withFileStem(String stem) {
        outputBuffer.withFileStem(stem);
        return this;
    }

    public OutputFactory<E> withExtension(String extension) {
        outputBuffer.withFileExtension(extension);
        return this;
    }

    public OutputFactory<E> withStemDelimeter(String delimeter) {
        outputBuffer.withStemDelimeter(delimeter);
        return this;
    }

    public OutputFactory<E> withSubfolder(String subfolder) {
        outputBuffer.withSubfolder(subfolder);
        return this;
    }

    public OutputFactory<E> withSortedOutput(boolean sortedOutput) {
        this.sortedOutput = sortedOutput;
        return this;
    }
}
