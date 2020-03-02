package cn.herodotus.eurynome.upms.api.feign.fallback;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.domain.HerodotusApplication;
import cn.herodotus.eurynome.upms.api.service.fegin.SysApplicationFeignService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysApplicationServiceFallback implements SysApplicationFeignService {

    @Setter
    private Throwable cause;

    @Override
    public Result<HerodotusApplication> findByClientId(String clientId) {
        log.error("[Herodotus] |- SysApplicationService findByClientId Invoke Failed!", cause);
        return new Result<HerodotusApplication>().failed().error(cause);
    }
}
