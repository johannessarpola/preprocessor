package fi.johannes.Core;

import com.google.common.collect.Multiset;
import com.sun.istack.internal.NotNull;
import fi.johannes.Abstractions.Cluster;
import fi.johannes.Core.AppConf.SupportedProcessingMethods;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.Logging;
import fi.johannes.Utilities.Resources.ResourceList;
import fi.johannes.VectorOutput.OutputFactory;
import fi.johannes.VectorOutput.OutputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.file.NotDirectoryException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fi.johannes.Core.AppConf.SupportedProcessingStrategy.*;

@SpringBootApplication
public class App implements CommandLineRunner {

    private AppCli cli;

    @Autowired
    private AppConf conf;


    @NotNull
    public static Resource getStopwordsResource() {
        return getResource("stopwords.txt");
    }

    @NotNull
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

        Logging.logMessageInfo(App.class, "Using configuration: " + conf.toString());

        this.cli = new AppCli(args).parse();
        Stream<String> input = null;
        if (this.cli.getState().getInputFolder().isPresent()) {
            String inputPath;
            // there's input folder
            if (this.cli.getState().getInputFolder().isPresent()) {
                input = AppIO.readStreamsForFiles(this.cli.getState().getInputFolder().get());
            } else {
                input = AppIO.readStreamForFile(this.cli.getState().getInputFile().get());
            }
        }

        List<ClusterConnection> cons = this.createClusters();
        // todo move to some other method this if/else
        List<String> documents = new ArrayList<>();
        if (input != null) {
            if (cli.getState().getLimitInputRows().isPresent()) {
                documents = input.parallel().limit(cli.getState().getLimitInputRows().get()).collect(Collectors.toList());
            } else {
                documents = input.parallel().collect(Collectors.toList());
            }
        } else {
            throw new RuntimeException("Input stream was null");
        }

        // todo from cli as well
        List<AppConf.SupportedProcessingStrategy> selectedStrategies
                = Arrays.asList(TFIDF_Keywords, TFIDF_WordNgram, TFIDF_KeywordsFirst, WikipediaTitles);

        List<String> result = new ArrayList<>();

        ArticleProcessor processor = defaultProcessorConf(); // todo from cli

        Map<SupportedProcessingStrategy, Collection<Multiset<String>>> multisets = new HashMap<>();
        List<String> processedDocuments = documents.stream().map(processor::processLineToString).collect(Collectors.toList());

        for (SupportedProcessingStrategy s : selectedStrategies) {
            for (ClusterConnection connection : cons) {
                if (connection.getStrategies().contains(s)) {

                    // note that keywords first needs some kind of normalization since it has way higher counts
                    Cluster c = connection.getCluster();
                    c.buildStrategy(s, processedDocuments);
                    c.selectStrategy(s);
                    c.setBiasingSize(5);

                    if (c.isClusterReady()) {
                        List<String> collect = processedDocuments.parallelStream()
                                .map(line -> {
                                    try {
                                        String processed = c.processLine(line, SupportedProcessingMethods.Append);
                                        if (!multisets.containsKey(s)) {
                                            multisets.put(s, new ArrayList<>());
                                        }
                                        multisets.get(s).add(OutputUtils.toMultiset(processed));
                                        return processed;
                                    } catch (ServiceNotReadyException | ClusterNoteadyException | UnhandledServiceException e) {
                                        e.printStackTrace();
                                        return "";
                                    }
                                })
                                .filter(str -> !str.isEmpty())
                                .collect(Collectors.toList());
                        result.addAll(collect);
                        c.clear();
                    }
                }
            }
        }

        // todo for some reasons output are duplicated in outputFactory
        OutputFactory<String> outputFactory = defaultOutputFactoryConf();
        multisets.forEach((supportedProcessingStrategy, strings) -> {
            outputFactory.withSubfolder(supportedProcessingStrategy.name());
            outputFactory.createVectors(strings);
        });
    }

    private ArticleProcessor defaultProcessorConf() {
        ArticleProcessor processor = new ArticleProcessor();
        processor.getStates()
                .removeNumbers()
                .removeUrls()
                .removeRemoveTags()
                .removeSingleCharacters()
                .replacePunctuationWithSpace()
                .removeStopwords()
                .useLemmatization()
                .useLowercase();
        return processor;
    }

    private OutputFactory<String> defaultOutputFactoryConf() {
        String output = this.cli.getState().getOutputFolder().isPresent() ? this.cli.getState().getOutputFolder().get() : ".out";
        return new OutputFactory<String>()
                .withChunkSize(1000)
                .withChunkedOutput(true)
                .withCompression(false)
                .withOutput(output);

    }
}
