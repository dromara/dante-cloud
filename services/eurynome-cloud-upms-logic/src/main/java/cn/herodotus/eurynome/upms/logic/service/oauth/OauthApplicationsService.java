/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-upms-logic
 * File Name: OauthApplicationsService.java
 * Author: gengwei.zheng
 * Date: 2021/1/18 下午6:20
 * LastModified: 2021/1/17 下午7:25
 */

package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2Scopes;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthApplicationsRepository;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : OauthApplicationsService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 17:00
 */
@Service
public class OauthApplicationsService extends BaseLayeredService<OauthApplications, String> {

    private static final Logger log = LoggerFactory.getLogger(OauthApplicationsService.class);

    @Autowired
    private OauthApplicationsRepository oauthApplicationsRepository;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Override
    public BaseRepository<OauthApplications, String> getRepository() {
        return oauthApplicationsRepository;
    }

    @Override
    public void deleteById(String appKey) {
        super.deleteById(appKey);
        oauthClientDetailsService.deleteById(appKey);
    }

    @Override
    public OauthApplications saveOrUpdate(OauthApplications domain) {
        if(ObjectUtils.isNotEmpty(domain)) {
            if (StringUtils.isBlank(domain.getAppSecret())) {
                domain.setAppSecret(IdUtil.randomUUID());
            }
        }
        OauthApplications oauthApplications = super.saveOrUpdate(domain);
        oauthClientDetailsService.synchronize(oauthApplications);
        return oauthApplications;
    }

    public OauthApplications assign(String appKey, String[] scopeIds) {

        log.debug("[Eurynome] |- OauthApplications Service authorize.");

        Set<OAuth2Scopes> OAuth2ScopesSet = new HashSet<>();
        for (String scopeId : scopeIds) {
            OAuth2Scopes OAuth2Scopes = new OAuth2Scopes();
            OAuth2Scopes.setScopeId(scopeId);
            OAuth2ScopesSet.add(OAuth2Scopes);
        }

        OauthApplications oauthApplications = findById(appKey);
        oauthApplications.setScopes(OAuth2ScopesSet);

        return saveOrUpdate(oauthApplications);
    }
}
