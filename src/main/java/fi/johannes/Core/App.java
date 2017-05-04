package fi.johannes.Core;

import fi.johannes.Abstractions.Cluster;
import fi.johannes.Core.AppConf.SupportedProcessingMethods;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.Logging;
import fi.johannes.Utilities.Resources.ResourceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static fi.johannes.Core.AppConf.SupportedProcessingStrategy.*;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private AppConf conf;


    public static Resource getStopwordsResource() {
        return getResource("stopwords.txt");
    } // FIXME

    public static Resource getResource(String resourcePath) {
        return new ClassPathResource(resourcePath);
    }

    public static Collection<String> getResources(String resourcePath) {
        return ResourceList.getResources(Pattern.compile("src/main/resources/.*"));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        App app = ctx.getBean(App.class);
    }

    private List<ClusterConnection> createClusters() {
        return Arrays
                .stream(ClusterMapping.ClusterEnums
                        .values()).map(c -> new ClusterConnection(c, conf))
                .collect(Collectors.toList());
    }

    @Override
    public void run(String... args) throws Exception {

        Logging.logMessage_Info(this.getClass(), "Using configuration: "+conf.toString());

        List<ClusterConnection> cons = this.createClusters();
        List<String> documents = new ArrayList<>();
        documents.add("George Calvert, 1st Baron Baltimore (1579 – 15 April 1632) was an English politician and coloniser. He achieved domestic political success as a Member of Parliament and later Secretary of State under King James I. He lost much of his political power after his support for a failed marriage alliance between Prince Charles and the Spanish House of Habsburg royal family. Rather than continue in politics, he resigned all of his political offices in 1625 except for his position on the Privy Council and declared his Catholicism publicly. He was created Baron Baltimore in the Irish peerage upon his resignation. Baltimore Manor was located in County Longford, Ireland.");
        documents.add("Calvert took an interest in the British colonisation of the Americas, at first for commercial reasons and later to create a refuge for English Catholics. He became the proprietor of Avalon, the first sustained English settlement on the southeastern peninsula on the island of Newfoundland (off the eastern coast of modern Canada). Discouraged by its cold and sometimes inhospitable climate and the sufferings of the settlers, Sir George looked for a more suitable spot further south and sought a new royal charter to settle the region, which would become the state of Maryland. Calvert died five weeks before the new Charter was sealed, leaving the settlement of the Maryland colony to his son Cecil, (1605–1675). His second son Leonard Calvert, (1606–1647), was the first colonial governor of the Province of Maryland.");
        documents.add("Little is known of the ancestry of the Yorkshire branch of the Calverts. At George Calvert's knighting, it was claimed that his family originally came from Flanders (a Dutch-speaking area today across the English Channel in modern Belgium).[1] Calvert's father, (an earlier) Leonard was a country gentleman who had achieved some prominence as a tenant of Lord Wharton,[2] and was wealthy enough to marry a \"gentlewoman\" of a noble line, Alicia or Alice Crossland (or sometimes spelled: \"Crosland\"). He established his family on the estate of the later-built Kiplin Hall, near Catterick in Richmondshire, of Yorkshire.[3] George Calvert was born at Kiplin in late 1579.[2] His mother Alicia/Alice died on 28 November 1587, when he was fifteen years old. His father then married Grace Crossland (sometimes spelled: \"Crosland\"), Alicia's first cousin.");
        List<AppConf.SupportedProcessingStrategy> selectedStrategies
                = Arrays.asList(TFIDF_Keywords, TFIDF_WordNgram, TFIDF_KeywordsFirst, WikipediaTitles);

        List<String> result = new ArrayList<>();

        ArticleProcessor processor = new ArticleProcessor();
        processor.getStates()
                .removeNumbers()
                .removeUrls()
                .removeRemoveTags()
                .removeSingleCharacters()
                .removeStopwords()
                .removeStopwords()
                .useLemmatization()
                .useLowercase();

        List<String> processedDocuments = documents.stream().map(processor::processLineToString).collect(Collectors.toList());
        for (SupportedProcessingStrategy s : selectedStrategies) {
            for (ClusterConnection connection : cons) {
                if (connection.getStrategies().contains(s)) {
                    Cluster c = connection.getCluster();
                    c.buildStrategy(s, processedDocuments);
                    c.selectStrategy(s);
                    c.setBiasingSize(5);
                    if (c.isClusterReady()) {
                        List<String> collect = processedDocuments.parallelStream()
                                .map(line -> {
                                    try {
                                        // fixme keywordsfirst has empty
                                        return s.toString()+"| "+c.processLine(line, SupportedProcessingMethods.Replace);
                                    } catch (ServiceNotReadyException | ClusterNoteadyException | UnhandledServiceException e) {
                                        e.printStackTrace();
                                        return "";
                                    }
                                })
                                .filter(str -> !str.isEmpty())
                                .collect(Collectors.toList());
                        result.addAll(collect);
                    }
                }
            }
            result.forEach(r -> {
                System.out.println("---------");
                System.out.println(r);
            });
        }

    }
}
