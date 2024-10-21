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

package org.dromara.dante.module.strategy.feign;

import org.dromara.dante.module.common.ServiceNameConstants;
import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>Description: 远程权限服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/1 18:55
 */
@FeignClient(contextId = "remoteAuthorityDetailsService", value = ServiceNameConstants.SERVICE_NAME_UPMS)
public interface RemoteAuthorityDetailsService {

    @GetMapping("/security/authority/all")
    Result<List<SysPermission>> findAll();
}
