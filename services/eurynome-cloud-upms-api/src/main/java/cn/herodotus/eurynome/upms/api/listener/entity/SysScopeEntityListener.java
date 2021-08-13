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
import cn.herodotus.eurynome.upms.api.listener.event.SysSecurityAttributeRelationChangeEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostLoad;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.util.*;

/**
 * <p>Description: SysScope实体数据变更监听器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:21
 */
public class SysScopeEntityListener extends AbstractRelationEntityListener {

    private static final Logger log = LoggerFactory.getLogger(SysScopeEntityListener.class);

    private List<String> clone(OAuth2Scopes oAuth2Scopes) {
        if (ObjectUtils.isNotEmpty(oAuth2Scopes)) {
            return this.clone(oAuth2Scopes.getAuthorities());
        }
        return new ArrayList<>();
    }

    @PostLoad
    protected void postLoad(OAuth2Scopes entity) {
        log.debug("[Eurynome] |- SysScopeEntityListener @PostLoad : [{}]", entity.toString());
        this.setBefore(clone(entity));
    }

    @PreUpdate
    protected void preUpdate(OAuth2Scopes entity) {
        log.debug("[Eurynome] |- SysScopeEntityListener @PreUpdate actived, value is : [{}]. Trigger SysSecurityAttribute relation change event.", entity.toString());
        this.setAfter(clone(entity));
    }

    @PostUpdate
    protected void postUpdate(OAuth2Scopes entity) {
        log.debug("[Eurynome] |- SysScopeEntityListener @PostUpdate actived, value is : [{}]. Trigger SysSecurityAttribute relation change event.", entity.toString());
        this.getApplicationContext().publishEvent(new SysSecurityAttributeRelationChangeEvent(this.getChangedAuthority()));
    }

    @PostRemove
    protected void postRemove(OAuth2Scopes entity) {
        log.debug("[Eurynome] |- SysScopeEntityListener @PostRemove actived, value is : [{}]. Trigger SysSecurityAttribute relation change event.", entity.toString());
        if (CollectionUtils.isNotEmpty(entity.getAuthorities())) {
            this.getApplicationContext().publishEvent(new SysSecurityAttributeRelationChangeEvent(clone(entity)));
        }
    }
}
