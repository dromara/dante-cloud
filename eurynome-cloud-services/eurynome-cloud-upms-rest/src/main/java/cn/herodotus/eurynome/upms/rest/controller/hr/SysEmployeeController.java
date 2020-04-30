package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import cn.herodotus.eurynome.upms.logic.service.hr.SysEmployeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Api(value = "人员管理接口", tags = "用户中心服务")
public class SysEmployeeController extends BaseRestController<SysEmployee, String> {

    private final SysEmployeeService sysEmployeeService;

    @Autowired
    public SysEmployeeController(SysEmployeeService sysEmployeeService) {
        this.sysEmployeeService = sysEmployeeService;
    }

    @Override
    public BaseService<SysEmployee, String> getBaseService() {
        return this.sysEmployeeService;
    }
}
