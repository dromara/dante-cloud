/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-data
 * File Name: BaseSysEntity.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午5:51
 * LastModified: 2019/11/8 下午4:47
 */

package cn.herodotus.eurynome.component.data.base.entity;

import cn.herodotus.eurynome.component.common.enums.StatusEnum;

import javax.persistence.*;

/** 
 * <p>Description: 框架基础权限实体通用基础类</p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/25 15:05
 */
@MappedSuperclass
public abstract class BaseSysEntity extends BaseEntity {

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status = StatusEnum.ENABLE;

    /**
     * 保留数据
     * True 为不能删，False为可以删除
     */
    @Column(name = "is_reserved")
    private Boolean reserved = Boolean.FALSE;

    @Column(name = "reversion")
    @OrderBy("reversion asc")
    private Integer reversion = 0;

    /**
     * 角色描述,UI界面显示使用
     */
    @Column(name = "description", length = 512)
    private String description;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReversion() {
        return reversion;
    }

    public void setReversion(Integer reversion) {
        this.reversion = reversion;
    }
}
