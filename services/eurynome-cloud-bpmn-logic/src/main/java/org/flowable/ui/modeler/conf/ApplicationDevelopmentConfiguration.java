/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.ui.modeler.conf;

import cn.herodotus.eurynome.bpmn.logic.constants.ProcessConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Development @Profile specific configuration property overrides
 *
 * @author Yvo Swillens
 */
@Slf4j
@Configuration
public class ApplicationDevelopmentConfiguration {

    protected static final boolean FLOWABLE_MODELER_REST_ENABLED = true;
    protected static final int DEFAULT_SERVER_PORT = 8080;

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private FlowableModelerAppProperties flowableModelerAppProperties;

    private int getServerPort() {
        if (serverPort == 0) {
            return DEFAULT_SERVER_PORT;
        }

        return serverPort;
    }

    private String getContextRoot() {

        String hostAddress = "localhost";

        try {
            InetAddress address = InetAddress.getLocalHost();
            if (ObjectUtils.isNotEmpty(address)) {
                hostAddress = address.getHostAddress();
            }
        } catch (UnknownHostException e) {
            log.error("[Herodotus] |- Get Local Host Error", e);
        }

        return "http://" + hostAddress + ":" + getServerPort() + ProcessConstants.FLOWABLE_APP_REST_API_MAPPEED_TO;
    }

    @PostConstruct
    public void postConstruct() {
        flowableModelerAppProperties.setRestEnabled(FLOWABLE_MODELER_REST_ENABLED);

        String deploymentApiUrl = getContextRoot();
        flowableModelerAppProperties.setDeploymentApiUrl(deploymentApiUrl);

        log.info("[Herodotus] |- Auto Configuration the flowable [Deployment Api Url]");
        log.debug("[Herodotus] |- The Deployment Api Url is [{}]", deploymentApiUrl);
    }
}
