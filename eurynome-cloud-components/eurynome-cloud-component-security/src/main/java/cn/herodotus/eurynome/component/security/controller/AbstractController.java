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
 * File Name: AbstractController.java
 * Author: gengwei.zheng
 * Date: 2019/11/24 下午3:45
 * LastModified: 2019/11/11 下午11:33
 */

package cn.herodotus.eurynome.component.security.controller;

import cn.herodotus.eurynome.component.common.display.datatables.DataTableResult;
import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

/** 
 * <p>Description: TODO </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/24 15:50
 */
public abstract class AbstractController {

    public enum Message {
        success, info, warning, error
    }

    public Map<String, Object> getDataTablePageMap(DataTableResult dataTableResult, long totalCount, int totalPages) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("sEcho", dataTableResult.getSEcho());
        // 总数
        result.put("iTotalRecords", totalCount);
        // 显示的行数,这个要和上面写的一样
        result.put("iTotalDisplayRecords", totalCount);
        // 多少页
        result.put("iTotalPages",totalPages);
        return result;
    }

    public Map<String, Object> getFrontPageMap(Page pages) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", pages.getContent());
        result.put("totalPages", pages.getTotalPages());
        result.put("totalElements", pages.getTotalElements());
        return result;
    }

    protected String ajaxSuccess(String message) {
        return ajaxMessage(message, Message.success.name());
    }

    protected String ajaxInfo(String message) {
        return ajaxMessage(message, Message.info.name());
    }

    protected String ajaxWarning(String message) {
        return ajaxMessage(message, Message.warning.name());
    }

    protected String ajaxError(String message) {
        return ajaxMessage(message, Message.error.name());
    }

    protected String ajaxDefault(String message) {
        return ajaxMessage(message, "default");
    }

    private String ajaxMessage(String message, Message messageType) {
        return ajaxMessage(message, messageType.name());
    }

    private String ajaxMessage(String message, String type) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", type);
        map.put("msg", message);
        return JSON.toJSONString(map);
    }
}
