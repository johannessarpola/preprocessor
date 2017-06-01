package fi.johannes.VectorOutput

import com.google.common.collect.Multiset
import spock.lang.Specification

/**
 * Created by Johannes on 1.6.2017.
 */
class OutputUtilsTest extends Specification {
    def "ToMultiset"() {
        given:
        String line = "A A B B C";

        when:
        Multiset<String> ms = OutputUtils.toMultiset(line)

        then:
        ms.entrySet().find { entry ->
            entry.element == "A"
        }.count == 2

        ms.entrySet().find { entry ->
            entry.element == "B"
        }.count == 2


        ms.entrySet().find { entry ->
            entry.element == "C"
        }.count == 1


    }
}
