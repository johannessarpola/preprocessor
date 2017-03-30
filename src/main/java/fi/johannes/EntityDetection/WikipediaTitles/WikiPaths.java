/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.EntityDetection.WikipediaTitles;

/**
 * Filepaths
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiPaths {
    /**
     * Migrated from Wikititles service, a bit redundant 
     * TODO Prune
     */
    protected static final String WORKINGDIR = System.getProperty("user.dir");
    protected static final String SRC = WORKINGDIR + "/src";
    protected static final String TESTS = SRC + "/test";
    protected static final String MAIN = SRC + "/main";
    protected static final String JAVASRC = MAIN + "/java";
    protected static final String RESOURCES = MAIN + "/resources";
    protected static final String WIKIFOLDER = RESOURCES+"/wikis";
}
