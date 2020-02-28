package cn.herodotus.eurynome.upms.api.feign.factory;

import cn.herodotus.eurynome.upms.api.feign.fallback.SysUserServiceFallback;
import cn.herodotus.eurynome.upms.api.feign.service.ISysUserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SysUserServiceFallbackFactory implements FallbackFactory<ISysUserService> {
    @Override
    public ISysUserService create(Throwable throwable) {
        SysUserServiceFallback sysUserServiceFallback = new SysUserServiceFallback();
        sysUserServiceFallback.setCause(throwable);
        return sysUserServiceFallback;
    }
}
