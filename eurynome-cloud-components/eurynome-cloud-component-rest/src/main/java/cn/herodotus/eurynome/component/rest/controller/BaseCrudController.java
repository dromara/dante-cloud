package cn.herodotus.eurynome.component.rest.controller;

import cn.herodotus.eurynome.component.common.definition.AbstractDomain;
import cn.herodotus.eurynome.component.common.domain.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 15:28
 */
public abstract class BaseCrudController extends AbstractController {

    protected <D extends AbstractDomain> Result<D> result(D domain) {
        Result<D> result = new Result<>();
        if (ObjectUtils.isNotEmpty(domain)) {
            return result.ok().message("操作成功！").data(domain);
        } else {
            return result.failed().message("操作失败！");
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

    protected Result<String> result(String parameter) {
        Result<String> result = new Result<>();
        if (StringUtils.isNotEmpty(parameter)) {
            return result.ok().message("操作成功！");
        } else {
            return result.failed().message("操作失败！");
        }
    }

    protected <D extends AbstractDomain> Map<String, Object> getPageInfoMap(Page<D> pages) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", pages.getContent());
        result.put("totalPages", pages.getTotalPages());
        result.put("totalElements", pages.getTotalElements());
        return result;
    }
}
