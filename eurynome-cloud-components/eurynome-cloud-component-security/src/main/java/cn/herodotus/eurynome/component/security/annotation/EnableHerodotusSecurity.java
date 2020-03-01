package cn.herodotus.eurynome.component.security.annotation;

import cn.herodotus.eurynome.component.security.properties.ApplicationProperties;
import cn.herodotus.eurynome.component.security.properties.SecurityProperities;
import cn.herodotus.eurynome.component.security.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/1 8:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties({
        ApplicationProperties.class,
        SwaggerProperties.class,
        SecurityProperities.class
})
@ComponentScan(basePackages = {
        "cn.hutool.extra.spring",
        "cn.herodotus.eurynome.component.security.configuration",
        "cn.herodotus.eurynome.component.security.authentication",
        "cn.herodotus.eurynome.component.security.exception"
})
public @interface EnableHerodotusSecurity {
}
