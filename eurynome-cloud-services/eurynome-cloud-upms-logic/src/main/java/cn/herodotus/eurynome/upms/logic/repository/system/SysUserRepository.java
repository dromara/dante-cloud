package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;

/**
 * <p>Description: SysUserRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/8 16:14
 */
public interface SysUserRepository extends BaseRepository<SysUser, String> {

    /**
     * 根据ID查找SysUser
     *
     * @param userId 用户ID
     * @return SysUser
     */
    SysUser findByUserId(String userId);

    /**
     * 根据用户名查找SysUser
     *
     * @param userName 用户名
     * @return SysUser
     */
    SysUser findByUserName(String userName);
}
