/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

/**
 * State machine on how to preprocess with ArticleProcessor
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ArticleProcessorStates {

    private boolean useRemoveStopwords = true;
    private boolean usePorterstemmer = true;
    private boolean useStanfordLemmatizer = false;
    private boolean useRemoveURLs = true;
    private boolean useRemoveNumbers = true;
    private boolean useRemoveSpecialCharacters = true;
    private boolean useRemoveTags = true;
    private boolean useLowercaseOnly = true;
    private boolean useRemovePunctuationMarks = true;
    private boolean useRemoveSingleCharacters = true;

    public boolean isUseReplacePunctuationMarksWithSpaces() {
        return useReplacePunctuationMarksWithSpaces;
    }

    public void setUseReplacePunctuationMarksWithSpaces(boolean useReplacePunctuationMarksWithSpaces) {
        this.useReplacePunctuationMarksWithSpaces = useReplacePunctuationMarksWithSpaces;
    }

    private boolean useReplacePunctuationMarksWithSpaces = false;
    
    public ArticleProcessorStates() {
    }

    public boolean isUseRemoveSpecialCharacters() {
        return useRemoveSpecialCharacters;
    }

    public void setUseRemoveSpecialCharacters(boolean aUseRemoveSpecialCharacters) {
        useRemoveSpecialCharacters = aUseRemoveSpecialCharacters;
    }

    public boolean isUseRemoveStopwords() {
        return useRemoveStopwords;
    }

    public boolean isUsePorterstemmer() {
        return usePorterstemmer;
    }

    public boolean isUseStanfordLemmatizer() {
        return useStanfordLemmatizer;
    }

    public boolean isUseRemoveURLs() {
        return useRemoveURLs;
    }

    public boolean isUseRemoveNumbers() {
        return useRemoveNumbers;
    }

    public void setUseRemoveStopwords(boolean aUseRemoveStopwords) {
        useRemoveStopwords = aUseRemoveStopwords;
    }

    public void setUsePorterstemmer(boolean aUsePorterstemmer) {
        usePorterstemmer = aUsePorterstemmer;
        useStanfordLemmatizer = !aUsePorterstemmer;
    }

    public void setUseStanfordLemmatizer(boolean aUseStanfordLemmatizer) {
        useStanfordLemmatizer = aUseStanfordLemmatizer;
        usePorterstemmer = !aUseStanfordLemmatizer;
    }

    public boolean isUseRemoveTags() {
        return useRemoveTags;
    }

    public boolean isUseLowercaseOnly() {
        return useLowercaseOnly;
    }

    public boolean isUseRemovePunctuationMarks() {
        return useRemovePunctuationMarks;
    }

    public boolean isUseRemoveSingleCharacters() {
        return useRemoveSingleCharacters;
    }

    public ArticleProcessorStates useLowercase() {
        this.useLowercaseOnly = true;
        return this;
    }
    public ArticleProcessorStates useLemmatization() {
        this.useStanfordLemmatizer = true;
        this.usePorterstemmer = false;
        return this;
    }
    public ArticleProcessorStates useStemming() {
        this.usePorterstemmer = true;
        this.useStanfordLemmatizer = false;
        return this;
    }
    public ArticleProcessorStates removeUrls() {
        this.useRemoveURLs = true;
        return this;
    }
    public ArticleProcessorStates removeNumbers() {
        this.useRemoveNumbers = true;
        return this;
    }
    public ArticleProcessorStates removeStopwords() {
        this.useRemoveStopwords = true;
        return this;
    }
    public ArticleProcessorStates removeRemoveTags() {
        this.useRemoveTags = true;
        return this;
    }
    public ArticleProcessorStates removeSingleCharacters() {
        this.useRemoveSingleCharacters = true;
        return this;
    }
    public ArticleProcessorStates removePunctuation() {
        this.useRemovePunctuationMarks = true;
        return this;
    }
    public ArticleProcessorStates removeSpecialCharacters() {
        this.useRemoveSpecialCharacters = true;
        return this;
    }
    public ArticleProcessorStates replacePunctuationWithSpace() {
        this.useReplacePunctuationMarksWithSpaces = true;
        return this;
    }
}
