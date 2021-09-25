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
 * File Name: SysAuthorityUUIDGenerator.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 18:32:05
 */

package cn.herodotus.eurynome.upms.api.generator;

import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * 自定义UUID生成器，使得保存实体类时可以在保留主键生成策略的情况下自定义表的主键
 *
 * @author gengwei.zheng
 */
public class SysAuthorityUUIDGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        SysAuthority sysAuthority = (SysAuthority) object;

        if (StringUtils.isEmpty(sysAuthority.getAuthorityId())) {
            return super.generate(session, object);
        } else {
            return sysAuthority.getAuthorityId();
        }
    }
}
