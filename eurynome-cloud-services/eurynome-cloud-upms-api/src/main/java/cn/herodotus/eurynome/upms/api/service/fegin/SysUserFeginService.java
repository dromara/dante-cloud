package cn.herodotus.eurynome.upms.api.service.fegin;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Description:
 * 使用接口前，需要在每个服务上去写对应的调用，现在由服务提供者在对应的接口统一定义
 * 即我开发一个服务，然后我写一个对应服务的消费者调用接口，想调用我服务的，统一继承我的接口即可 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/24 15:37
 */

public interface SysUserFeginService {

    /**
     * 通过用户名获取用户
     * @param username 系统用户登录名
     * @return SysUser
     */
    @PostMapping("/user/findByUsername")
    Result<SysUser> findByUsername(@RequestParam(value = "username") String username);

    /**
     * 通过用户ID获取用户
     * @param userId 系统用户Id
     * @return
     */
    @PostMapping("/user/findById")
    Result<SysUser> findById(@RequestParam(value = "userId") String userId);
}
