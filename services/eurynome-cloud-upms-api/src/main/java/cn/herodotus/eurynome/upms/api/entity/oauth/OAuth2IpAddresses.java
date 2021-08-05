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
 * Module Name: eurynome-cloud-upms-api
 * File Name: OAuth2IpAddresses.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:02:05
 */

package cn.herodotus.eurynome.upms.api.entity.oauth;

import cn.herodotus.eurynome.data.base.entity.BaseSysEntity;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: OAuth2 管控IP地址 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:02
 */
@ApiModel(description = "OAuth2 管控IP地址")
@Entity
@Table(name = "oauth_ip_addresses", indexes = {@Index(name = "oauth_ip_addresses_id_idx", columnList = "ip_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_OAUTH_IP_ADDRESSES)
public class OAuth2IpAddresses extends BaseSysEntity {

    @ApiModelProperty(value = "IP地址ID")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ip_id", length = 64)
    private String ipId;

    @ApiModelProperty(value = "IP地址")
    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    public String getIpId() {
        return ipId;
    }

    public void setIpId(String ipId) {
        this.ipId = ipId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String getLinkedProperty() {
        return this.getIpAddress();
    }

    @Override
    public String getId() {
        return this.getIpId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ipId", ipId)
                .add("ipAddress", ipAddress)
                .toString();
    }
}
