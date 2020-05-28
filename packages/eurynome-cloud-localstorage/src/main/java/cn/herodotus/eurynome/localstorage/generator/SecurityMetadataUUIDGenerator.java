/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-localstorage
 * File Name: SecurityMetadataUUIDGenerator.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午12:43
 * LastModified: 2020/5/28 下午12:43
 */

package cn.herodotus.eurynome.localstorage.generator;

import cn.herodotus.eurynome.localstorage.entity.SecurityMetadata;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataUUIDGenerator </p>
 *
 * <p>Description: SecurityMetadata 自定义 UUID 生成器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 12:43
 */
public class SecurityMetadataUUIDGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        SecurityMetadata securityMetadata = (SecurityMetadata) object;

        if (StringUtils.isEmpty(securityMetadata.getMetadataId())) {
            return super.generate(session, object);
        } else {
            return securityMetadata.getMetadataId();
        }
    }
}
