package cn.herodotus.eurynome.upms.rest.controller.hr;

import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseRestController;
import cn.herodotus.eurynome.upms.api.entity.hr.SysOrganization;
import cn.herodotus.eurynome.upms.logic.service.hr.SysOrganizationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
@Api(tags = {"用户中心服务", "单位管理接口"})
public class SysOrganizationController extends BaseRestController<SysOrganization, String> {

    private final SysOrganizationService sysOrganizationService;

    @Autowired
    public SysOrganizationController(SysOrganizationService sysOrganizationService) {
        this.sysOrganizationService = sysOrganizationService;
    }

    @Override
    public BaseService<SysOrganization, String> getBaseService() {
        return this.sysOrganizationService;
    }
}
