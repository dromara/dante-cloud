package cn.herodotus.eurynome.integration.social.annotation;

import cn.herodotus.eurynome.integration.social.configuration.EasemobConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 仅开启环信配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 15:28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EasemobConfiguration.class)
public @interface EnableEasemob {
}
