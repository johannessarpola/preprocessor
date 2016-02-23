/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArticleFactory;

import Global.Options;
import Utilities.ChunkedInput.FileChunks;
import Utilities.Structures.ReutersArticles;
import java.io.IOException;

/**
 * Manages reading from filechunks to news articles, 
 * goes forward lineary so that there's no way to 'go back'
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ChunkArticleBuilder {

    private FileChunks fc;
    private int indexOfLastUsedArticle = 0;
    /**
     * Manages the articles in chunks on disk
     */
    public ChunkArticleBuilder() {
        init();
    }

    private void init() {
        fc = new FileChunks(Options.CHUNKS);
    }
    /**
     * Reads ALL the articles
     * @TODO Not yet implemented, is on consideration
     * @return 
     */
    public ReutersArticles buildArticles() {
        // TODO Implement all articles in memory if required
        return null;
    }
    /**
     * Reads the next chunk on disk
     * @return
     * @throws IOException 
     */
    public ReutersArticles buildArticleChunk() throws IOException {
        ReutersArticles articles = new ReutersArticles(fc.getChunkContent(indexOfLastUsedArticle), fc.getChunk(indexOfLastUsedArticle).getName());
        indexOfLastUsedArticle++;
        return articles;
    }
    /**
     * Capsulates the files behind FileChunks to prevent altering of the list of files
     * @param index
     * @return 
     */
    public String getChunk(int index){
        return fc.getChunk(index).getAbsolutePath();
    }
     public String getChunkName(int index){
        return fc.getChunk(index).getName();
    }
     public int getNumberOfChunks(){
         return fc.getNumberOfChunks();
     }
}
