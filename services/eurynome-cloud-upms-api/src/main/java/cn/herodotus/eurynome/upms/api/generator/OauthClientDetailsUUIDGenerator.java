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
 * Module Name: eurynome-cloud-upms-api
 * File Name: OauthClientDetailsUUIDGenerator.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:32:13
 */

package cn.herodotus.eurynome.upms.api.generator;

import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * <p> Description : 使得保存实体类时可以在保留主键生成策略的情况下自定义表的主键 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/22 13:16
 */
public class OauthClientDetailsUUIDGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        OauthClientDetails oauthClientDetails = (OauthClientDetails) object;

        if (StringUtils.isEmpty(oauthClientDetails.getClientId())) {
            return super.generate(session, object);
        } else {
            return oauthClientDetails.getClientId();
        }
    }
}
