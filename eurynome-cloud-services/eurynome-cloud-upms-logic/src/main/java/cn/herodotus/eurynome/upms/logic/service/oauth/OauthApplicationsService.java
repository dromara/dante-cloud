package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.data.base.service.BaseCacheService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthScopes;
import cn.herodotus.eurynome.upms.api.entity.system.SysAuthority;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthApplicationsRepository;
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
 * <p> Description : OauthApplicationsService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 17:00
 */
@Slf4j
@Service
public class OauthApplicationsService extends BaseCacheService<OauthApplications, String> {

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_APPLICATIONS, expire = 86400, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, OauthApplications> oauthApplicationsCache;

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_APPLICATIONS_INDEX, expire = 86400, cacheType = CacheType.BOTH, localLimit = 100)
    private Cache<String, Set<String>> oauthApplicationsIndexCache;

    @Autowired
    private OauthApplicationsRepository oauthApplicationsRepository;

    @Override
    public Cache<String, OauthApplications> getCache() {
        return this.oauthApplicationsCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return this.oauthApplicationsIndexCache;
    }

    @Override
    public OauthApplications findById(String appKey) {
        OauthApplications oauthApplications = this.getFromCache(appKey);

        if (ObjectUtils.isEmpty(oauthApplications)) {
            oauthApplications = oauthApplicationsRepository.findByAppKey(appKey);
            this.cache(oauthApplications);
        }

        log.debug("[Herodotus] |- OauthApplications Service findById.");
        return oauthApplications;
    }

    @Override
    public void deleteById(String appKey) {
        log.debug("[Herodotus] |- OauthApplications Service delete.");
        oauthApplicationsRepository.deleteById(appKey);
        this.remove(appKey);
    }

    @Override
    public OauthApplications saveOrUpdate(OauthApplications domain) {
        OauthApplications savedOauthApplications = oauthApplicationsRepository.saveAndFlush(domain);
        this.cache(savedOauthApplications);
        log.debug("[Herodotus] |- OauthApplications Service saveOrUpdate.");
        return savedOauthApplications;
    }

    @Override
    public Page<OauthApplications> findByPage(int pageNumber, int pageSize) {
        Page<OauthApplications> pages = oauthApplicationsRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createTime"));
        cache(pages.getContent());
        log.debug("[Herodotus] |- OauthApplications Service findByPage.");
        return pages;
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
