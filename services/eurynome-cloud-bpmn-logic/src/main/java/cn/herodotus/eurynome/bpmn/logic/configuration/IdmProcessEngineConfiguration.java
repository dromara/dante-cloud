/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-bpmn-logic
 * File Name: IdmProcessEngineConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/1/26 上午10:15
 * LastModified: 2020/1/26 上午10:14
 */

package cn.herodotus.eurynome.bpmn.logic.configuration;

import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.idm.spring.authentication.SpringEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>Description: 更改Flowable默认明文密码 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/26 16:49
 */

@Configuration
public class IdmProcessEngineConfiguration extends SpringIdmEngineConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringEncoder springEncoder(){
        return new SpringEncoder(passwordEncoder());
    }

    @Override
    public IdmEngineConfiguration setPasswordEncoder(org.flowable.idm.api.PasswordEncoder passwordEncoder) {
        return super.setPasswordEncoder(springEncoder());
    }
}
