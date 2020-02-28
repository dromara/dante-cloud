package cn.herodotus.eurynome.upms.api.feign.fallback;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.api.feign.service.ISysUserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * <p>Description: TODO </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:07
 */
@Slf4j
public class SysUserServiceFallback implements ISysUserService {

    @Setter
    private Throwable cause;

    @Override
    public Result<SysUser> findByUsername(String username) {
        log.error("[Luban] |- SysUserService findByUsername Invoke Failed!", cause);
        return Result.failed().data(cause);
    }

    @Override
    public Result<SysUser> findById(String userId) {
        log.error("[Luban] |- SysUserService findByUserId Invoke Failed!", cause);
        return Result.failed().data(cause);
    }
}
