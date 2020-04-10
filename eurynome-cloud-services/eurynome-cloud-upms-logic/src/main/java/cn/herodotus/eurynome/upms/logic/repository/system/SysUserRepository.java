package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Description: SysUserRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/8 16:14
 */
public interface SysUserRepository extends JpaRepository<SysUser, String> {

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

    /**
     * 根据用户ID删除SysUser
     * JpaRepository 默认的deleteById，会执行两条SQL语句，用此种方法只执行一条SQL语句
     *
     * @param userId 用户ID
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("delete from SysUser su where su.userId = ?1")
    void deleteByUserId(String userId);
}
