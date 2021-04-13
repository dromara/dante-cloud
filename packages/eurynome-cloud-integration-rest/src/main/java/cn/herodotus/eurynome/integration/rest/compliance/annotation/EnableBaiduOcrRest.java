package cn.herodotus.eurynome.integration.rest.compliance.annotation;

import cn.herodotus.eurynome.integration.compliance.annotation.EnableBaiduOcr;
import cn.herodotus.eurynome.integration.rest.compliance.configuration.BaiduOcrRestConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启百度OCR REST支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableBaiduOcr
@Import(BaiduOcrRestConfiguration.class)
public @interface EnableBaiduOcrRest {
}
