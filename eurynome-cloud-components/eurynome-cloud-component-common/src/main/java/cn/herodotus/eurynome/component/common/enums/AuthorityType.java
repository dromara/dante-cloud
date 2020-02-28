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
 * File Name: AuthorityType.java
 * Author: gengwei.zheng
 * Date: 2019/11/25 下午3:15
 * LastModified: 2019/11/25 下午3:10
 */

package cn.herodotus.eurynome.component.common.enums;

/**
 * <p>Description: 权限资源类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 15:10
 */
public enum AuthorityType {

    API(0, "REST API"),
    MENU(1, "功能菜单"),
    PAGE(2, "Web Page"),
    WEAPP_PAGE(3, "小程序页面");

    private Integer type;
    private String description;

    AuthorityType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "AuthorityType{" +
                "type=" + type +
                ", description='" + description + '\'' +
                '}';
    }
}
