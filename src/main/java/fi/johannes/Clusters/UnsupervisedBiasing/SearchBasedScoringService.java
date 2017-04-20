package fi.johannes.Clusters.UnsupervisedBiasing;

import fi.johannes.Clusters.UnsupervisedBiasing.Internal.Converter;
import fi.johannes.Clusters.UnsupervisedBiasing.Internal.Result;
import fi.johannes.Clusters.UnsupervisedBiasing.Internal.SearchEngine;
import fi.johannes.Utilities.Structures.LinkedWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Performs the search with a keyword and returns on how many "hits" or "pages" it returns
 * Created by Johannes on 22.3.2017.
 */
@Component
public class SearchBasedScoringService {

    private final SearchEngine searchEngine;

    @Autowired
    public SearchBasedScoringService(SearchEngine searchService) {
        this.searchEngine = searchService;
    }

    /**
     * Performs the query with LinkedWord
     * @param lw
     * @return
     */
    public Result query(LinkedWord lw){
        String queryString = Converter.linkedWordToString(lw);
        Result result = searchEngine.query(queryString);
        return result;
    }

}
