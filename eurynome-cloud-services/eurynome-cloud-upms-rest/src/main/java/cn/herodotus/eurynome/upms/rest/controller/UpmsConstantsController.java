package cn.herodotus.eurynome.upms.rest.controller;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/constants")
public class UpmsConstantsController {

    @ApiOperation(value = "获取服务使用常量", notes = "获取服务涉及的常量以及信息")
    @GetMapping(value = "/enums")
    public Result findAllEnums() {
        Map<String, Object> allEnums =  UpmsConstants.getAllEnums();
        if (MapUtils.isNotEmpty(allEnums)) {
            return Result.ok().data(allEnums);
        } else {
            return Result.failed();
        }
    }
}
