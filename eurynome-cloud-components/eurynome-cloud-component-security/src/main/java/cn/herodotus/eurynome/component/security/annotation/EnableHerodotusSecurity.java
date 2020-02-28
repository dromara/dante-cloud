package cn.herodotus.eurynome.component.security.annotation;

import cn.herodotus.eurynome.component.data.configuration.DataComponentConfiguration;
import cn.herodotus.eurynome.component.security.properties.ApplicationProperties;
import cn.herodotus.eurynome.component.security.properties.SecurityProperities;
import cn.herodotus.eurynome.component.security.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/28 11:00
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DataComponentConfiguration.class)
@EnableConfigurationProperties({ApplicationProperties.class, SwaggerProperties.class, SecurityProperities.class})
public @interface EnableHerodotusSecurity {
}
