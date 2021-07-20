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
 * Module Name: eurynome-cloud-bpmn-rest
 * File Name: ActIdTenantMemberUUIDGenerator.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:11:20
 */

package cn.herodotus.eurynome.bpmn.rest.generator;

import cn.herodotus.eurynome.bpmn.rest.entity.ActIdTenantMember;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * <p>Description: Camunda 租户成员 UUID 生成器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 13:09
 */
public class ActIdTenantMemberUUIDGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        ActIdTenantMember actIdTenantMember = (ActIdTenantMember) object;

        if (StringUtils.isEmpty(actIdTenantMember.getId())) {
            return super.generate(session, object);
        } else {
            return actIdTenantMember.getId();
        }
    }
}
