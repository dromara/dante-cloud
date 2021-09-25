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
 * Module Name: eurynome-cloud-rest
 * File Name: ServiceProperties.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.rest.properties;

import cn.herodotus.eurynome.common.utils.ConvertUtils;
import cn.herodotus.eurynome.common.constant.magic.PropertyConstants;
import cn.herodotus.eurynome.common.constant.magic.SymbolConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 服务运行相关配置信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/6/13 16:58
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_PREFIX_MANAGEMENT_SERVICE)
public class ServiceProperties {

    /**
     * 服务端口号
     */
    private int port;
    /**
     * 服务IP地址
     */
    private String ip;
    /**
     * 服务地址，格式：ip:port
     */
    private String address;
    /**
     * 服务Url，格式：http://ip:port
     */
    private String url;
    /**
     * 应用名称，与spring.application.name一致
     */
    private String applicationName;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        if (StringUtils.isBlank(this.address)) {
            if (StringUtils.isNotBlank(this.ip) && this.port != 0) {
                return this.ip + SymbolConstants.COLON + this.port;
            }
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        if (StringUtils.isBlank(this.url)) {
            String address = this.getAddress();
            if (StringUtils.isNotBlank(address)) {
                return ConvertUtils.addressToUri(address);
            }
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("port", port)
                .add("ip", ip)
                .add("address", address)
                .add("url", url)
                .add("applicationName", applicationName)
                .toString();
    }
}
