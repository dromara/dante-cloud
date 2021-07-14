package cn.herodotus.eurynome.upms.rest.controller.development;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.crud.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.crud.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.development.Supplier;
import cn.herodotus.eurynome.upms.logic.service.development.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> Description : 微服务开发厂商及团队管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:35
 */
@RestController
@RequestMapping("/microservice/supplier")
@Api(tags = {"用户中心服务", "开发团队/厂商管理接口"})
public class SupplierController extends BaseWriteableRestController<Supplier, String> {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public WriteableService<Supplier, String> getWriteableService() {
        return this.supplierService;
    }

    @ApiOperation(value = "获取全部厂商数据", notes = "查询全部的厂商数据，用作列表选择根据目前观测该类数据不会太多，如果过多就需要修改查询方法和方式")
    @GetMapping("/list")
    @Override
    public Result<List<Supplier>> findAll() {
        return super.findAll();
    }
}
