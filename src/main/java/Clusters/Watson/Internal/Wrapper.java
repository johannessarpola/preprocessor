/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Internal;

import com.ibm.watson.developer_cloud.service.WatsonService;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public interface Wrapper {
    public void connect(WatsonService ws);
}
