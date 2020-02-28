package cn.herodotus.eurynome.platform.uaa.service.feign;

import cn.herodotus.eurynome.upms.api.constants.ServiceNameConstants;
import cn.herodotus.eurynome.upms.api.feign.factory.SysUserServiceFallbackFactory;
import cn.herodotus.eurynome.upms.api.feign.service.ISysUserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 该Feign Client的配置类，注意：
 * 1. 该类可以独立出去；
 * 2. 该类上也可添加@Configuration声明是一个配置类；
 * 配置类上也可添加@Configuration注解，声明这是一个配置类；
 * 但此时千万别将该放置在主应用程序上下文@ComponentScan所扫描的包中，
 * 否则，该配置将会被所有Feign Client共享，无法实现细粒度配置！
 * 个人建议：像我一样，不加@Configuration注解
 *
 * @author gengwei.zheng
 */

@FeignClient(value = ServiceNameConstants.UPMS_SERVICE, fallbackFactory = SysUserServiceFallbackFactory.class)
public interface SysUserRemoteService extends ISysUserService {
}
