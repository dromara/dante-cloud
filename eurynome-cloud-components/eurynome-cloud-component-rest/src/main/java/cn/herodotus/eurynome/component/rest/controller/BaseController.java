package cn.herodotus.eurynome.component.rest.controller;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.data.base.entity.AbstractEntity;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Map;

/**
 * <p> Description : BaseController 单独提取出一些公共方法，是为了解决某些支持feign的controller，requestMapping 不方便统一编写的问题。 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 11:00
 */
public abstract class BaseController<E extends AbstractEntity, ID extends Serializable> extends AbstractController {

    public abstract BaseService<E, ID> getBaseService();

    public Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getBaseService().findByPage(pageNumber, pageSize);
        return result(pages);
    }

    public Result<E> saveOrUpdate(E domain) {
        E savedDomain = getBaseService().saveOrUpdate(domain);
        return result(savedDomain);
    }

    public Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getBaseService().deleteById(id);
        return result;
    }
}
