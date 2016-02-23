/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreprocessingTools;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ZipfsLaw {

    // TODO Take only x % words into account, remove rest
    // Maybe just 5 % as Pareto is 20/80 and stopwords already removed
    private double percenToKeep = 0.2;

    public ZipfsLaw(double percentToKeep) {
        this.percenToKeep = percentToKeep;
    }

    public List<String> removeCommon(List<String> words) {
        return null;
    }

    public List<String> removeCommon(String words) {
        return null;
    }

    public String removeCommonAsString(String words) {
        return null;
    }

    public String removeCommonAsString(List<String> words) {
        return null;
    }

    public double getPercenToKeep() {
        return percenToKeep;
    }

    public void setPercenToKeep(double percenToKeep) {
        this.percenToKeep = percenToKeep;
    }
}
