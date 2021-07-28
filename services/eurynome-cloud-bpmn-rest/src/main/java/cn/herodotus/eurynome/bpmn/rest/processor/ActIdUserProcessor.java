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
 * File Name: ActIdUserProcessor.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:20:20
 */

package cn.herodotus.eurynome.bpmn.rest.processor;

import cn.herodotus.eurynome.bpmn.rest.entity.ActIdUser;
import cn.herodotus.eurynome.bpmn.rest.service.ActIdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Debezium User 数据处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 16:50
 */
@Component
public class ActIdUserProcessor extends AbstractProcessor<ActIdUser> {

    private static final Logger log = LoggerFactory.getLogger(ActIdUserProcessor.class);

    private final ActIdUserService actIdUserService;

    @Autowired
    public ActIdUserProcessor(ActIdUserService actIdUserService) {
        this.actIdUserService = actIdUserService;
    }

    @KafkaListener(topics = {"herodotus.public.sys_employee"}, groupId = "herodotus.debezium")
    public void received(String body) {
        log.info("[Eurynome] |- Recived Debezium event message from [sys_employee], content is : [{}]", body);
        this.execute(body);
    }

    @Override
    public void delete(ActIdUser entity) {
        actIdUserService.deleteById(entity.getId());
    }

    @Override
    public ActIdUser saveOrUpdate(ActIdUser entity) {
        return actIdUserService.saveOrUpdate(entity);
    }
}
