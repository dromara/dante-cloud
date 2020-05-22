package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.common.enums.AuthorityType;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;

import java.util.List;

public interface SysAuthorityRepository extends BaseRepository<SysAuthority, String> {

    List<SysAuthority> findAllByAuthorityType(AuthorityType authorityType);
}
