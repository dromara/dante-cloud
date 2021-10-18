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
 * Module Name: eurynome-cloud-assistant
 * File Name: ConvertUtils.java
 * Author: gengwei.zheng
 * Date: 2021/10/17 22:56:17
 */

package cn.herodotus.eurynome.assistant.utils;

import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.assistant.enums.ProtocolType;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 转换工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/6/13 16:54
 */
public class ConvertUtils {

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address             ip地址加端口号，格式：ip:port
     * @param protocolType        http协议类型 {@link ProtocolType}
     * @param endWithForwardSlash 是否在结尾添加“/”
     * @return http格式地址
     */
    public static String addressToUri(String address, ProtocolType protocolType, boolean endWithForwardSlash) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(protocolType.getFormat());
        stringBuilder.append(address);

        if (endWithForwardSlash) {
            if (StringUtils.endsWith(address, SymbolConstants.FORWARD_SLASH)) {
                stringBuilder.append(address);
            } else {
                stringBuilder.append(address);
                stringBuilder.append(SymbolConstants.FORWARD_SLASH);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address             ip地址加端口号，格式：ip:port
     * @param endWithForwardSlash 是否在结尾添加“/”
     * @return http格式地址
     */
    public static String addressToUri(String address, boolean endWithForwardSlash) {
        return addressToUri(address, ProtocolType.HTTP, endWithForwardSlash);
    }

    /**
     * 将IP地址加端口号，转换为http地址。
     *
     * @param address ip地址加端口号，格式：ip:port
     * @return http格式地址
     */
    public static String addressToUri(String address) {
        return addressToUri(address, false);
    }
}
