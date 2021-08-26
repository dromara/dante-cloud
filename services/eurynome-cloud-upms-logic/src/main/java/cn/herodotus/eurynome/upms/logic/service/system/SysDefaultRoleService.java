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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: SysDefaultRoleService.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:53:05
 */

package cn.herodotus.eurynome.upms.logic.service.system;

import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.common.constant.enums.AccountType;
import cn.herodotus.eurynome.upms.api.entity.system.SysDefaultRole;
import cn.herodotus.eurynome.upms.logic.repository.system.SysDefaultRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: SysDefaultRoleService </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:53
 */
@Service
public class SysDefaultRoleService extends BaseLayeredService<SysDefaultRole, String> {

    private static final Logger log = LoggerFactory.getLogger(SysDefaultRoleService.class);

    private final SysDefaultRoleRepository sysDefaultRoleRepository;

    @Autowired
    public SysDefaultRoleService(SysDefaultRoleRepository sysDefaultRoleRepository) {
        this.sysDefaultRoleRepository = sysDefaultRoleRepository;
    }

    @Override
    public BaseRepository<SysDefaultRole, String> getRepository() {
        return this.sysDefaultRoleRepository;
    }

    public SysDefaultRole findByScene(AccountType scene) {
        SysDefaultRole sysDefaultRole = this.sysDefaultRoleRepository.findByScene(scene);
        log.debug("[Eurynome] |- SysDefaultRole Service findBySource.");
        return sysDefaultRole;
    }
}
