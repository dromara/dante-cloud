package cn.herodotus.eurynome.component.data.annotation;

import cn.herodotus.eurynome.component.data.configuration.RedisConfiguration;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.annotation.*;

/**
 * <p> Description : Data Component 相关配置Enable注解</p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/3 17:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisConfiguration.class)
@EnableJpaAuditing
@EnableCreateCacheAnnotation
public @interface EnableHerodotusData {
}
