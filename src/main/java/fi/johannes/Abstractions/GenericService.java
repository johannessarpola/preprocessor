/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions;

import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Core.AppConf.SupportedProcessingMethods;
import fi.johannes.Core.AppConf;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;

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
    protected AppConf appConf;
    
    public GenericService(SupportedProcessingStrategy id) {
        this.serviceId = id;
        this.isServiceReady = false;
        this.requiresVocabulary = true;
        this.isVocabularyAdded = false;
    }

    public SupportedProcessingStrategy getName() {
        return serviceId;
    }

    // todo This would be better as a interface which requires documents to be added
    public abstract void build(List<String> documents);

    @Override
    public String processLine(String line, SupportedProcessingMethods method, int biasingsize) throws ServiceNotReadyException, UnhandledServiceException {
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

    public abstract String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException;

    public abstract String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException;

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
