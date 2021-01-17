package cn.herodotus.eurynome.security.annotation;

import cn.herodotus.eurynome.crud.annotation.EnableHerodotusCrud;
import cn.herodotus.eurynome.security.configuration.SecurityConfiguration;
import cn.herodotus.eurynome.security.configuration.WebMvcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : Security Component 相关配置Enable注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/1 8:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableHerodotusCrud
@Import({SecurityConfiguration.class, WebMvcConfiguration.class})
public @interface EnableHerodotusSecurity {
}
