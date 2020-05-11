package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import cn.herodotus.eurynome.upms.logic.service.hr.SysDepartmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@Api(tags = {"用户中心服务", "部门管理接口"})
public class SysDepartmentController extends BaseRestController<SysDepartment, String> {

    private final SysDepartmentService sysDepartmentService;

    @Autowired
    public SysDepartmentController(SysDepartmentService sysDepartmentService) {
        this.sysDepartmentService = sysDepartmentService;
    }

    @Override
    public BaseService<SysDepartment, String> getBaseService() {
        return this.sysDepartmentService;
    }
}
