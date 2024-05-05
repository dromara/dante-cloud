/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
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
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.cloud.authorization.reactive.autoconfigure;

import cn.herodotus.stirrup.oauth2.authorization.reactive.OAuth2AuthorizeExchangeSpecCustomizer;
import cn.herodotus.stirrup.oauth2.authorization.reactive.OAuth2ResourceServerSpecCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

/**
 * <p>Description: Reactive 环境资源服务器配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 15:13
 */
@AutoConfiguration
@EnableWebFluxSecurity
public class AuthorizationReactiveAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationReactiveAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Herodotus Authorization Reactive] Auto Configure.");
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity httpSecurity,
            ServerSecurityContextRepository serverSecurityContextRepository,
            OAuth2AuthorizeExchangeSpecCustomizer oauth2AuthorizeExchangeSpecCustomizer,
            OAuth2ResourceServerSpecCustomizer oauth2ResourceServerSpecCustomizer) {

        log.debug("[Herodotus] |- Bean [Authorization Reactive Security Filter Chain] Auto Configure.");

        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable).cors(ServerHttpSecurity.CorsSpec::disable);

        httpSecurity
                .authorizeExchange(oauth2AuthorizeExchangeSpecCustomizer)
                .oauth2ResourceServer(oauth2ResourceServerSpecCustomizer)
                .securityContextRepository(serverSecurityContextRepository);

        SecurityWebFilterChain chain = httpSecurity.build();

        chain.getWebFilters().toStream()
                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
                .forEach(item -> {
                    AuthenticationWebFilter filter = (AuthenticationWebFilter) item;
                    filter.setSecurityContextRepository(serverSecurityContextRepository);
                });

        return chain;
    }
}
