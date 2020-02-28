package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.component.common.enums.AuthorityType;
import cn.herodotus.eurynome.component.data.jpa.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysAuthorityRepository extends BaseRepository<SysAuthority, String> {

    SysAuthority findByAuthorityId(String authorityId);

    @Modifying
    @Transactional
    @Query("delete from SysAuthority sa where sa.authorityId = ?1")
    void deleteByAuthorityId(String authorityId);

    List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType);
}
