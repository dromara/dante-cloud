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
 * File Name: SysMetadataEntityListener.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:08:05
 */

package cn.herodotus.eurynome.upms.api.listener.entity;

import cn.herodotus.eurynome.upms.api.entity.system.SysMetadata;
import cn.herodotus.eurynome.upms.api.listener.event.SysMetadataChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.persistence.PostUpdate;

/**
 * <p>Description: SysMetadata实体变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:08
 */
public class SysMetadataEntityListener implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SysMetadataEntityListener.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostUpdate
    protected void postUpdate(SysMetadata entity) {
        log.trace("[Eurynome] |- SysMetadataEntityListener @PostUpdate : [{}]", entity.toString());
        this.applicationContext.publishEvent(new SysMetadataChangeEvent(entity));
    }
}
