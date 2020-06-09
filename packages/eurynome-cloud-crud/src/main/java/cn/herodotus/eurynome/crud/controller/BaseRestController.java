package cn.herodotus.eurynome.crud.controller;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.definition.entity.AbstractEntity;
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

    @ApiOperation(value = "分页查询数据", notes = "通过pageNumber和pageSize获取分页数据", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping
    @Override
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
       return super.findByPage(pageNumber, pageSize);
    }

    @ApiOperation(value = "保存或更新数据", notes = "接收JSON数据，转换为实体，进行保存或更新", produces = "application/json", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entity", required = true, value = "可转换为实体的json数据", paramType = "JSON")
    })
    @PostMapping
    @Override
    public Result<E> saveOrUpdate(@RequestBody E domain) {
        return super.saveOrUpdate(domain);
    }

    @ApiOperation(value = "删除数据", notes = "根据实体ID删除数据，以及相关联的关联数据", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "实体ID，@Id注解对应的实体属性")
    })
    @DeleteMapping
    @Override
    public Result<String> delete(@RequestBody ID id) {
        return super.delete(id);
    }
}
