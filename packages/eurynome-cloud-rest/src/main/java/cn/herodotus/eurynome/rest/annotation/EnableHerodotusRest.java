package cn.herodotus.eurynome.rest.annotation;

import cn.herodotus.eurynome.rest.configuration.*;
import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
import cn.herodotus.eurynome.rest.properties.SwaggerProperties;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : Rest Component 相关配置Enable注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/2 11:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties({
        ApplicationProperties.class,
        RestProperties.class,
        SwaggerProperties.class
})
@EnableCreateCacheAnnotation
@Import({RestConfiguration.class,
        RestTemplateConfiguration.class,
        FeignConfiguration.class,
        SwaggerConfiguration.class,
        UndertowWebServerFactoryCustomizer.class
})
public @interface EnableHerodotusRest {
}
