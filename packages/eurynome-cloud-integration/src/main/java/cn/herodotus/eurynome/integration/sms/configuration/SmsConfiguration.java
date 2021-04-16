package cn.herodotus.eurynome.integration.sms.configuration;

import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 阿里云短信配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 16:29
 */
@Slf4j
@EnableConfigurationProperties(AliyunProperties.class)
@ComponentScan(basePackages = {
		"cn.herodotus.eurynome.integration.sms.service.aliyun"
})
public class SmsConfiguration {

	@PostConstruct
	public void init() {
		log.info("[Braineex] |- Bean [Aliyun Sms] Auto Configure.");
	}

	@Autowired
	private AliyunProperties aliyunProperties;

	@Bean
	@ConditionalOnMissingBean(IAcsClient.class)
	public IAcsClient iAcsClient() {
		IClientProfile iClientProfile = DefaultProfile.getProfile(aliyunProperties.getRegionId(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
		IAcsClient iAcsClient = new DefaultAcsClient(iClientProfile);
		log.debug("[Eurynome] |- Bean [iAcsClient] Auto Configure.");
		return iAcsClient;
	}
}
