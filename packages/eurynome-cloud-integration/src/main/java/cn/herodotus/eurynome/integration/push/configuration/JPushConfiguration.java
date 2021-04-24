package cn.herodotus.eurynome.integration.push.configuration;

import cn.herodotus.eurynome.integration.push.properties.JPushProperties;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.connection.HttpProxy;
import cn.jpush.api.JPushClient;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Description: 极光推送配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 9:05
 */
@EnableConfigurationProperties(JPushProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.push.service.jpush"
})
public class JPushConfiguration {

    @Autowired
    private JPushProperties jPushProperties;

    @Bean
    @ConditionalOnMissingBean(JPushClient.class)
    public JPushClient jPushClient() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(jPushProperties.getAppKey()), "appKey为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(jPushProperties.getMasterSecret()), "masterSecret为空");
        ClientConfig clientConfig = ClientConfig.getInstance();
        HttpProxy proxy = null;
        if (jPushProperties.isUseProxy()) {
            Preconditions.checkArgument(StringUtils.isNotEmpty(jPushProperties.getProxyHost()), "代理服务器主机名或IP为空");
            Preconditions.checkArgument(jPushProperties.getProxyPort() > 1, "代理服务器主机端口无效");
            if (StringUtils.isNotEmpty(jPushProperties.getProxyUsername())) {
                proxy = new HttpProxy(StringUtils.trim(jPushProperties.getProxyHost()), jPushProperties.getProxyPort()
                        , StringUtils.trim(jPushProperties.getProxyUsername()), StringUtils.trim(jPushProperties.getProxyPassword()));
            } else {
                proxy = new HttpProxy(StringUtils.trim(jPushProperties.getProxyHost()), jPushProperties.getProxyPort());
            }
        }
        return new JPushClient(jPushProperties.getMasterSecret(), jPushProperties.getAppKey(), proxy, clientConfig);
    }
}
