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
 * File Name: DataTableUtils.java
 * Author: gengwei.zheng
 * Date: 2019/11/24 下午3:48
 * LastModified: 2019/11/7 下午2:28
 */

package cn.herodotus.eurynome.component.rest.domain.datatables;

import java.util.List;

/**
 * <p>Description: JQuery Datatable组件使用的工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/24 15:49
 */
public class DataTableUtils {

    public static final String ECHO = "sEcho";
    public static final String DISPLAY_START = "iDisplayStart";
    public static final String DISPLAY_LENGTH = "iDisplayLength";
    public static final String QUERY_JSON = "queryJson";
    public static final String DATA = "data";

    public static DataTableResult parseDataTableParameter(List<DataTableParameter> params) {

        String sEcho = null;
        String jsonString = null;
        int iDisplayStart = 0;
        int iDisplayLength = 0;
        for (DataTableParameter param : params) {
            if (param.getName().equals(ECHO)) {
                sEcho = param.getValue().toString();
            }
            if (param.getName().equals(DISPLAY_START)) {
                iDisplayStart = (int) param.getValue();
            }
            if (param.getName().equals(DISPLAY_LENGTH)) {
                iDisplayLength = (int) param.getValue();
            }
            if (param.getName().equals(QUERY_JSON)) {
                jsonString = param.getValue().toString();
            }
        }

        return new DataTableResult(sEcho, iDisplayStart, iDisplayLength, jsonString);
    }
}
