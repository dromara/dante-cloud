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
 * File Name: SysUserRepository.java
 * Author: gengwei.zheng
 * Date: 2021/07/14 21:19:14
 */

package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * <p>Description: SysUserRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/8 16:14
 */
public interface SysUserRepository extends BaseRepository<SysUser, String> {
    /**
     * 根据用户名查找SysUser
     *
     * @param userName 用户名
     * @return SysUser
     */
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    SysUser findByUserName(String userName);

    /**
     * 根据用户ID查找用户
     *
     * @param userId 用户ID
     * @return {@link SysUser}
     */
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    SysUser findByUserId(String userId);
}
