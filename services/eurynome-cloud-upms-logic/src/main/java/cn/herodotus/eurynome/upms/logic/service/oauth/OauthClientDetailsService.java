/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Module Name: eurynome-cloud-upms-logic
 * File Name: OauthClientDetailsService.java
 * Author: gengwei.zheng
 * Date: 2021/1/18 下午6:20
 * LastModified: 2021/1/17 下午7:20
 */

package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.rest.base.service.BaseLayeredService;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthClientDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> Description : OauthClientDetailService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:59
 */
@Service
public class OauthClientDetailsService extends BaseLayeredService<OauthClientDetails, String> {

    private static final Logger log = LoggerFactory.getLogger(OauthClientDetailsService.class);

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Override
    public BaseRepository<OauthClientDetails, String> getRepository() {
        return oauthClientDetailsRepository;
    }

    public OauthClientDetails synchronize(OauthApplications oauthApplications) {
        OauthClientDetails oauthClientDetails = findById(oauthApplications.getAppKey());
        oauthClientDetails = UpmsHelper.convertOauthApplicationsToOauthClientDetails(oauthApplications, oauthClientDetails);

        log.debug("[Eurynome] |- OauthClientDetails Service synchronize.");
        return saveOrUpdate(oauthClientDetails);
    }
}
