/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions.Core;

import fi.johannes.Core.AppConf.SupportedProcessingMethods;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public interface GenericServiceMethods {
    // Need just to define that there is a method to process line which is implemented
    // as defined in the abstract class of Cluster or implementation specific case
    public String processLine(String line, SupportedProcessingMethods method, int biasingsize) throws ServiceNotReadyException, UnhandledServiceException;
}
