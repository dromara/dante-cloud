package cn.herodotus.eurynome.integration.rest.content.annotation;

import cn.herodotus.eurynome.integration.content.annotation.EnableAliyunOss;
import cn.herodotus.eurynome.integration.rest.content.configuration.AliyunRestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * <p>Description: 开启阿里云REST </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 11:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAsync
@EnableAliyunOss
@Import(AliyunRestConfiguration.class)
public @interface EnableAliyunRest {
}
