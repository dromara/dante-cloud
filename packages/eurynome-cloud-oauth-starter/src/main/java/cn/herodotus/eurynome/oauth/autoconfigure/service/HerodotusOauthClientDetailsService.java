/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-oauth-starter
 * File Name: HerodotusOauthClientDetailsService.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.oauth.autoconfigure.service;

import cn.herodotus.eurynome.assistant.enums.StatusEnum;
import cn.herodotus.eurynome.security.definition.core.HerodotusClientDetails;
import cn.herodotus.eurynome.security.definition.service.HerodotusClientDetailsService;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthClientDetailsService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * <p>Description: ClientDetailsService核心类 </p>
 *
 * 之前一直使用Fegin进行UserDetailsService的远程调用。现在直接改为数据库访问。主要原因是：
 * 1. 根据目前的设计，Oauth的表与系统权限相关的表是在一个库中的。因此UAA和UPMS分开是为了以后提高新能考虑，逻辑上没有必要分成两个服务。
 * 2. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，调用频繁增加一道远程调用增加消耗而已。
 * 3. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，只是UAA在使用。
 * 4. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，是各种验证权限之前必须调用的内容。
 *    一方面：使用feign的方式调用，只能采取作为白名单的方式，安全性无法保证。
 *    另一方面：会产生调用的循环。
 * 因此，最终考虑把这两个服务相关的代码，抽取至UPMS API，采用UAA直接访问数据库的方式。
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:02
 */

public class HerodotusOauthClientDetailsService implements HerodotusClientDetailsService {

    private static final Logger log = LoggerFactory.getLogger(HerodotusOauthClientDetailsService.class);

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    /**
     * 这里AdditionalInformation的用途：
     * Oauth2自带表结构，只能满足Oauth2的基本使用，但是如果要实际应用，还需要进一步扩展。
     * 优雅的方式肯定是在不改动原表和代码的情况下，自己扩展数据表。同时，为了保证自己扩展内容可以和原表进行交互，所以提供的AdditionalInformation信息，进行处理。
     * Open-Cloud是把自己扩展表的信息，以JSON的格式存入到AdditionalInformation，取出以后在进行处理和应用。
     *
     * @param clientId clientId
     * @return ClientDetails
     * @throws ClientRegistrationException ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        HerodotusClientDetails herodotusClientDetails = getOauthClientDetails(clientId);
        if (herodotusClientDetails != null && herodotusClientDetails.getAdditionalInformation() != null) {
            String status = herodotusClientDetails.getAdditionalInformation().getOrDefault("status", "0").toString();
            if (String.valueOf(StatusEnum.FORBIDDEN.getIndex()).equals(status)) {
                log.warn("[Herodotus] |- Client [{}] has been Forbidden! ", herodotusClientDetails.getClientId());
                throw new ClientRegistrationException("客户端已被禁用");
            }
        }
        return herodotusClientDetails;
    }

    /**
     * 2019.09.01
     * 由于Oauth2自身的查询使用原生SQL，目前还不知道如何进行缓存处理，为了减少以后的性能问题，所以将oauth_client_details增加了jpa的处理。
     * 同时，为了方便SysApplication和oauth_client_details的联动，将两者作为@OneToOne处理，并将oauth_client_details操作移动到了Upms中。
     * <p>
     * 在处理的过程中，OAuth2ClientDetails需要set 相关的权限。一种解决办法就是需要远程查询两次，第二种办法就是在服务端一次查询完成后返回。
     * 第一种方法感觉太low，所以采取的是第二种方法。
     * <p>
     * 第二种方法在实现过程中比较曲折，最早是在Upms端就直接把值set好，然后远程返回给OAuth。但是在这个过程中出现了Jackson多态问题。
     * 经过查询使用@JsonTypeInfo是Jackson处理多态的方式。逻辑上在GrantedAuthority接口上设置就行，但是TMD这个OAuth2的东西，动不了。
     * 所以最后采取了一种“绕”的方式，重新拼凑一个OAuth2Application对象，装载相关的值，然后拿到OAuth2端进行拼装。
     *
     * @param clientId clientId
     * @return HerodotusClientDetails
     */
    @Override
    public HerodotusClientDetails getOauthClientDetails(String clientId) {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(clientId);

        if (ObjectUtils.isEmpty(oauthClientDetails)) {
            log.error("[Herodotus] |- Can not Fetch the Remote Client Details!");
            return null;
        } else {
            HerodotusClientDetails herodotusClientDetails = UpmsHelper.convertOauthClientDetailsToHerodotusClientDetails(oauthClientDetails);
            log.debug("[Herodotus] |- Fetch Remote Client Details Successfully! [{}]", herodotusClientDetails.getClientId());
            return herodotusClientDetails;
        }
    }
}
