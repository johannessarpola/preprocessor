/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Internal;

import Abstractions.GenericService;
import Global.Options;
import com.ibm.watson.developer_cloud.service.WatsonService;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class WatsonConnector extends GenericService{
    
    public WatsonConnector(Options.SupportedProcessingStrategy id){
        super(id);
    }
    // This is required in rebuilding them
    public abstract void connectWith(WatsonCredentialsStorage cs);
    public abstract WatsonService getServiceDirectly();
}
