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

package org.dromara.dante.bpmn.logic.service;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.WriteableService;
import org.dromara.dante.bpmn.logic.entity.ActIdMembership;
import org.dromara.dante.bpmn.logic.repository.ActIdMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Camunda 组成员 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 14:32
 */
@Service
public class ActIdMembershipService implements WriteableService<ActIdMembership, String> {

    private final ActIdMembershipRepository actIdMembershipRepository;

    @Autowired
    public ActIdMembershipService(ActIdMembershipRepository actIdMembershipRepository) {
        this.actIdMembershipRepository = actIdMembershipRepository;
    }

    @Override
    public BaseRepository<ActIdMembership, String> getRepository() {
        return this.actIdMembershipRepository;
    }

    public void deleteByUserIdAndGroupId(String userId, String groupId) {
        this.actIdMembershipRepository.deleteByUserIdAndGroupId(userId, groupId);
    }
}
