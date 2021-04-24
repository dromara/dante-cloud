package cn.herodotus.eurynome.integration.content.annotation;

import cn.herodotus.eurynome.integration.content.configuration.TianyanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启天眼查支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 10:16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TianyanConfiguration.class)
public @interface EnableTianyan {
}
