package cn.herodotus.eurynome.upms.api.feign.factory;

import cn.herodotus.eurynome.upms.api.feign.fallback.SysUserServiceFallback;
import cn.herodotus.eurynome.upms.api.service.fegin.SysUserFeginService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SysUserServiceFallbackFactory implements FallbackFactory<SysUserFeginService> {



    @Override
    public SysUserFeginService create(Throwable throwable) {
        SysUserServiceFallback sysUserServiceFallback = new SysUserServiceFallback();
        sysUserServiceFallback.setCause(throwable);
        return sysUserServiceFallback;
    }
}
