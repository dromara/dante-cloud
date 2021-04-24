package cn.herodotus.eurynome.integration.content.configuration;

import cn.herodotus.eurynome.integration.content.properties.TianyanProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 天眼查配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 16:52
 */
@Slf4j
@EnableConfigurationProperties(TianyanProperties.class)
@ComponentScan(basePackages = {
		"cn.herodotus.eurynome.integration.content.service.tianyan"
})
public class TianyanConfiguration {

	@PostConstruct
	public void init() {
		log.debug("[Eurynome] |- Bean [Tian yan cha] Auto Configure.");
	}
}
