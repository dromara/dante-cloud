package cn.herodotus.eurynome.component.rest.controller;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.data.base.entity.AbstractEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * <p> Description : BaseRestController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/29 15:28
 */
public abstract class BaseRestController<E extends AbstractEntity, ID extends Serializable> extends BaseController<E, ID> {

    @ApiOperation(value = "获取应用分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
       return super.findByPage(pageNumber, pageSize);
    }

    @ApiOperation(value = "保存或更新应用", notes = "接收JSON数据，转换为实体，进行保存或更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entity", required = true, value = "可转换为实体的json数据", paramType = "JSON")
    })
    @PostMapping
    public Result<E> saveOrUpdate(@RequestBody E domain) {
        E entity = getBaseService().saveOrUpdate(domain);
        return result(entity);
    }

    @ApiOperation(value = "删除应用", notes = "根据appKey删除应用，以及相关联的关联关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "实体ID", paramType = "JSON")
    })
    @DeleteMapping
    public Result<String> delete(@RequestBody ID id) {
        Result<String> result = result(id);
        getBaseService().deleteById(id);
        return result;
    }
}
