package fi.johannes.Core

import spock.lang.Specification;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Johannes on 7.5.2017.
 */
class AppIOTest extends Specification {

    def "test stream all files in folder"()  throws Exception {
        given:
        String p = App.getResource("io-testing").getFile().getAbsolutePath();

        when:
        Stream<String> stream = AppIO.readStreamsForFiles(p);
        List<String> streamList = stream.collect(Collectors.toList())

        then:
        streamList != null
        streamList.size() == 3
        streamList.find { it == "this is first file" }
        streamList.find { it == "this is second file" }
        streamList.find { it == "this is third file" }
    }

    def "test stream file"() throws Exception {
        given:
        String p = App.getResource("io-testing/first.txt").getFile().getAbsolutePath();

        when:
        Stream<String> stream = AppIO.readStreamsForFiles(p);
        String join = stream.collect(Collectors.joining(""))

        then:
        join != null
        join == "this is first file"

    }

}