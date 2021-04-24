package cn.herodotus.eurynome.integration.push.annotation;

import cn.herodotus.eurynome.integration.push.configuration.JPushConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启极光推送支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 9:11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JPushConfiguration.class)
public @interface EnableJPush {
}
