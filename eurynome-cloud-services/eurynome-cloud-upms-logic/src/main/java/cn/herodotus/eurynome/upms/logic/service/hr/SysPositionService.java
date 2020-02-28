/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-upms-logic
 * File Name: SysPositionService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午10:59
 */

package cn.herodotus.eurynome.upms.logic.service.hr;

import cn.herodotus.eurynome.upms.api.entity.hr.SysPosition;
import cn.herodotus.eurynome.upms.logic.repository.hr.SysPositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysPositionService {

    private final SysPositionRepository sysPositionRepository;

    @Autowired
    public SysPositionService(SysPositionRepository sysPositionRepository) {
        this.sysPositionRepository = sysPositionRepository;
    }

    public Page<SysPosition> findByPage(Integer pageNumber, Integer pageSize) {
        return sysPositionRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "organizationId"));
    }

    public SysPosition saveOrUpdate(SysPosition sysPosition) {
        log.debug("[Luban UPMS] |- SysPosition Service saveOrUpdate.");
        return sysPositionRepository.saveAndFlush(sysPosition);
    }

    public void delete(String positionId) {
        log.debug("[Luban UPMS] |- SysPosition Service delete.");
        sysPositionRepository.deleteById(positionId);
    }
}
