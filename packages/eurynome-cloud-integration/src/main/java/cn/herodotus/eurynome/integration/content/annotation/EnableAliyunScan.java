package cn.herodotus.eurynome.integration.content.annotation;

import cn.herodotus.eurynome.integration.content.configuration.AliyunScanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启阿里云Scan支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/16 16:37
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AliyunScanConfiguration.class)
public @interface EnableAliyunScan {
}
