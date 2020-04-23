package cn.herodotus.eurynome.component.rest.controller;

import cn.herodotus.eurynome.component.common.definition.AbstractController;
import cn.herodotus.eurynome.component.common.definition.AbstractDomain;
import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.domain.datatables.DataTableResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 15:28
 */
public abstract class BaseController extends AbstractController {

    protected <D extends AbstractDomain> Result<D> result(D domain) {
        Result<D> result = new Result<>();
        if (ObjectUtils.isNotEmpty(domain)) {
            return result.ok().message("操作成功！").data(domain);
        } else {
            return result.failed().message("操作失败！");
        }
    }

    protected <D extends AbstractDomain> Result<List<D>> result(List<D> domains) {
        Result<List<D>> result = new Result<>();
        if (CollectionUtils.isNotEmpty(domains)) {
            return result.ok().message("查询数据成功！").data(domains);
        } else {
            return result.failed().message("查询数据失败！");
        }
    }

    protected <D extends AbstractDomain> Result<Map<String, Object>> result(Page<D> pages) {
        Result<Map<String, Object>> result = new Result<>();
        if (ObjectUtils.isNotEmpty(pages)) {
            return result.ok().message("查询数据成功！").data(getPageInfoMap(pages));
        } else {
            return result.failed().message("查询失败！");
        }
    }

    protected <D extends AbstractDomain> Result<Map<String, Object>> result(Map<String, Object> map) {
        Result<Map<String, Object>> result = new Result<>();
        if (MapUtils.isNotEmpty(map)) {
            return result.ok().message("查询数据成功！").data(map);
        } else {
            return result.failed().message("查询失败！");
        }
    }

    protected Result<String> result(String parameter) {
        Result<String> result = new Result<>();
        if (StringUtils.isNotEmpty(parameter)) {
            return result.ok().message("操作成功！");
        } else {
            return result.failed().message("操作失败！");
        }
    }

    protected <D extends AbstractDomain> Map<String, Object> getPageInfoMap(Page<D> pages) {
        return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    protected <D extends AbstractDomain> Map<String, Object> getPageInfoMap(List<D> content, int totalPages, long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }

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
