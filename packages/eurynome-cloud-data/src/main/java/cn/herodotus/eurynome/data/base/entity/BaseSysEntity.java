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
 * Module Name: eurynome-cloud-data
 * File Name: BaseSysEntity.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.base.entity;

import cn.herodotus.eurynome.assistant.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * <p>Description: 框架基础权限实体通用基础类</p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 15:05
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseSysEntity extends BaseEntity {

    @Schema(title =  "数据状态")
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status = StatusEnum.ENABLE;

    @Schema(title =  "是否为保留数据", description = "True 为不能删，False为可以删除")
    @Column(name = "is_reserved")
    private Boolean reserved = Boolean.FALSE;

    @Schema(title =  "版本号")
    @Column(name = "reversion")
    private Integer reversion = 0;

    /**
     * 角色描述,UI界面显示使用
     */
    @Schema(title =  "备注")
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
