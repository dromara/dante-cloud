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
 * Module Name: eurynome-cloud-upms-api
 * File Name: SysScopeEntityListener.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:21:05
 */

package cn.herodotus.eurynome.upms.api.listener.entity;

import cn.herodotus.eurynome.upms.api.entity.oauth.OAuth2Scopes;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.listener.event.SysMetadataRelationChangeEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysScope实体数据变更监听器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:21
 */
public class SysScopeEntityListener implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SysScopeEntityListener.class);

    private ApplicationContext applicationContext;

    private OAuth2Scopes preOAuth2Scopes;
    private OAuth2Scopes postOAuth2Scopes;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PreUpdate
    protected void preUpdate(OAuth2Scopes entity) {
        log.trace("[Herodotus] |- SysScopeEntityListener @PreUpdate : [{}]", entity.toString());
        this.preOAuth2Scopes = entity;
    }

    @PostUpdate
    protected void postUpdate(OAuth2Scopes entity) {
        log.trace("[Herodotus] |- SysScopeEntityListener @PostUpdate : [{}]", entity.toString());
        this.postOAuth2Scopes = entity;
        this.applicationContext.publishEvent(new SysMetadataRelationChangeEvent(this.getChangedAuthority()));
    }

    @PostRemove
    protected void postRemove(OAuth2Scopes entity) {
        log.trace("[Herodotus] |- SysScopeEntityListener @PostRemove : [{}]", entity.toString());
        if (CollectionUtils.isNotEmpty(entity.getAuthorities())) {
            this.applicationContext.publishEvent(new SysMetadataRelationChangeEvent(entity.getAuthorities()));
        }
    }

    private Set<SysAuthority> getPostAuthorities() {
        if (ObjectUtils.isNotEmpty(this.postOAuth2Scopes)) {
            return this.postOAuth2Scopes.getAuthorities();
        }
        return null;
    }

    private Set<SysAuthority> getPreAuthorities() {
        if (ObjectUtils.isNotEmpty(this.preOAuth2Scopes)) {
            return this.preOAuth2Scopes.getAuthorities();
        }
        return null;
    }

    private Collection<SysAuthority> getChangedAuthority() {
        Set<SysAuthority> preAuthorities = this.getPreAuthorities();
        Set<SysAuthority> postAuthorities = this.getPostAuthorities();
        if (CollectionUtils.isNotEmpty(preAuthorities) && CollectionUtils.isNotEmpty(postAuthorities)) {
            return CollectionUtils.disjunction(preAuthorities, postAuthorities);
        }

        if (CollectionUtils.isNotEmpty(preAuthorities) && CollectionUtils.isEmpty(postAuthorities)) {
            return preAuthorities;
        }

        if (CollectionUtils.isEmpty(preAuthorities) && CollectionUtils.isNotEmpty(postAuthorities)) {
            return postAuthorities;
        }

        return new HashSet<>();
    }
}
