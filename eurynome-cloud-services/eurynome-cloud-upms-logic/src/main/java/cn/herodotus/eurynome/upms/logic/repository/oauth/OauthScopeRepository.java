package cn.herodotus.eurynome.upms.logic.repository.oauth;

import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p> Description : OauthScopeRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:57
 */
public interface OauthScopeRepository extends JpaRepository<OauthScopes, String> {
}
