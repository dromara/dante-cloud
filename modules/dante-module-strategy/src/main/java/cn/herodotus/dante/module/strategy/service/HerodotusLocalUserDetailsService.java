/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.dante.module.strategy.service;

import cn.herodotus.dante.module.strategy.definition.AbstractStrategyUserDetailsService;
import cn.herodotus.dante.module.upms.logic.entity.system.SysUser;
import cn.herodotus.dante.module.upms.logic.service.system.SysUserService;
import cn.herodotus.engine.assistant.core.domain.AccessPrincipal;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.oauth2.core.definition.handler.SocialAuthenticationHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: UserDetail本地直联服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/23 9:20
 */
public class HerodotusLocalUserDetailsService extends AbstractStrategyUserDetailsService {

    private final SysUserService sysUserService;
    private final SocialAuthenticationHandler socialAuthenticationHandler;

    public HerodotusLocalUserDetailsService(SysUserService sysUserService, SocialAuthenticationHandler socialAuthenticationHandler) {
        this.sysUserService = sysUserService;
        this.socialAuthenticationHandler = socialAuthenticationHandler;
    }

    @Override
    public HerodotusUser findUserDetailsByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findByUserName(userName);
        return this.convertSysUser(sysUser, userName);
    }

    @Override
    public HerodotusUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) {
        return socialAuthenticationHandler.authentication(source, accessPrincipal);
    }
}
