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
 * File Name: SysMetadataRelationChangeEvent.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:10:05
 */

package cn.herodotus.eurynome.upms.api.listener.event;

import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.List;

/**
 * <p>Description: SysMetadata关联数据变更Event </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:10
 */
public class SysMetadataRelationChangeEvent extends ApplicationEvent {

    private final List<String> changedAuthorities;

    public SysMetadataRelationChangeEvent(List<String> changedAuthorities) {
        super(changedAuthorities);
        this.changedAuthorities = changedAuthorities;
    }

    public List<String> getChangedAuthorities() {
        return changedAuthorities;
    }
}
