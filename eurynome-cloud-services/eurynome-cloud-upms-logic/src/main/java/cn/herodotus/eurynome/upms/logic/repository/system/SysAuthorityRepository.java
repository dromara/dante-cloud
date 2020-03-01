package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.component.common.enums.AuthorityType;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysAuthorityRepository extends JpaRepository<SysAuthority, String> {

    SysAuthority findByAuthorityId(String authorityId);

    @Modifying
    @Transactional
    @Query("delete from SysAuthority sa where sa.authorityId = ?1")
    void deleteByAuthorityId(String authorityId);

    List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType);
}
