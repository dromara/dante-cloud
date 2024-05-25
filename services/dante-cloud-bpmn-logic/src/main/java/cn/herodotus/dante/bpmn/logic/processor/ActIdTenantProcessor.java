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

package cn.herodotus.dante.bpmn.logic.processor;

import cn.herodotus.dante.bpmn.logic.entity.ActIdTenant;
import cn.herodotus.dante.bpmn.logic.service.ActIdTenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Debezium Tenant 数据处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 16:55
 */
@Component
public class ActIdTenantProcessor extends AbstractProcessor<ActIdTenant> {

    private static final Logger log = LoggerFactory.getLogger(ActIdTenantProcessor.class);

    private final ActIdTenantService actIdTenantService;

    @Autowired
    public ActIdTenantProcessor(ActIdTenantService actIdTenantService) {
        this.actIdTenantService = actIdTenantService;
    }

    @KafkaListener(topics = {"herodotus.public.sys_department"}, groupId = "herodotus.debezium", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void received(String body) {
        log.info("[Herodotus] |- Recived Debezium event message from [sys_department], content is : [{}]", body);
        this.execute(body, ActIdTenant.class);
    }

    @Override
    public void delete(ActIdTenant entity) {
        this.actIdTenantService.deleteById(entity.getId());
    }

    @Override
    public ActIdTenant saveOrUpdate(ActIdTenant entity) {
        return this.actIdTenantService.saveAndFlush(entity);
    }
}
