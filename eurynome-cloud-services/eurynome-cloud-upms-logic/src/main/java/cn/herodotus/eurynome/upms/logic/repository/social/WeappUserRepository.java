package cn.herodotus.eurynome.upms.logic.repository.social;

import cn.herodotus.eurynome.upms.api.entity.social.WeappUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeappUserRepository extends JpaRepository<WeappUser, String> {
}
