package cn.herodotus.eurynome.component.data.annotation;

import cn.herodotus.eurynome.component.data.configuration.DataComponentConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/28 10:33
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DataComponentConfiguration.class)
public @interface EnableHerodotusData {
}
