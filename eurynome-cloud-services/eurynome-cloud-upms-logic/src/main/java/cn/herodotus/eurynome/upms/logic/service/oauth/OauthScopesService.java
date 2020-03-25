package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.data.base.service.BaseCacheService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.api.entity.system.SysRole;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthScopesRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : OauthScopeService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 17:00
 */
@Slf4j
@Service
public class OauthScopesService extends BaseCacheService<OauthScopes, String> {

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_SCOPES, expire = 86400, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, OauthScopes> oauthScopesCache;

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_SCOPES_INDEX, expire = 86400, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, Set<String>> oauthScopesIndexCache;

    @Autowired
    private OauthScopesRepository oauthScopesRepository;

    @Override
    public Cache<String, OauthScopes> getCache() {
        return this.oauthScopesCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return this.oauthScopesIndexCache;
    }

    @Override
    public OauthScopes findById(String scopeId) {
        OauthScopes oauthScopes = this.getFromCache(scopeId);

        if (ObjectUtils.isEmpty(oauthScopes)) {
            oauthScopes = oauthScopesRepository.findByScopeId(scopeId);
            this.cache(oauthScopes);
        }

        log.debug("[Herodotus] |- OauthScopes Service findById.");
        return oauthScopes;
    }

    @Override
    public void deleteById(String scopeId) {
        log.debug("[Herodotus] |- OauthScopes Service delete.");
        oauthScopesRepository.deleteById(scopeId);
        this.remove(scopeId);
    }

    @Override
    public OauthScopes saveOrUpdate(OauthScopes domain) {
        OauthScopes savedOauthScopes = oauthScopesRepository.saveAndFlush(domain);
        this.cache(savedOauthScopes);
        log.debug("[Herodotus] |- OauthScopes Service saveOrUpdate.");
        return savedOauthScopes;
    }

    @Override
    public Page<OauthScopes> findByPage(int pageNumber, int pageSize) {
        Page<OauthScopes> pages = oauthScopesRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createTime"));
        cache(pages.getContent());
        log.debug("[Herodotus] |- OauthScopes Service findByPage.");
        return pages;
    }

    public OauthScopes authorize(String scopeId, String[] authorities) {

        log.debug("[Herodotus] |- OauthScopes Service authorize.");

        Set<SysAuthority> sysAuthorities = new HashSet<>();
        for (String authority : authorities) {
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setAuthorityId(authority);
            sysAuthorities.add(sysAuthority);
        }

        OauthScopes oauthScopes = findById(scopeId);
        oauthScopes.setAuthorities(sysAuthorities);

        return saveOrUpdate(oauthScopes);
    }
}
