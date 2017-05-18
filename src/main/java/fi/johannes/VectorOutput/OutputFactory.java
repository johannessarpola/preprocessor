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
    List<E> universe;
    List<Multiset<Integer>> intVectors; // used in compressed
    List<Multiset<E>> entityVectors; // used without compressed
    String output;
     int vectorChunks = 25_000;
     boolean compressed = false;
     boolean chunkedOutput = false;


    OutputFactory() {
    }

    public OutputFactory(String output) {
        this.output = output;
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
            writeEntityVectors(entityVectors, vectorChunks);
        }
    }


     void writeEntityVectors(List<Multiset<E>> entityVectors, int chunkSize) {
        final List<List<Multiset<E>>> partition = Lists.partition(entityVectors, chunkSize);
        AtomicInteger integer = new AtomicInteger(0);
        partition.parallelStream()
                .map(chunk -> {
                    Stream<String> s = chunk.stream().map(this::multisetToLine);
                    return new ImmutablePair<>(integer.incrementAndGet(), s);
                })
                .forEach(pair -> {
                    try {
                        Path outputPath = Paths.get(output, fmt("%s_%d", "out", pair.getLeft()));
                        List<String> content = pair.getRight().collect(Collectors.toList());
                        Files.write(outputPath, content, Charset.defaultCharset());
                    } catch (IOException e) {
                        warn(this.getClass(), "Could not write vector to output");
                        e.printStackTrace();
                    }
                });
        info(this.getClass(), fmt("Vector output done to files in %s with %d chunks", output, integer.get()));
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

    public int getVectorChunks() {
        return vectorChunks;
    }

    public void setVectorChunks(int vectorChunks) {
        this.vectorChunks = vectorChunks;
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
