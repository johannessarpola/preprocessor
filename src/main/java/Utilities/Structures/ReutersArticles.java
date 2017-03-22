/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Johannes töissä
 */
public class ReutersArticles {

    String[] columnNames;
    private ArrayList<Article> Articles;
    private int numberOfArticles = 0;
    Charset cs;
    private String sourceFilename = "";

    public ReutersArticles(List<char[]> rows, String sourceFile, String delimeter, Charset cs) {
        this.cs = cs;
        init(rows, delimeter);
    }

    public ReutersArticles(List<char[]> rows, String sourceFile, String delimeter) {
        this.cs = StandardCharsets.UTF_8;
        this.sourceFilename = sourceFile;
        init(rows, delimeter);
    }

    /**
     * Uses default separator of ","
     *
     * @param rows
     * @param sourceFile
     */
    public ReutersArticles(List<char[]> rows, String sourceFile) {
        this.sourceFilename = sourceFile;
        this.cs = StandardCharsets.UTF_8; // Default Charset (UTF8)
        init(rows, ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Default CSV delimeter ','
    }

    private void init(List<char[]> rows, String delimeter) {
        this.Articles = new ArrayList<>();
        for (char[] article : rows) {
            Articles.add(new Article(article, delimeter));
        }
        // Column names are in the first
        Article first = Articles.get(0);
        int i = 0;
        columnNames = new String[first.getCharArraysInOrder().size()];
        for (char[] charArr : first.getCharArraysInOrder()) {
            String field = String.valueOf(charArr);
            // Remove extra characters for keys 
            String charsToRemove = "%^#\"";
            field = CharMatcher.anyOf(charsToRemove).removeFrom(field);
            columnNames[i] = field;
            i++;
        }
        Articles.remove(0);
        numberOfArticles = Articles.size();
    }

    /**
     * Gets the artile with column names
     *
     * @param index of article
     * @return Map of article
     */
    // TODO Remove \"
    public Map<String, String> getArticle(int index) {
        Article art = Articles.get(index);
        TreeMap<String, String> tm = new TreeMap<>();
        int i = 0;
        for (char[] charArr : art.getCharArraysInOrder()) {
            String field = String.valueOf(charArr);
            tm.put(columnNames[i], field);
            i++;
        }
        return tm;
    }

    public int getArticleCount() {
        return Articles.size();
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public int getNumberOfArticles() {
        return numberOfArticles;
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    public class Article {

        private char[] date;
        private char[] title;
        private char[] story;

        public Article(char[] row, String delimeter) {
            divideAndStore(row, delimeter);
        }

        private void divideAndStore(char[] row, String delimeter) {
            String rowStr = String.valueOf(row);
            Iterable<String> strs = Splitter.onPattern(delimeter).split(rowStr);
            int i = 0;
            for (String s : strs) {
                switch (i) {
                    case 0:
                        date = s.toCharArray();
                        i++;
                        continue;
                    case 1:
                        title = s.toCharArray();
                        i++;
                        continue;
                    case 2:
                        story = s.toCharArray();
                        i++;
                        continue;
                }
            }
        }

        public ArrayList<char[]> getCharArraysInOrder() {
            ArrayList<char[]> l = new ArrayList<>();
            l.add(getDate());
            l.add(getTitle());
            l.add(getStory());
            return l;
        }

        public char[] getDate() {
            return date;
        }

        public char[] getTitle() {
            return title;
        }

        public char[] getStory() {
            return story;
        }

        public void setDate(char[] date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return String.valueOf(date) + "/" + String.valueOf(title) + String.valueOf(story);
        }
    }
}
