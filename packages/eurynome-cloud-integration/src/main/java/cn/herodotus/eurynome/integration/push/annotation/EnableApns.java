package cn.herodotus.eurynome.integration.push.annotation;

import cn.herodotus.eurynome.integration.push.configuration.ApnsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启Ios Apns 支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 9:58
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ApnsConfiguration.class)
public @interface EnableApns {
}
