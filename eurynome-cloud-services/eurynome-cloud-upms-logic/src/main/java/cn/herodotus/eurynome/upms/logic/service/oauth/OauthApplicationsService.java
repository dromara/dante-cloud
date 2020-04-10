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
import org.apache.commons.collections4.CollectionUtils;
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

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_APPLICATIONS, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, OauthApplications> oauthApplicationsCache;

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_APPLICATIONS_INDEX, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
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
        OauthApplications oauthApplications = getFromCache(appKey);
        if (ObjectUtils.isEmpty(oauthApplications)) {
            oauthApplications = oauthApplicationsRepository.findByAppKey(appKey);
            cache(oauthApplications);
        }

        log.debug("[Herodotus] |- OauthApplications Service findById.");
        return oauthApplications;
    }

    @Override
    public void deleteById(String appKey) {
        log.debug("[Herodotus] |- OauthApplications Service delete.");
        oauthApplicationsRepository.deleteByAppKey(appKey);
        remove(appKey);
    }

    @Override
    public OauthApplications saveOrUpdate(OauthApplications domain) {
        OauthApplications oauthApplications = oauthApplicationsRepository.saveAndFlush(domain);
        cache(oauthApplications);
        log.debug("[Herodotus] |- OauthApplications Service saveOrUpdate.");
        return oauthApplications;
    }

    @Override
    public Page<OauthApplications> findByPage(int pageNumber, int pageSize) {
        Page<OauthApplications> pages = getPageFromCache(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(pages.getContent())) {
            pages = oauthApplicationsRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createTime"));
            cachePage(pages);
        }

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
