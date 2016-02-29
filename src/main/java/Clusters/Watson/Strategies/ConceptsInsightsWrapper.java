/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Strategies;

import Clusters.Watson.Internal.WatsonConnector;
import Clusters.Watson.Internal.WatsonCredentialsStorage;
import Clusters.Watson.Internal.WatsonOptions;
import Global.Options;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import com.ibm.watson.developer_cloud.concept_insights.v2.ConceptInsights;
import com.ibm.watson.developer_cloud.concept_insights.v2.model.Annotations;
import com.ibm.watson.developer_cloud.concept_insights.v2.model.Graph;
import com.ibm.watson.developer_cloud.concept_insights.v2.model.ScoredConcept;
import com.ibm.watson.developer_cloud.service.WatsonService;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ConceptsInsightsWrapper extends WatsonConnector {

    ConceptInsights client;
    Graph graph;

    public ConceptsInsightsWrapper() {
        super(Options.SupportedProcessingStrategy.ConceptInsights);
    }

    /**
     * Instantiates a new graph using the account id and graph name
     *
     * @param accountId the account id in Bluemix
     * @param name the name of graph
     */
    protected void setGraph(String accountId, String name) {
        this.graph = new Graph(accountId, name);
    }

    /**
     * Annotates the text, gets JsonObject
     *
     * @param line string to annotate
     * @return Json representation of annotation
     */
    private List<ScoredConcept> annotateText(String line) {

        Annotations ann = client.annotateText(graph, line);
        return ann.getAnnotations();
    }

    private TreeMap<String, Double> transFormAnnotations(List<ScoredConcept> l) {
        TreeMap<String, Double> tm = new TreeMap<>();
        for (ScoredConcept a : l) {
            tm.put(a.getConcept().getLabel(), a.getScore());
        }
        return tm;
    }

    /**
     * {
     * "concept": { "id":
     * "/graphs/wikipedia/en-20120601/concepts/Bank_of_Japan", "label": "Bank of
     * Japan" }, "score": 0.6698257 }
     *
     * @param line
     * @return
     */
    @Override
    public String processLineByAppend(String line, int biasingsize) throws ServiceNotReadyException {
        if (isServiceReady) {
            List<ScoredConcept> scs = annotateText(line);
            line = appendConcepts(scs, line, biasingsize);
            return line;
        } else {
            throw new ServiceNotReadyException();
        }
    }

    private String appendConcepts(List<ScoredConcept> scs, String line, int biasingsize) {
        String ret = appendConcepts(scs, line, WatsonOptions.defaultThreshold, biasingsize);
        return ret;
    }

    private String appendConcepts(List<ScoredConcept> scs, String line, double threshold, int biasingsize) {
        int i = 0;
        for (ScoredConcept sc : scs) {
            if (sc.getScore() >= threshold) {
                line += " " + sc.getConcept().getLabel();
            }
            if (i == biasingsize) {
                break;
            }
            i++;
        }
        return line;
    }

    private String replaceWithConcepts(List<ScoredConcept> scs, String line, int biasingsize) {
        String ret = appendConcepts(scs, line, WatsonOptions.defaultThreshold, biasingsize);
        ret = ret.substring(line.length() + 1, ret.length()); // "stiring " replaced
        return ret;
    }

    @Override
    public void build(List<String> documents) {
        // Nothing to do here 
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        if (isServiceReady) {
            List<ScoredConcept> scs = annotateText(line);
            line = replaceWithConcepts(scs, line, biasingSize);
            return line;
        } else {
            throw new ServiceNotReadyException();
        }
    }

    @Override
    public void clear() {
        // Nothing to reinit    
    }

    /**
     * Connects to Watsonservice
     *
     * @param ser WatsonService
     */
    @Override
    public void connect(WatsonCredentialsStorage cs) {
        ConceptInsights ci = new ConceptInsights();
        ci.setUsernameAndPassword(cs.access("credentials.username"), cs.access("credentials.password")); // TODO Enums
        this.client = ci; // needs to have ref to be able to use methods
        this.graph = Graph.WIKIPEDIA;
        isServiceReady = true;
    }

    @Override
    public WatsonService getServiceDirectly() {
        return client;
    }

}
