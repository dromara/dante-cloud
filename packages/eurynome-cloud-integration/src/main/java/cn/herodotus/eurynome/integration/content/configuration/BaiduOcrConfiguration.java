package cn.herodotus.eurynome.integration.content.configuration;

import cn.herodotus.eurynome.integration.content.properties.BaiduOcrProperties;
import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 百度文字识别配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 13:13
 */
@Slf4j
@EnableConfigurationProperties(BaiduOcrProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.content.service.baidu"
})
public class BaiduOcrConfiguration {

	@Autowired
	private BaiduOcrProperties baiduOcrProperties;

	@PostConstruct
	public void init() {
		log.debug("[Eurynome] |- Bean [Baidu OCR] Auto Configure.");
	}

	@Bean
	public AipOcr aipOcr() {
		AipOcr client = new AipOcr(baiduOcrProperties.getAppId(), baiduOcrProperties.getAppKey(), baiduOcrProperties.getSecretKey());
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(baiduOcrProperties.getConnectionTimeout());
		client.setSocketTimeoutInMillis(baiduOcrProperties.getSocketTimeout());

		log.debug("[Eurynome] |- Bean [Baidu Aip Ocr] Auto Configure.");
		return client;
	}
}
