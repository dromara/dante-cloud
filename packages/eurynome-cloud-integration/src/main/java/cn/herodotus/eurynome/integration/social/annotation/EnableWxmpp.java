package cn.herodotus.eurynome.integration.social.annotation;

import cn.herodotus.eurynome.integration.social.configuration.WxmppConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 仅开启微信公众号 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/7 14:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WxmppConfiguration.class)
public @interface EnableWxmpp {
}
