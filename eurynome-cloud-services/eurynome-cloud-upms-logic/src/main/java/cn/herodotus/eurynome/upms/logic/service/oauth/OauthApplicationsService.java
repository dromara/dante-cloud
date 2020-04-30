package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthApplicationsRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : OauthApplicationsService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 17:00
 */
@Slf4j
@Service
public class OauthApplicationsService extends BaseService<OauthApplications, String> {

    private static final String CACHE_NAME = UpmsConstants.CACHE_NAME_OAUTH_APPLICATIONS;

    @CreateCache(name = CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, OauthApplications> dataCache;

    @CreateCache(name = CACHE_NAME + UpmsConstants.INDEX_CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    @Autowired
    private OauthApplicationsRepository oauthApplicationsRepository;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Override
    public Cache<String, OauthApplications> getCache() {
        return this.dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return this.indexCache;
    }

    @Override
    public BaseRepository<OauthApplications, String> getRepository() {
        return oauthApplicationsRepository;
    }

    @Override
    public void deleteById(String appKey) {
        super.deleteById(appKey);
        oauthClientDetailsService.deleteById(appKey);
    }

    @Override
    public OauthApplications saveOrUpdate(OauthApplications domain) {
        OauthApplications oauthApplications = super.saveOrUpdate(domain);
        oauthClientDetailsService.synchronize(oauthApplications);
        return oauthApplications;
    }

    public OauthApplications assign(String appKey, String[] scopeIds) {

        log.debug("[Herodotus] |- OauthApplications Service authorize.");

        Set<OauthScopes> oauthScopesSet = new HashSet<>();
        for (String scopeId : scopeIds) {
            OauthScopes oauthScopes = new OauthScopes();
            oauthScopes.setScopeId(scopeId);
            oauthScopesSet.add(oauthScopes);
        }

        OauthApplications oauthApplications = findById(appKey);
        oauthApplications.setScopes(oauthScopesSet);

        return saveOrUpdate(oauthApplications);
    }
}
