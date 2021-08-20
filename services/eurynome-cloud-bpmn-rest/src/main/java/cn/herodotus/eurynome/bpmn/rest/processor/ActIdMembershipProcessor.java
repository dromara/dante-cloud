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
 * File Name: ActIdMembershipProcessor.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:20:20
 */

package cn.herodotus.eurynome.bpmn.rest.processor;

import cn.herodotus.eurynome.bpmn.rest.entity.ActIdMembership;
import cn.herodotus.eurynome.bpmn.rest.service.ActIdMembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Debezium Membership 数据处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 16:57
 */
@Component
public class ActIdMembershipProcessor extends AbstractProcessor<ActIdMembership> {

    private static final Logger log = LoggerFactory.getLogger(ActIdMembershipProcessor.class);

    private final ActIdMembershipService actIdMembershipService;

    @Autowired
    public ActIdMembershipProcessor(ActIdMembershipService actIdMembershipService) {
        this.actIdMembershipService = actIdMembershipService;
    }

    @KafkaListener(topics = {"herodotus.public.sys_employee_department"}, groupId = "herodotus.debezium", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void received(String body) {
        log.info("[Eurynome] |- Recived Debezium event message from [sys_employee_department], content is : [{}]", body);
        this.execute(body);
    }

    @Override
    public void delete(ActIdMembership entity) {
        actIdMembershipService.deleteByUserIdAndGroupId(entity.getUserId(), entity.getGroupId());
    }

    @Override
    public ActIdMembership saveOrUpdate(ActIdMembership entity) {
        return this.actIdMembershipService.saveOrUpdate(entity);
    }
}
