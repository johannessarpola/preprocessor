/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput;

import com.google.common.collect.Lists;
import fi.johannes.Utilities.File.CFolderOperations;
import fi.johannes.Utilities.Logging.Logging;
import fi.johannes.Utilities.Shorthands.Str;
import fi.johannes.VectorOutput.Vector.TokenVector;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fi.johannes.Utilities.Shorthands.Log.info;
import static fi.johannes.Utilities.Shorthands.Log.warn;
import static fi.johannes.Utilities.Shorthands.Str.fmt;

/**
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class OutputFactory<E extends Comparable<E>> {
    private List<E> universe;
    private List<Multiset<Integer>> intVectors; // used in compressed
    private List<Multiset<E>> entityVectors; // used without compressed
    private String output;
    private boolean compressed = false;
    private boolean chunkedOutput = false;

    private OutputBuffer<String> outputBuffer;


    OutputFactory() {
        init();
    }

    public OutputFactory(String output) {
        this.output = output;
        init();
    }

    private void init() {
        outputBuffer = new OutputBuffer<>();
        confBuffer();
    }

    private void confBuffer() {
        outputBuffer
                .withBufferSize(25_000)
                .withFileExtension("txt")
                .withFileStem("out")
                .withOutputFolder(output)
                .withStemDelimeter("_");
    }

    /**
     * Creates and outputs intVectors to a set folder
     *
     * @param tokens
     */
    public void createVectors(List<Multiset<E>> tokens) {
        universe = createUniverse(tokens);
        if (compressed) {
            intVectors = new ArrayList<>();
            // todo intvectors in which there's glossary of words and then vector is the word index and frequency
            //Multiset<Integer> vector = createIntegerVector(ms, universe);
            //intVectors.add(vector);
        } else {
            entityVectors = tokens;
            try {
                writeVectors();
            } catch (NotDirectoryException e) {
                // todo do something smart info guess
                e.printStackTrace();
            }
        }

    }

    public void writeVectors() throws NotDirectoryException {
        List<TokenVector> chunks = new ArrayList<>();
        File folder = CFolderOperations.createFolder(output);
        info(this.getClass(), "Started outputting vectors to " + output);
        if (compressed) {
            info(this.getClass(), "Using compressed vector output");
            writeIntegerVectors();
        } else {
            info(this.getClass(), "Using entity vector output");
            writeEntityVectors(entityVectors);
        }
    }

    void writeEntityVectors(List<Multiset<E>> entityVectors, int chunkSize) {
        outputBuffer.withBufferSize(chunkSize);
        writeEntityVectors(entityVectors);
    }

    void writeEntityVectors(List<Multiset<E>> entityVectors) {
        entityVectors.stream().map( e -> multisetToLine(e)).forEach( s -> outputBuffer.append(s));
        outputBuffer.end();
        info(this.getClass(), fmt("Vector output done to files in %s folder", output));
    }

    void writeIntegerVectors() {
        // todo output integer vectors to files
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

    // Take in a line and output a vector
    public List<E> createUniverse(List<Multiset<E>> tokens) {
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

}
