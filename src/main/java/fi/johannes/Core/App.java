package fi.johannes.Core;

import fi.johannes.Utilities.Resources.ResourceList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@EnableAutoConfiguration
@Component
@ComponentScan
public class App {

    public final static String RESOURCE_SDIR = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    public final static String STOPWORDS_PATH = RESOURCE_SDIR + "stopwords.txt";
    private final static String STOWORD_FILLE = "stopwords.txt";
    private final static String LOG4J_PROPERTIES = RESOURCE_SDIR + "log4j.properties";
    private final static String KEY_SDIR = RESOURCE_SDIR + "keys\\keys.csv";
    public final static String WORKING_DIR = System.getProperty("user.dir");
    public final static String CHUNKS = WORKING_DIR + "/chunks/";
    private final static String OUTPUT_DIR = WORKING_DIR + "\\output data\\";
    private final static String WATSON_CREDENTIALS = System.getProperty("user.dir") + "/.store/Credentials.json";
    public final static int MURMURSEED = 23417789;


    public static Resource getStopwordsResource(){
        return getPathToResource(STOWORD_FILLE);
    }

    public static Resource getPathToResource(String resourcePath){
        Resource resource = new ClassPathResource(resourcePath);
        return resource;
    }
    public static Collection<String> getResources(String resourcePath){
        return ResourceList.getResources(Pattern.compile("src/main/resources/.*"));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        App mainObj = ctx.getBean(App.class);
        mainObj.init();
    }

    void init() {
        System.out.println("Hello W");
    }



    void tryToCreateClusters() {
        List<ClusterConnection> connections = Arrays.stream(ClusterMapping.ClusterEnums.values()).map(ClusterConnection::new).collect(Collectors.toList());
        // TODO Read corpus from a directory
        // TODO Perform the tf-idf indexing
    }

    /**
     * Application specific enums
     */
    public enum SupportedTableStrategy {
        xlsx
    }

    public enum SupportedCorpuses {
        WikipediaCorpus
    }

    public enum SupportedProcessingStrategy {
        ConceptInsights, Alchemy, TFIDF_Keywords, TFIDF_WordNgram, TFIDF_Combined, TFIDF_KeywordsFirst, SupervisedBiasingWithTable
    }

    public enum SupportedProcessingParadigms {
        Append, Replace
    }
}
