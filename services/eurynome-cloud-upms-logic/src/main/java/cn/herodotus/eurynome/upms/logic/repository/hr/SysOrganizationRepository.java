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
 * File Name: SysOrganizationRepository.java
 * Author: gengwei.zheng
 * Date: 2021/09/25 10:40:25
 */

package cn.herodotus.eurynome.upms.logic.repository.hr;

import cn.herodotus.eurynome.assistant.enums.OrganizationCategory;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOrganization;

import java.util.List;

/**
 * <p>Description: 单位管理Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:37
 */
public interface SysOrganizationRepository extends BaseRepository<SysOrganization, String> {

    /**
     * 根据组织分类查询组织
     *
     * @param category 组织分类 {@link OrganizationCategory}
     * @return 组织列表
     */
    List<SysOrganization> findByCategory(OrganizationCategory category);
}
