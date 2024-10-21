/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.bpmn.logic.generator;

import cn.herodotus.engine.data.core.identifier.AbstractUuidGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.bpmn.logic.entity.ActIdTenantMember;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;

/**
 * <p>Description: Camunda 租户成员 UUID 生成器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 13:09
 */
public class ActIdTenantMemberUuidGeneratorType extends AbstractUuidGenerator {

    public ActIdTenantMemberUuidGeneratorType(ActIdTenantMemberUuidGenerator config, Member idMember, CustomIdGeneratorCreationContext creationContext) {
        super(idMember);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
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
