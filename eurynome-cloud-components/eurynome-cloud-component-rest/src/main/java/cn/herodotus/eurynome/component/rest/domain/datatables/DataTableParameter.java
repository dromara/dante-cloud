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
 * File Name: DataTableParameter.java
 * Author: gengwei.zheng
 * Date: 2019/11/24 下午3:48
 * LastModified: 2019/11/7 下午2:28
 */

package cn.herodotus.eurynome.component.rest.domain.datatables;

import java.io.Serializable;

/**
 * <p>Description: JQuery Datatables 组件只用的参数对象封装 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/24 15:48
 */
public class DataTableParameter implements Serializable {

    private String name;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
