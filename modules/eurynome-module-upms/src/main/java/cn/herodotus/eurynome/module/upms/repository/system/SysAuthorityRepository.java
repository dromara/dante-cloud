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
 * File Name: SysAuthorityRepository.java
 * Author: gengwei.zheng
 * Date: 2021/08/18 17:48:18
 */

package cn.herodotus.eurynome.module.upms.repository.system;

import cn.herodotus.engine.assistant.core.enums.AuthorityType;
import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.eurynome.module.upms.entity.system.SysAuthority;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface SysAuthorityRepository extends BaseRepository<SysAuthority, String> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType);
}
