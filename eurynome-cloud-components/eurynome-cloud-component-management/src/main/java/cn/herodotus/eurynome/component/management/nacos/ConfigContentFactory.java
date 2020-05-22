package cn.herodotus.eurynome.component.management.nacos;

import cn.herodotus.eurynome.common.utils.JacksonYamlUtils;
import cn.herodotus.eurynome.component.management.yaml.domain.OauthPropertyContent;

/**
 * <p> Description : 根据实体创建配置文件的工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 16:17
 */
public class ConfigContentFactory {

    public static String createOauthProperty(String clientId, String clientSecret) {
        OauthPropertyContent oauthPropertyContent = new OauthPropertyContent();
        oauthPropertyContent.getSecurity().getOauth2().getClient().setClientId(clientId);
        oauthPropertyContent.getSecurity().getOauth2().getClient().setClientSecret(clientSecret);

        return JacksonYamlUtils.writeAsString(oauthPropertyContent);
    }

}
