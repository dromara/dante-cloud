package cn.herodotus.eurynome.integration.compliance.annotation;

import cn.herodotus.eurynome.integration.compliance.configuration.AliyunScanConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 15:47
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AliyunScanConfiguration.class)
public @interface EnableAliyunScan {
}
