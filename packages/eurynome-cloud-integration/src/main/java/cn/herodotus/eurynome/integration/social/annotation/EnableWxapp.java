package cn.herodotus.eurynome.integration.social.annotation;

import cn.herodotus.eurynome.integration.social.configuration.WxappConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 仅开启微信小程序 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/29 15:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WxappConfiguration.class)
public @interface EnableWxapp {
}
