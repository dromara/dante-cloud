/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
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
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.bpmn.logic.service;

import cn.herodotus.dante.bpmn.logic.entity.ActIdTenant;
import cn.herodotus.dante.bpmn.logic.repository.ActIdTenantRepository;
import cn.herodotus.stirrup.data.crud.repository.BaseJpaRepository;
import cn.herodotus.stirrup.data.crud.service.JpaWriteableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Camunda 租户 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 13:30
 */
@Service
public class ActIdTenantService implements JpaWriteableService<ActIdTenant, String> {

    private final ActIdTenantRepository actIdTenantRepository;

    @Autowired
    public ActIdTenantService(ActIdTenantRepository actIdTenantRepository) {
        this.actIdTenantRepository = actIdTenantRepository;
    }

    @Override
    public BaseJpaRepository<ActIdTenant, String> getRepository() {
        return this.actIdTenantRepository;
    }
}
