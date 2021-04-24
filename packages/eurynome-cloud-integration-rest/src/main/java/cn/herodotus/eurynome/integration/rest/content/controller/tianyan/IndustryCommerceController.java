package cn.herodotus.eurynome.integration.rest.content.controller.tianyan;

import cn.herodotus.eurynome.integration.content.domain.tianyan.BaseInfo;
import cn.herodotus.eurynome.integration.content.domain.tianyan.Response;
import cn.herodotus.eurynome.integration.content.service.tianyan.IndustryCommerceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 天眼查企业工商信息接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 10:21
 */
@RestController
@RequestMapping("/integration/rest/tianyan/ic")
@Api(tags = {"第三方集成接口", "天眼查接口", "天眼查工商信息接口"})
public class IndustryCommerceController {

    @Autowired
    private IndustryCommerceService industryCommerceService;

    @ApiOperation(value = "企业基本信息", notes = "查询企业基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", required = true, value = "搜索关键字（公司名称、公司id、注册号或社会统一信用代码）", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
            @ApiResponse(code = 300000, message = "无数据"),
            @ApiResponse(code = 300001, message = "请求失败"),
            @ApiResponse(code = 300002, message = "账号失效"),
            @ApiResponse(code = 300003, message = "账号过期"),
            @ApiResponse(code = 300004, message = "访问频率过快"),
            @ApiResponse(code = 300005, message = "无权限访问此api"),
            @ApiResponse(code = 300006, message = "余额不足"),
            @ApiResponse(code = 300007, message = "剩余次数不足"),
            @ApiResponse(code = 300008, message = "缺少必要参数"),
            @ApiResponse(code = 300009, message = "账号信息有误"),
            @ApiResponse(code = 300010, message = "URL不存在"),
            @ApiResponse(code = 300011, message = "此IP无权限访问此api"),
            @ApiResponse(code = 300012, message = "报告生成中")
    })
    @GetMapping("/baseinfo")
    public Response<BaseInfo> getBaseInfo(String keyword) {
        return industryCommerceService.getBaseInfo(keyword);
    }
}
