package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.component.security.domain.HerodotusApplication;
import cn.herodotus.eurynome.component.security.domain.HerodotusClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.system.SysApplication;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.api.service.fegin.OauthClientDetailFeignService;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthClientDetailsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> Description : OauthClientDetailsController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/20 11:48
 */
@Api(value = "OauthClientDetails接口", tags = "用户中心服务")
@RestController
public class OauthClientDetailsController extends BaseController implements OauthClientDetailFeignService {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;


    @Override
    @ApiOperation(value = "获取终端信息", notes = "通过clientId获取封装后的终端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", required = true, value = "终端ID")
    })
    public Result<HerodotusClientDetails> findByClientId(String clientId) {

        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(clientId);

        Map<String, Object> additionalInformationMap = new HashMap<>(8);
        if (StringUtils.isNotEmpty(oauthClientDetails.getAdditionalInformation())) {
            additionalInformationMap = JSON.parseObject(oauthClientDetails.getAdditionalInformation(), new TypeReference<Map<String, Object>>() {
            });
        }

        HerodotusClientDetails herodotusClientDetails = UpmsHelper.convertOauthClientDetailsToHerodotusClientDetails(oauthClientDetails, additionalInformationMap);

        return result(herodotusClientDetails);

    }
}
