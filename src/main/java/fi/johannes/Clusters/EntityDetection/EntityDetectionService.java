package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus;
import fi.johannes.Core.AppConf;
import fi.johannes.Utilities.GeneralUtilities;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.String.Ngram;

import java.util.*;
import java.util.stream.Collectors;

/**
 * johanness on 24/04/2017.
 */
public class EntityDetectionService extends GenericService {
    private static final int ngramRange = 2;
    private Set<EntityCorpus> corpuses;

    public EntityDetectionService(EntityCorpus... corpus ) {
        super(AppConf.SupportedProcessingStrategy.WikipediaTitles);
        this.corpuses = new HashSet<>();
        Collections.addAll(corpuses, corpus);
    }

    private List<String> createNgrams(String line) {
        List<List<String>> ngrams = Ngram.createNgrams(line, ngramRange);
        return ngrams.parallelStream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public void build(List<String> documents) {
        // not sure is this needed
        this.isServiceReady = true;
    }

    private List<String> addItemsFromLine(String line ){
        List<String> list = createNgrams(line);
        list.addAll(GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line));
        return list;
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        // todo there's no really hierarchy in corpuses so biasingSize is quite hard to handle, probably need some kind of ranking
        List<String> list = addItemsFromLine(line);
        String contained = list.parallelStream()
                .filter(s -> corpuses.stream().anyMatch(c -> c.doesContain(s)))
                .collect(Collectors.joining(" "));
        return line + " " + contained;
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        // todo there's no really hierarchy in corpuses so biasingSize is quite hard to handle, probably need some kind of ranking
        List<String> list = addItemsFromLine(line);
        return list.parallelStream()
                .filter(s -> corpuses.stream().anyMatch(c -> c.doesContain(s)))
                .collect(Collectors.joining(" "));
    }

    @Override
    public void clear() {
        // todo
    }
}
