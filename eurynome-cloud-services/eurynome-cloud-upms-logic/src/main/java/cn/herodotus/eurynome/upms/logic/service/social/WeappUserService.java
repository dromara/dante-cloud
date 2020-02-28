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
 * File Name: WeappUserService.java
 * Author: gengwei.zheng
 * Date: 2019/11/19 上午11:03
 * LastModified: 2019/11/19 上午10:59
 */

package cn.herodotus.eurynome.upms.logic.service.social;

import cn.herodotus.eurynome.upms.api.entity.social.WeappUser;
import cn.herodotus.eurynome.upms.logic.repository.social.WeappUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeappUserService {

    private final WeappUserRepository weappUserRepository;

    @Autowired
    public WeappUserService(WeappUserRepository weappUserRepository) {
        this.weappUserRepository = weappUserRepository;
    }

    public Page<WeappUser> findByPage(Integer pageNumber, Integer pageSize) {
        return weappUserRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "openId"));
    }

    public WeappUser saveOrUpdate(WeappUser weappUser) {
        log.debug("[Luban UPMS] |- WeappUser Service saveOrUpdate.");
        return weappUserRepository.saveAndFlush(weappUser);
    }

    public void delete(String openId) {
        log.debug("[Luban UPMS] |- WeappUser Service delete.");
        weappUserRepository.deleteById(openId);
    }
}
