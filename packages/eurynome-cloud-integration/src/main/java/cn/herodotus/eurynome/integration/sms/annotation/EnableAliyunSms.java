package cn.herodotus.eurynome.integration.sms.annotation;

import cn.herodotus.eurynome.integration.sms.configuration.AliyunSmsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 开启阿里云短信支持 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 10:26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AliyunSmsConfiguration.class)
public @interface EnableAliyunSms {
}
