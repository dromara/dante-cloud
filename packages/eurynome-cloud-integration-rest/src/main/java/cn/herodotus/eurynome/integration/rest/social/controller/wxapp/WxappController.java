package cn.herodotus.eurynome.integration.rest.social.controller.wxapp;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.integration.social.service.wxapp.WxappService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 微信小程序接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/8 17:19
 */
@Slf4j
@RestController
@RequestMapping("/integration/rest/wxapp")
@Api(tags = {"第三方集成接口", "微信小程序接口"})
public class WxappController {

	@Autowired
	private WxappService wxappService;

	@ApiOperation(value = "发送订阅消息", notes = "微信小程序发送订阅消息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "appId", required = true, value = "微信小程序appId", dataType = "String", dataTypeClass = String.class, paramType = "query"),
			@ApiImplicitParam(name = "toUser", required = true, value = "发送给指定用户的微信小程序openId", dataType = "String", dataTypeClass = String.class, paramType = "query"),
			@ApiImplicitParam(name = "subscribeId", required = true, value = "微信小程序Subscribe配置中的subscribeId", dataType = "String", dataTypeClass = String.class, paramType = "query"),
	})
	@PostMapping("/sendSubscribeMessage")
	public Result<String> sendSubscribeMessage(String appId, String toUser, String messageType) {
		boolean result = wxappService.sendSubscribeMessage(appId, toUser, messageType);
		if (result) {
			return new Result<String>().ok().message("订阅消息发送成功");
		} else {

			return new Result<String>().failed().message("订阅消息发送失败");
		}
	}
}
