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
 * Module Name: luban-cloud-component-common
 * File Name: AccountType.java
 * Author: gengwei.zheng
 * Date: 2019/11/8 下午4:16
 * LastModified: 2019/11/8 下午4:16
 */

package cn.herodotus.eurynome.component.common.enums;

/**
 * @author gengwei.zheng
 * 登录类型:password-密码、mobile-手机号、email-邮箱、weixin-微信、weibo-微博、qq-等等
 */

public enum AccountType {
    /**
     * enum
     */
    PASSWORD(0, "密码"),
    MOBILE(1, "手机"),
    EMAIL(2, "邮箱"),
    WEIXIN(3, "微信"),
    QQ(4, "微信");

    private Integer type;
    private String name;

    AccountType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}
