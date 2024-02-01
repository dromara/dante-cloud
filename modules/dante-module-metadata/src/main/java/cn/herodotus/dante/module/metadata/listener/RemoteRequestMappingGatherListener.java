/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.metadata.listener;

import cn.herodotus.dante.module.metadata.processor.AttributeTransmitterDistributeProcessor;
import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.stirrup.message.ability.domain.RequestMapping;
import cn.herodotus.stirrup.oauth2.authorization.autoconfigure.bus.RemoteRequestMappingGatherEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Description: SecurityMetadata远程变更事件监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 16:16
 */
@Component
public class RemoteRequestMappingGatherListener implements ApplicationListener<RemoteRequestMappingGatherEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteRequestMappingGatherListener.class);

    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    @Autowired
    public RemoteRequestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    @Override
    public void onApplicationEvent(RemoteRequestMappingGatherEvent event) {

        log.info("[Herodotus] |- Request mapping gather REMOTE listener, response event!");

        String requestMapping = event.getData();
        log.debug("[Herodotus] |- Fetch data [{}]", requestMapping);
        if (ObjectUtils.isNotEmpty(requestMapping)) {
            List<RequestMapping> requestMappings = Jackson2Utils.toList(requestMapping, RequestMapping.class);
            if (CollectionUtils.isNotEmpty(requestMappings)) {
                log.debug("[Herodotus] |- [4] Async store request mapping process BEGIN!");
                attributeTransmitterDistributeProcessor.postRequestMappings(requestMappings);
            }
        }
    }
}
