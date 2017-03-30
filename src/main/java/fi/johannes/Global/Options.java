
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Global;

/**
 * Holds the main connstants like paths and enums
 *
 * @author Johannes töissä
 */
public class Options {

    public final static String RESOURCESDIR = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    public final static String KEYSDIR = RESOURCESDIR + "keys\\keys.csv";
    public final static String LOG4JPROPERTIES = RESOURCESDIR + "log4j.properties";
    public static final String WORKINGDIR = System.getProperty("user.dir");
    public final static String OUTPUTDIR = WORKINGDIR + "\\output data\\";
    public static final String WATSONCREDENTIALS = System.getProperty("user.dir") + "/.store/Credentials.json";
    public static final int MURMURSEED = 23417789;
    public static final String CHUNKS = WORKINGDIR + "/chunks/";
    public static final String STOPWORDSPATH = RESOURCESDIR + "stopwords.txt";

    /**
     * Application specific enums
     */
    public static enum SupportedTableStrategy {
        xlsx
    }

    public static enum SupportedClusters {
        Watson, TFIDF, TableBiasing
    }

    public static enum SupportedCorpuses {
        Wikipedia
    }

    public static enum SupportedProcessingStrategy {
        ConceptInsights, Alchemy, TFIDF_Keywords, TFIDF_WordNgram, TFIDF_Combined, TFIDF_KeywordsFirst, SupervisedBiasingWithTable
    }

    public static enum SupportedProcessingParadigms {
        Append, Replace
    }
}
