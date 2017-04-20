package fi.johannes.Core;

import fi.johannes.Utilities.Resources.ResourceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private AppConf conf;


    public static Resource getStopwordsResource(){
        return getResource("stopwords.txt");
    } // FIXME

    public static Resource getResource(String resourcePath){
        return new ClassPathResource(resourcePath);
    }
    public static Collection<String> getResources(String resourcePath){
        return ResourceList.getResources(Pattern.compile("src/main/resources/.*"));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        App app = ctx.getBean(App.class);
    }

    private void createClusters() {
        List<ClusterConnection> connections = Arrays
                .stream(ClusterMapping.ClusterEnums
                        .values()).map(ClusterConnection::new)
                .collect(Collectors.toList());
    }

    @Override
    public void run(String... args) throws Exception {
        this.createClusters();
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
        ConceptInsights, Alchemy, TFIDF_Keywords, TFIDF_WordNgram, TFIDF_Combined, TFIDF_KeywordsFirst, SupervisedBiasingWithTable, WikipediaTitles
    }

    public enum SupportedProcessingMethods {
        Append, Replace
    }
}
