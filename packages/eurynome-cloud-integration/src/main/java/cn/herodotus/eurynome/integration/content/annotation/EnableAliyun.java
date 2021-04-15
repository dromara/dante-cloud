package cn.herodotus.eurynome.integration.content.annotation;

import cn.herodotus.eurynome.integration.content.configuration.AliyunConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启阿里云支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 15:47
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AliyunConfiguration.class)
public @interface EnableAliyun {
}
