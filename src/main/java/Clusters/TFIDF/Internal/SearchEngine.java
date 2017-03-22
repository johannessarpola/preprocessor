package Clusters.TFIDF.Internal;

/**
 * Created by Johannes on 22.3.2017.
 */


public interface SearchEngine {
    final int totalDocuments = 10_000_000; // FIXME This should be the total documents indexed in the engine

    SearchResult query(String keyword);

}
