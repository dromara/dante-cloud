/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.module.upms.logic.assistant.listener;

import cn.herodotus.dante.module.upms.logic.assistant.event.SysSecurityAttributeRelationChangeEvent;
import cn.herodotus.dante.module.upms.logic.entity.system.SysRole;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostLoad;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: SysRole实体数据变更监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 14:24
 */
public class SysRoleEntityListener extends AbstractRelationListener {

    private static final Logger log = LoggerFactory.getLogger(SysRoleEntityListener.class);

    private List<String> clone(SysRole sysRole) {
        if (ObjectUtils.isNotEmpty(sysRole)) {
            return this.clone(sysRole.getAuthorities());
        }
        return new ArrayList<>();
    }

    @PostLoad
    protected void postLoad(SysRole entity) {
        log.debug("[Herodotus] |- SysRoleEntityListener @PostLoad : [{}]", entity.toString());
        this.setBefore(clone(entity));
    }

    @PreUpdate
    protected void preUpdate(SysRole entity) {
        log.debug("[Herodotus] |- SysRoleEntityListener @PreUpdate active, value is : [{}]. Trigger SysSecurityAttribute relation change event.", entity.toString());
        this.setAfter(clone(entity));
    }

    @PostUpdate
    protected void postUpdate(SysRole entity) {
        log.debug("[Herodotus] |- SysRoleEntityListener @PostUpdate active, value is : [{}]. Trigger SysSecurityAttribute relation change event.", entity.toString());
        this.publishEvent(new SysSecurityAttributeRelationChangeEvent(this.getChangedItems()));
    }

    @PostRemove
    protected void postRemove(SysRole entity) {
        log.debug("[Herodotus] |- SysRoleEntityListener @PostRemove active, value is : [{}]. Trigger SysSecurityAttribute relation change event.", entity.toString());
        if (CollectionUtils.isNotEmpty(entity.getAuthorities())) {
            this.publishEvent(new SysSecurityAttributeRelationChangeEvent(clone(entity)));
        }
    }
}
