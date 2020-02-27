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
 * File Name: BaseRelationshipEntity.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午5:51
 * LastModified: 2019/11/8 下午4:13
 */

package cn.herodotus.eurynome.component.data.base.entity;

import cn.herodotus.eurynome.component.common.definitions.AbstractDomain;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * <p>Description: 手动多对多配置的中间关系表实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/28 7:31
 */
@MappedSuperclass
public abstract class BaseRelationshipEntity extends AbstractDomain {

    @Column(name = "expire_time", updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date expireTime;


    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
