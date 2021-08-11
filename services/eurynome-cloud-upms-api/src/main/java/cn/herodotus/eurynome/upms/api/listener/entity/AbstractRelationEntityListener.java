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
 * File Name: AbstractRelationEntityListener.java
 * Author: gengwei.zheng
 * Date: 2021/08/11 20:16:11
 */

package cn.herodotus.eurynome.upms.api.listener.entity;

import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 抽取基于SysAuthority关联关系的实体变更 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/11 20:16
 */
public abstract class AbstractRelationEntityListener implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<String> before;
    private List<String> after;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setBefore(List<String> before) {
        this.before = before;
    }

    public void setAfter(List<String> after) {
        this.after = after;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected List<String> clone(Set<SysAuthority> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            return sysAuthorities.stream().map(SysAuthority:: getAuthorityId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    protected List<String> getChangedAuthority() {
        if (CollectionUtils.isNotEmpty(this.before) && CollectionUtils.isNotEmpty(this.after)) {
            return new ArrayList<>(CollectionUtils.disjunction(this.before, this.after));
        }

        if (CollectionUtils.isNotEmpty(this.before) && CollectionUtils.isEmpty(this.after)) {
            return this.before;
        }

        if (CollectionUtils.isEmpty(this.before) && CollectionUtils.isNotEmpty(this.after)) {
            return this.after;
        }

        return new ArrayList<>();
    }
}
