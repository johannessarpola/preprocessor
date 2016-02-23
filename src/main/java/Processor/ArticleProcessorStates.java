/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

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

    public void setUseRemoveURLs(boolean aUseRemoveURLs) {
        useRemoveURLs = aUseRemoveURLs;
    }

    public void setUseRemoveNumbers(boolean aUseRemoveNumbers) {
        useRemoveNumbers = aUseRemoveNumbers;
    }

    public boolean isUseRemoveTags() {
        return useRemoveTags;
    }

    public void setUseRemoveTags(boolean useRemoveTags) {
        this.useRemoveTags = useRemoveTags;
    }

    public boolean isUseLowercaseOnly() {
        return useLowercaseOnly;
    }

    public void setUseLowercaseOnly(boolean useLowercaseOnly) {
        this.useLowercaseOnly = useLowercaseOnly;
    }

    public boolean isUseRemovePunctuationMarks() {
        return useRemovePunctuationMarks;
    }

    public void setUseRemovePunctuationMarks(boolean useRemovePunctuationMarks) {
        this.useRemovePunctuationMarks = useRemovePunctuationMarks;
    }

    public boolean isUseRemoveSingleCharacters() {
        return useRemoveSingleCharacters;
    }

    public void setUseRemoveSingleCharacters(boolean useRemoveSingleCharacters) {
        this.useRemoveSingleCharacters = useRemoveSingleCharacters;
    }
}
