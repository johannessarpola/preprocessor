package fi.johannes.Core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Johannes on 20.4.2017.
 */

@Component
@ConfigurationProperties("general")
public class AppConf {

    private int seed;
    private Paths paths;
    private Resources resources;

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public Paths getPaths() {
        return paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public static class Paths {
        private String data;
        private String raw;
        private String supervised;
        private String entities;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getSupervised() {
            return supervised;
        }

        public void setSupervised(String supervised) {
            this.supervised = supervised;
        }

        public String getEntities() {
            return entities;
        }

        public void setEntities(String entities) {
            this.entities = entities;
        }
    }

    public static class Resources {
        private String stopwords;

        public String getStopwords() {
            return stopwords;
        }

        public void setStopwords(String stopwords) {
            this.stopwords = stopwords;
        }

        public Resources() {
        }
    }
}
