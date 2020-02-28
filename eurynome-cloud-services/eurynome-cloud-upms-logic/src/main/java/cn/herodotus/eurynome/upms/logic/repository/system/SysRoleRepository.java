package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.component.data.jpa.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SysRoleRepository extends BaseRepository<SysRole, String> {

    @EntityGraph(value = "SysRoleWithAndAuthority", type = EntityGraph.EntityGraphType.FETCH)
    SysRole findByRoleId(String roleId);

    @Modifying
    @Transactional
    @Query("delete from SysRole sr where sr.roleId = ?1")
    void deleteByRoleId(String roleId);
}
