package cn.herodotus.eurynome.upms.rest.controller.oauth;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.rest.controller.BaseController;
import cn.herodotus.eurynome.component.security.oauth2.provider.HerodotusClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.api.service.fegin.OauthClientDetailsFeignService;
import cn.herodotus.eurynome.upms.logic.service.oauth.OauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Description : OauthClientDetailsController </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/20 11:48
 */
@Api(tags = {"用户中心服务", "Oauth客户端详情接口"})
@RestController
@Transactional(rollbackFor = Exception.class)
public class OauthClientDetailsController extends BaseController<OauthClientDetails, String> implements OauthClientDetailsFeignService {

    private final OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    public OauthClientDetailsController(OauthClientDetailsService oauthClientDetailsService) {
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @Override
    public BaseService<OauthClientDetails, String> getBaseService() {
        return oauthClientDetailsService;
    }

    @Override
    @ApiOperation(value = "获取终端信息", notes = "通过clientId获取封装后的终端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", required = true, value = "终端ID")
    })
    public Result<HerodotusClientDetails> findByClientId(String clientId) {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(clientId);
        HerodotusClientDetails herodotusClientDetails = UpmsHelper.convertOauthClientDetailsToHerodotusClientDetails(oauthClientDetails);
        return result(herodotusClientDetails);
    }

    @ApiOperation(value = "获取ClientDetails分页数据", notes = "通过pageNumber和pageSize获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", required = true, value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页显示数据条目")
    })
    @GetMapping("/oauth/herodotus_client_details")
    @Override
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        Page<OauthClientDetails> pages = oauthClientDetailsService.findByPage(pageNumber, pageSize);
        if (ObjectUtils.isNotEmpty(pages) && CollectionUtils.isNotEmpty(pages.getContent())) {
            List<HerodotusClientDetails> herodotusClientDetails = pages.getContent().stream().map(UpmsHelper::convertOauthClientDetailsToHerodotusClientDetails).collect(Collectors.toList());
            return result(getPageInfoMap(herodotusClientDetails, pages.getTotalPages(), pages.getTotalElements()));
        }

        return new Result<Map<String, Object>>().failed().message("查询数据失败！");
    }

    @ApiOperation(value = "更新ClientDetails", notes = "接收JSON数据，转换为OauthClientDetails实体，进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oauthClientDetails", required = true, value = "可转换为OauthClientDetails实体的json数据", paramType = "JSON")
    })
    @PostMapping("/oauth/client_details")
    public Result<OauthClientDetails> saveOrUpdate(@RequestBody HerodotusClientDetails domain) {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.saveOrUpdate(UpmsHelper.convertHerodotusClientDetailsToOauthClientDetails(domain));
        return result(oauthClientDetails);
    }

    @ApiOperation(value = "删除ClientDetails", notes = "根据clientId删除ClientDetails，以及相关联的关系数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", required = true, value = "clientId", paramType = "JSON")
    })
    @DeleteMapping("/oauth/client_details")
    @Override
    public Result<String> delete(@RequestBody String clientId) {
        return super.delete(clientId);
    }
}
