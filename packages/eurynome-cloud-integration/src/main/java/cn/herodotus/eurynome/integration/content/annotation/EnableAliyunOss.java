package cn.herodotus.eurynome.integration.content.annotation;

import cn.herodotus.eurynome.integration.content.configuration.AliyunOssConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启阿里云OSS支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 16:36
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AliyunOssConfiguration.class)
public @interface EnableAliyunOss {
}
