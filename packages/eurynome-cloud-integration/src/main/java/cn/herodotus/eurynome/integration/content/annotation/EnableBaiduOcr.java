package cn.herodotus.eurynome.integration.content.annotation;

/**
 * <p>Description: 开启百度文字识别支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 13:15
 */

import cn.herodotus.eurynome.integration.content.configuration.BaiduOcrConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(BaiduOcrConfiguration.class)
public @interface EnableBaiduOcr {
}
