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
 * File Name: DataTableResult.java
 * Author: gengwei.zheng
 * Date: 2019/11/24 下午3:48
 * LastModified: 2019/11/7 下午2:28
 */

package cn.herodotus.eurynome.component.rest.domain.datatables;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: 返回给JQuery Datatables 组件使用的结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/24 15:48
 */
@Data
public class DataTableResult implements Serializable {

    private int pageNumber;
    private int pageSize;
    private String sEcho;
    private int iDisplayStart = 0;
    private int iDisplayLength = 0;
    private String jsonString;
    private long total;

    public DataTableResult(String sEcho, int iDisplayStart, int iDisplayLength, String jsonString) {
        this.sEcho = sEcho;
        this.iDisplayStart = iDisplayStart;
        this.iDisplayLength = iDisplayLength;
        this.pageNumber = this.iDisplayStart / this.iDisplayLength;
        this.pageSize = this.iDisplayLength;
        this.jsonString = jsonString;
    }

}
