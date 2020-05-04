package cn.herodotus.eurynome.component.management.annotation;

import cn.herodotus.eurynome.component.management.configuration.HerodotusManagementConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : Management Component 相关配置Enable注解 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 10:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HerodotusManagementConfiguration.class)
public @interface EnableHerodotusManagement {
}
