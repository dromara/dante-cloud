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
 * File Name: OauthApplicationsRepository.java
 * Author: gengwei.zheng
 * Date: 2021/06/29 15:58:29
 */

package cn.herodotus.eurynome.upms.logic.repository.oauth;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.eurynome.upms.logic.entity.oauth.OauthApplications;

/**
 * <p> Description : OauthApplicationRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:52
 */
public interface OauthApplicationsRepository extends BaseRepository<OauthApplications, String> {

}
