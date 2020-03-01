package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SysUserRepository extends JpaRepository<SysUser, String> {

    @EntityGraph(value = "SysUserWithRolesAndAuthority", type = EntityGraph.EntityGraphType.FETCH)
    SysUser findByUserId(String userId);

    @EntityGraph(value = "SysUserWithRolesAndAuthority", type = EntityGraph.EntityGraphType.FETCH)
    SysUser findByUserName(String userName);

    @Modifying
    @Transactional
    @Query("delete from SysUser su where su.userId = ?1")
    void deleteByUserId(String userId);
}
