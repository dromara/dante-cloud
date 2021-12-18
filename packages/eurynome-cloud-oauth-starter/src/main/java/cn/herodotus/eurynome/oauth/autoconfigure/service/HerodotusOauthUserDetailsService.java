/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-oauth-starter
 * File Name: HerodotusOauthUserDetailsService.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:11:13
 */

package cn.herodotus.eurynome.oauth.autoconfigure.service;

import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import cn.herodotus.eurynome.upms.logic.entity.system.SysUser;
import cn.herodotus.eurynome.upms.logic.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: UserDetailsService核心类 </p>
 *
 * 之前一直使用Fegin进行UserDetailsService的远程调用。现在直接改为数据库访问。主要原因是：
 * 1. 根据目前的设计，Oauth的表与系统权限相关的表是在一个库中的。因此UAA和UPMS分开是为了以后提高新能考虑，逻辑上没有必要分成两个服务。
 * 2. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，调用频繁增加一道远程调用增加消耗而已。
 * 3. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，只是UAA在使用。
 * 4. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，是各种验证权限之前必须调用的内容。
 *    一方面：使用feign的方式调用，只能采取作为白名单的方式，安全性无法保证。
 *    另一方面：会产生调用的循环。
 * 因此，最终考虑把这两个服务相关的代码，抽取至UPMS API，采用UAA直接访问数据库的方式。
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:02
 */
public class HerodotusOauthUserDetailsService implements HerodotusUserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(HerodotusOauthUserDetailsService.class);

    @Autowired
    private SysUserService sysUserService;

    @Override
    public HerodotusUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = findByUserName(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }

        log.debug("[Herodotus] |- UserDetailsService loaded user : [{}]", username);

        return UpmsHelper.convertSysUserToHerodotusUserDetails(sysUser);
    }

    private SysUser findByUserName(String userName) {
        SysUser sysUser = sysUserService.findByUserName(userName);
        log.debug("[Herodotus] |- SysUser Service findSysUserByUserName.");
        return sysUser;
    }
}
