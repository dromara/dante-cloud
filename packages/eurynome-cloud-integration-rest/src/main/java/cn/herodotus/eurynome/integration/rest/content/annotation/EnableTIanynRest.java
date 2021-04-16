package cn.herodotus.eurynome.integration.rest.content.annotation;

import cn.herodotus.eurynome.integration.content.annotation.EnableTianyan;
import cn.herodotus.eurynome.integration.rest.content.configuration.TianyanRestConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启天眼查Rest支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 10:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableTianyan
@Import(TianyanRestConfiguration.class)
public @interface EnableTIanynRest {
}
