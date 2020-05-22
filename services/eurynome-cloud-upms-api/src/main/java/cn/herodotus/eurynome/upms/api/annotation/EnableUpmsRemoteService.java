package cn.herodotus.eurynome.upms.api.annotation;

import cn.herodotus.eurynome.upms.api.configuration.UpmsApiConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/2 11:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UpmsApiConfiguration.class)
@EnableFeignClients(basePackages = {
        "cn.herodotus.eurynome.upms.api.service.remote"
})
public @interface EnableUpmsRemoteService {
}
