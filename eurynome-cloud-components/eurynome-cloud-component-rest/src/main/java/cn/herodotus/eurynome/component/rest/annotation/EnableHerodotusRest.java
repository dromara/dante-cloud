package cn.herodotus.eurynome.component.rest.annotation;

import cn.herodotus.eurynome.component.rest.configuration.HerodotusFeignFallbackConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/2 11:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HerodotusFeignFallbackConfiguration.class)
public @interface EnableHerodotusRest {
}
