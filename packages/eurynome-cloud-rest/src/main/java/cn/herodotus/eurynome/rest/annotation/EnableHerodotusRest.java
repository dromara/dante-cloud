package cn.herodotus.eurynome.rest.annotation;

import cn.herodotus.eurynome.rest.configuration.*;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启Herodotus REST核心注解 </p>
 * <p>
 * 目前主要功能：
 * 1.开启ApplicationProperties， RestProperties， SwaggerProperties
 * 2.启用RestTemplate配置
 * 3.启用Swagger配置
 * 4.解决Undertow启动警告问题
 *
 * @author : gengwei.zheng
 * @date : 2020/3/2 11:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        JacksonConfiguration.class,
        RestConfiguration.class,
        RestTemplateConfiguration.class,
        SwaggerConfiguration.class,
        UndertowWebServerFactoryCustomizer.class
})
public @interface EnableHerodotusRest {
}
