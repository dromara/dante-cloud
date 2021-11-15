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
 * File Name: ActIdMembershipService.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:20:20
 */

package cn.herodotus.eurynome.bpmn.rest.service;

import cn.herodotus.eurynome.bpmn.rest.entity.ActIdMembership;
import cn.herodotus.eurynome.bpmn.rest.repository.ActIdMembershipRepository;
import cn.herodotus.eurynome.data.base.service.WriteableService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
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

    public void deleteByUserIdAndGroupId (String userId, String groupId) {
        this.actIdMembershipRepository.deleteByUserIdAndGroupId(userId, groupId);
    }
}
