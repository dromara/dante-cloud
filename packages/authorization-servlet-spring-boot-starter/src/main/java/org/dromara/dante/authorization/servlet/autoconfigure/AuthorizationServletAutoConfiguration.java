/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.authorization.servlet.autoconfigure;

import cn.herodotus.engine.oauth2.authorization.servlet.ServletOAuth2AuthorizationConfigurerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>Description: 资源服务器配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/21 23:56
 */
@AutoConfiguration
@EnableWebSecurity
public class AuthorizationServletAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServletAutoConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            ServletOAuth2AuthorizationConfigurerManager authorizationConfigurerManager
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Resource Server Security Filter Chain] Auto Configure.");

        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(authorizationConfigurerManager.getOAuth2AuthorizeHttpRequestsConfigurerCustomer())
                .sessionManagement(authorizationConfigurerManager.getOAuth2SessionManagementConfigurerCustomer())
                .exceptionHandling(authorizationConfigurerManager.getOAuth2ExceptionHandlingConfigurerCustomizer())
                .oauth2ResourceServer(authorizationConfigurerManager.getOAuth2ResourceServerConfigurerCustomer());

        return httpSecurity.build();
    }
}
