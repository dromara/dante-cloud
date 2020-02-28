package cn.herodotus.eurynome.upms.api.feign.fallback;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.domain.HerodotusApplication;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.api.feign.service.ISysApplicationService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysApplicationServiceFallback implements ISysApplicationService {

    @Setter
    private Throwable cause;

    @Override
    public Result<HerodotusApplication> findByClientId(String clientId) {
        log.error("[Luban] |- SysApplicationService findByClientId Invoke Failed!", cause);
        return new Result<HerodotusApplication>().failed().error(cause);
    }
}
