/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstractions.Core;

import Global.Options;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class GenericService implements GenericServiceMethods {

    // TODO Implement abstract class for services 
    // Similar to clusters but in this case are likes of Concept Insights or Alchemy API
    protected SupportedProcessingStrategy serviceId;
    protected boolean isServiceReady;
    protected boolean requiresVocabulary;
    protected boolean isVocabularyAdded;

    
    public GenericService(SupportedProcessingStrategy id) {
        this.serviceId = id;
        this.isServiceReady = false;
        this.requiresVocabulary = true;
        this.isVocabularyAdded = false;
    }

    public SupportedProcessingStrategy getName() {
        return serviceId;
    }

    public abstract void build(List<String> documents);

    @Override
    public String processLine(String line, Options.SupportedProcessingParadigms method, int biasingsize) throws ServiceNotReadyException {
        if (isServiceReady) {
            if (null != method) {
                switch (method) {
                    case Append:
                        return this.processLineByAppend(line, biasingsize);
                    case Replace:
                        return this.processLineByReplace(line, biasingsize);
                    default:
                        return (null);
                }
            }
        }
        throw new ServiceNotReadyException();
    }
    // Needs to implement both methods of processing

    public abstract String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException;

    public abstract String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException;

    public boolean isServiceReady() {
        return isServiceReady;
    }

    public boolean doesRequireVocabulary() {
        return requiresVocabulary;
    }
    public boolean isVocabularyAdded(){
        return isVocabularyAdded;
    }
    public abstract void clear();
}
