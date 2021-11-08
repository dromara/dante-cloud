/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-assistant
 * File Name: MinioEnabledCondition.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 20:50:08
 */

package cn.herodotus.eurynome.assistant.condition;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import cn.herodotus.eurynome.assistant.resolver.PropertyResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: Minio是否开启条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 20:50
 */
public class MinioEnabledCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(MinioEnabledCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String endpoint = PropertyResolver.getProperty(conditionContext, PropertyConstants.ITEM_MINIO_ENDPOINT);
        String accessKey = PropertyResolver.getProperty(conditionContext, PropertyConstants.ITEM_MINIO_ACCESSKEY);
        String secretkey = PropertyResolver.getProperty(conditionContext, PropertyConstants.ITEM_MINIO_SECRETKEY);
        boolean result = StringUtils.isNotBlank(endpoint) && StringUtils.isNotBlank(accessKey) && StringUtils.isNotBlank(secretkey);
        log.debug("[Herodotus] |- Condition [Minio Enabled] value is [{}]", result);
        return result;
    }
}