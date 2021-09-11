package cn.herodotus.eurynome.upms.rest.controller.development;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.rest.base.controller.BaseWriteableRestController;
import cn.herodotus.eurynome.rest.base.service.WriteableService;
import cn.herodotus.eurynome.upms.api.entity.development.Supplier;
import cn.herodotus.eurynome.upms.logic.service.development.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "开发团队/厂商管理接口")
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

    @Operation(summary = "获取全部厂商数据", description = "查询全部的厂商数据，用作列表选择根据目前观测该类数据不会太多，如果过多就需要修改查询方法和方式")
    @GetMapping("/list")
    @Override
    public Result<List<Supplier>> findAll() {
        return super.findAll();
    }
}
