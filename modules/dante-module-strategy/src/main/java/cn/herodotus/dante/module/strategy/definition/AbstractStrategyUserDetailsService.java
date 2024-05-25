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

package cn.herodotus.dante.module.strategy.definition;

import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.oauth2.core.definition.strategy.StrategyUserDetailsService;
import cn.herodotus.engine.supplier.upms.logic.converter.SysUserToHerodotusUserConverter;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: 抽象StrategyUserDetailsService，提取公共方法 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/17 11:23
 */
public abstract class AbstractStrategyUserDetailsService implements StrategyUserDetailsService {

    private final Converter<SysUser, HerodotusUser> toUser = new SysUserToHerodotusUserConverter();

    protected HerodotusUser convertSysUser(SysUser sysUser, String username) throws AuthenticationException {
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }

        return toUser.convert(sysUser);
    }
}
