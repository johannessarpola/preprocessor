package Clusters.TFIDF.Internal;

import lombok.Data;

/**
 * Simple class to hold idf, tf and result
 * Created by Johannes on 22.3.2017.
 */
@Data
public class TFIDFScore {

    double idfSum;
    double tfSum;
    double result;

    public static TFIDFScore calculate(int frequency, int totalCount, int existingInDocuments, int totalDocuments){
        TFIDFScore tfidfScore = new TFIDFScore();
        tfidfScore.tfSum = frequency/totalCount;
        tfidfScore.idfSum = Math.log(totalDocuments/existingInDocuments);
        tfidfScore.result = tfidfScore.tfSum * tfidfScore.idfSum;
        return tfidfScore;
    }
}
