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
 * File Name: SysEmployeeRepository.java
 * Author: gengwei.zheng
 * Date: 2021/09/18 16:12:18
 */

package cn.herodotus.eurynome.module.upms.repository.hr;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.eurynome.module.upms.entity.hr.SysEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * <p>Description: 人员 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:47
 */
public interface SysEmployeeRepository extends BaseRepository<SysEmployee, String> {

    /**
     * 根据人员性名查找SysEmployee
     *
     * @param employeeName 人员姓名
     * @return {@link SysEmployee}
     */
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @EntityGraph(value = "SysEmployeeWithSysUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    SysEmployee findByEmployeeName(String employeeName);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @EntityGraph(value = "SysEmployeeWithSysUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Page<SysEmployee> findAll(Pageable pageable);
}
