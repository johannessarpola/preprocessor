package fi.johannes.Clusters.EntityDetection.WikipediaTitles;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by Johannes on 6.4.2017.
 */
public class WikiCorpusTest {
    @Test
    public void buildCorpus() throws Exception {
        Resource resource = new ClassPathResource("wiki");
        WikiCorpus wc = new WikiCorpus(resource.getFile().getCanonicalPath());
        wc.buildCorpus();
        List<String> lines = Files.list(Paths.get(resource.getFile().getAbsolutePath())).collect(Collectors.toList()).stream().map(path -> {
            try {
                return Files.readAllLines(path);
            } catch (IOException e) { return null; }
        }).flatMap(Collection::stream).collect(Collectors.toList());
        assertEquals(0.01, wc.getAccuracy(), 0);
        boolean anyContained = lines.stream().anyMatch(wc::mightContain);
        int countContainer = (int) lines.stream().filter(wc::mightContain).count();
        assertTrue(anyContained);
        assertTrue(countContainer > 1);
    }

    @Test
    public void mightContain() throws Exception {
    }

    @Test
    public void reliabilityOfContain() throws Exception {
    }

}