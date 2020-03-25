package cn.herodotus.eurynome.upms.logic.repository.oauth;

import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> Description : OauthScopeRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:57
 */
public interface OauthScopesRepository extends JpaRepository<OauthScopes, String> {

    /**
     * 删除人员基本操作
     * @param scopeId OauthScopes ID
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("delete from OauthScopes os where os.scopeId = ?1")
    void deleteByScopeId(String scopeId);

    /**
     * 根据appKey查找 OauthScopes
     * @param scopeId OauthScopes ID
     * @return OauthScopes 对象
     */
    OauthScopes findByScopeId(String scopeId);
}
