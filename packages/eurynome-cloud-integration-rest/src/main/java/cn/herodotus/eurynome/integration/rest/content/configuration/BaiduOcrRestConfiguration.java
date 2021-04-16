package cn.herodotus.eurynome.integration.rest.content.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 百度OCR REST 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:12
 */
@Slf4j
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.rest.content.controller.baidu"
})
public class BaiduOcrRestConfiguration {

	@PostConstruct
	public void init() {
		log.info("[Eurynome] |- Bean [Baidu OCR Rest] Auto Configure.");
	}
}
