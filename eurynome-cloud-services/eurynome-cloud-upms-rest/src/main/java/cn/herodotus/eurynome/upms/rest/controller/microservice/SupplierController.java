package cn.herodotus.eurynome.upms.rest.controller.microservice;

import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.microservice.Supplier;
import cn.herodotus.eurynome.upms.logic.service.microservice.SupplierService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> Description : 微服务开发厂商及团队管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:35
 */
@RestController
@RequestMapping("/microservice/supplier")
@Api(value = "厂商管理接口", tags = "用户中心服务")
public class SupplierController extends BaseRestController<Supplier, String> {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public BaseService<Supplier, String> getBaseService() {
        return this.supplierService;
    }
}
