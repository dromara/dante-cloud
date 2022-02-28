/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.bpmn.logic.processor;

import cn.herodotus.eurynome.bpmn.logic.entity.ActIdUser;
import cn.herodotus.eurynome.bpmn.logic.service.ActIdUserService;
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

    @KafkaListener(topics = {"herodotus.public.sys_employee"}, groupId = "herodotus.debezium", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void received(String body) {
        log.info("[Herodotus] |- Recived Debezium event message from [sys_employee], content is : [{}]", body);
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
