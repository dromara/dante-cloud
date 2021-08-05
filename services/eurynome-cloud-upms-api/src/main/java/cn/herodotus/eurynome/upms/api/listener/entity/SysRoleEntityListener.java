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
 * File Name: SysRoleEntityListener.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:21:05
 */

package cn.herodotus.eurynome.upms.api.listener.entity;

import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
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
 * <p>Description: SysRole实体变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:21
 */
public class SysRoleEntityListener implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SysRoleEntityListener.class);

    private ApplicationContext applicationContext;

    private SysRole preSysRole;
    private SysRole postSysRole;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PreUpdate
    protected void preUpdate(SysRole entity) {
        log.trace("[Eurynome] |- SysRoleEntityListener @PreUpdate : [{}]", entity.toString());
        this.preSysRole = entity;
    }

    @PostUpdate
    protected void postUpdate(SysRole entity) {
        log.trace("[Eurynome] |- SysRoleEntityListener @PostUpdate : [{}]", entity.toString());
        this.postSysRole = entity;
        this.applicationContext.publishEvent(new SysMetadataRelationChangeEvent(this.getChangedAuthority()));
    }

    @PostRemove
    protected void postRemove(SysRole entity) {
        log.trace("[Eurynome] |- BaseEntityListener @PostRemove : [{}]", entity.toString());
        if (CollectionUtils.isNotEmpty(entity.getAuthorities())) {
            this.applicationContext.publishEvent(new SysMetadataRelationChangeEvent(entity.getAuthorities()));
        }
    }

    private Set<SysAuthority> getPostAuthorities() {
        if (ObjectUtils.isNotEmpty(this.postSysRole)) {
            return this.postSysRole.getAuthorities();
        }
        return null;
    }

    private Set<SysAuthority> getPreAuthorities() {
        if (ObjectUtils.isNotEmpty(this.preSysRole)) {
            return this.preSysRole.getAuthorities();
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
