/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author Johannes töissä
 */
public class ReutersArticles {

    private String[] columnNames;
    private ArrayList<Article> articles;
    private int numberOfArticles = 0;
    private Charset cs;
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
        this.articles = new ArrayList<>();
        for (char[] article : rows) {
            articles.add(new Article(article, delimeter));
        }
        // Column names are in the first
        Article first = articles.get(0);
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
        articles.remove(0);
        numberOfArticles = articles.size();
    }

    /**
     * Gets the artile with column names
     *
     * @param index of article
     * @return Map of article
     */
    // TODO Remove \"
    public Map<String, String> getArticleAsMap(int index) {
        Article art = articles.get(index);
        TreeMap<String, String> tm = new TreeMap<>();
        int i = 0;
        for (char[] charArr : art.getCharArraysInOrder()) {
            String field = String.valueOf(charArr);
            tm.put(columnNames[i], field);
            i++;
        }
        return tm;
    }
    public Article getArticle(int index) {
        return articles.get(index);
    }

    public int getArticleCount() {
        return articles.size();
    }

    public List<Article> getArticles() {
        return articles;
    }

    public int getNumberOfArticles() {
        return numberOfArticles;
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    @Data
    public static class ArticleDto {
        private LocalDateTime date;
        private String title;
        private String story;

        // headlines don't use iso
        static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public static ArticleDto from(Article article) {
            ArticleDto dto = new ArticleDto();
            dto.date = LocalDateTime.parse(new String(article.date), formatter);
            dto.title = new String(article.date);
            dto.story = new String(article.story);
            return dto;
        }
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
