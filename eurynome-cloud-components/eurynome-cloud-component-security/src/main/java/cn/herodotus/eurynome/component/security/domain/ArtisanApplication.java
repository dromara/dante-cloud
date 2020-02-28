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
 * Module Name: luban-cloud-component-security
 * File Name: OauthApplication.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 下午2:59
 * LastModified: 2019/11/18 下午12:11
 */

package cn.herodotus.eurynome.component.security.domain;

import cn.herodotus.eurynome.component.common.domain.AbstractDomain;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author gengwei.zheng
 */
@ApiModel(value = "ArtisanApplication", description = "终端信息")
public class ArtisanApplication extends AbstractDomain {

    private ArtisanClientDetails artisanClientDetails;

    private List<ArtisanAuthority> artisanAuthorities;

    public ArtisanClientDetails getArtisanClientDetails() {
        return artisanClientDetails;
    }

    public void setArtisanClientDetails(ArtisanClientDetails artisanClientDetails) {
        this.artisanClientDetails = artisanClientDetails;
    }

    public List<ArtisanAuthority> getArtisanAuthorities() {
        return artisanAuthorities;
    }

    public void setArtisanAuthorities(List<ArtisanAuthority> artisanAuthorities) {
        this.artisanAuthorities = artisanAuthorities;
    }
}
