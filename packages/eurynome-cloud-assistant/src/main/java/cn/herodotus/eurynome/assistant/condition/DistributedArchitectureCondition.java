/*
 * Copyright (c) 2019-2022 Gengwei Zheng (herodotus@aliyun.com)
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
 * File Name: DistributedArchitectureCondition.java
 * Author: gengwei.zheng
 * Date: 2022/01/11 14:52:11
 */

package cn.herodotus.eurynome.assistant.condition;

import cn.herodotus.eurynome.assistant.enums.Architecture;
import cn.herodotus.eurynome.assistant.resolver.PropertyResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 分布式架构条件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/11 14:52
 */
public class DistributedArchitectureCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(DistributedArchitectureCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String property = PropertyResolver.getArchitecture(conditionContext.getEnvironment(), Architecture.DISTRIBUTED.name());
        boolean result = StringUtils.equalsIgnoreCase(property, Architecture.DISTRIBUTED.name());
        log.debug("[Herodotus] |- Condition [Distributed Architecture] value is [{}]", result);
        return result;
    }
}
