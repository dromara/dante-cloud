package cn.herodotus.eurynome.upms.api.feign.factory;

import cn.herodotus.eurynome.upms.api.feign.fallback.SysApplicationServiceFallback;
import cn.herodotus.eurynome.upms.api.service.fegin.SysApplicationFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SysApplicationServiceFallbackFactory implements FallbackFactory<SysApplicationFeignService> {

    @Override
    public SysApplicationFeignService create(Throwable throwable) {
        SysApplicationServiceFallback sysApplicationServiceFallback = new SysApplicationServiceFallback();
        sysApplicationServiceFallback.setCause(throwable);
        return sysApplicationServiceFallback;
    }
}
