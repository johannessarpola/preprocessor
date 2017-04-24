package fi.johannes.Clusters.EntityDetection

import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus
import fi.johannes.Core.AppConf
import org.junit.Test
import spock.lang.Specification

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * johanness on 24/04/2017.
 */
class EntityDetectionServiceTest extends Specification {
    EntityDetectionService es = new EntityDetectionService(new MockupEntityCorpus())

    def "Build"() {
        es.build(null)
        assertTrue("Service should be ready", es.isServiceReady())
    }

    def "ProcessLineByAppend"() {

        given:
        int m = Integer.MAX_VALUE
        es.build(null)

        when:
        String res = es.processLineByAppend("Hello Johannes", m)
        String res2 = es.processLineByAppend("Hello Johannes Abens", m)
        String res3 = es.processLineByAppend("Hello Corpus Johannes Abens", m)

        then:
        "Hello Johannes Hello" == res
        "Hello Johannes Abens Hello Abens" == res2
        "Hello Corpus Johannes Abens Hello Corpus Hello Corpus Abens" == res3

    }

    def "ProcessLineByReplace"() {

        given:
        int m = Integer.MAX_VALUE
        es.build(null)

        when:
        String res = es.processLineByReplace("Hello Johannes", m)
        String res2 = es.processLineByReplace("Hello Johannes Abens", m)

        then:
        "Hello" == res
        "Hello Abens" == res2
    }

    def "Clear"() {
    }

    class MockupEntityCorpus implements EntityCorpus {

        MockupEntityCorpus() {
        }

        @Override
        void buildCorpus() {

        }

        @Override
        boolean doesContain(String word) {
            def contained = ["Hello", "Hello Corpus", "Corpus", "Abens"]
            return contained.contains(word)
        }

        @Override
        double reliabilityOfContain() {
            return 1
        }

        @Override
        AppConf.SupportedCorpuses getId() {
            return null
        }
    }
}
