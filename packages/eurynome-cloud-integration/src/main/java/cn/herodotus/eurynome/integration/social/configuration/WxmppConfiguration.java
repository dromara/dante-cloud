package cn.herodotus.eurynome.integration.social.configuration;

import cn.herodotus.eurynome.integration.social.properties.WxmppProperties;
import cn.herodotus.eurynome.integration.social.service.wxmpp.WxmppLogHandler;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 微信公众号配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/7 13:25
 */
@Slf4j
@EnableConfigurationProperties(WxmppProperties.class)
@ComponentScan(basePackages = {
		"cn.herodotus.eurynome.integration.social.service.wxmpp"
})
public class WxmppConfiguration {

	@Autowired
	private WxmppProperties wxmppProperties;
	@Autowired
	private WxmppLogHandler wxmppLogHandler;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Bean
	public WxMpService wxMpService() {
		// 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
		final List<WxmppProperties.MpConfig> configs = this.wxmppProperties.getConfigs();
		if (configs == null) {
			throw new RuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
		}

		WxMpService service = new WxMpServiceImpl();
		service.setMultiConfigStorages(configs
				.stream().map(a -> {
					WxMpDefaultConfigImpl configStorage;
					if (this.wxmppProperties.isUseRedis()) {
						final WxmppProperties.RedisConfig redisConfig = this.wxmppProperties.getRedis();
						configStorage = new WxMpRedisConfigImpl(new RedisTemplateWxRedisOps(stringRedisTemplate), a.getAppId());
					} else {
						configStorage = new WxMpDefaultConfigImpl();
					}

					configStorage.setAppId(a.getAppId());
					configStorage.setSecret(a.getSecret());
					configStorage.setToken(a.getToken());
					configStorage.setAesKey(a.getAesKey());
					return configStorage;
				}).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));

		log.info("[Herodotus] |- Bean [Weixin Micro Message Public Platform] Auto Configure.");

		return service;
	}

	@Bean
	public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
		// 记录所有事件的日志 （异步执行）
		newRouter.rule().handler(this.wxmppLogHandler).next();
		return newRouter;
	}
}
