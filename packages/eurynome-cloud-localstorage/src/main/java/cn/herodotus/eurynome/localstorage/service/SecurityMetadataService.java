/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-localstorage
 * File Name: SecurityMetadataService.java
 * Author: gengwei.zheng
 * Date: 2020/5/28 下午9:26
 * LastModified: 2020/5/28 下午5:01
 */

package cn.herodotus.eurynome.localstorage.service;

import cn.herodotus.eurynome.data.datasource.annotation.DataSource;
import cn.herodotus.eurynome.localstorage.entity.SecurityMetadata;
import cn.herodotus.eurynome.localstorage.repository.SecurityMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: SecurityMetadataService </p>
 *
 * <p>Description: SecurityMetadataService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/28 12:52
 */
@Slf4j
@Service
@DataSource
public class SecurityMetadataService {

    @Autowired
    private SecurityMetadataRepository securityMetadataRepository;

    public List<SecurityMetadata> findAll() {
        return securityMetadataRepository.findAll();
    }

    public List<SecurityMetadata> saveAll(List<SecurityMetadata> securityMetadata) {
        return securityMetadataRepository.saveAll(securityMetadata);
    }

}
