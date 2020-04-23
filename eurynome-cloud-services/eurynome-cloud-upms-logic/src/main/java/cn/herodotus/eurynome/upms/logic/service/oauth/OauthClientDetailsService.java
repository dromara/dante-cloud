package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.data.base.service.BaseCacheService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthClientDetailsRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p> Description : OauthClientDetailService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:59
 */
@Slf4j
@Service
public class OauthClientDetailsService extends BaseCacheService<OauthClientDetails, String> {

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_CLIENTDETAILS, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, OauthClientDetails> oauthClientDetailCache;

    @CreateCache(name = UpmsConstants.CACHE_NAME_OAUTH_CLIENTDETAILS_INDEX, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> oauthClientDetailIndexCache;

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Override
    public Cache<String, OauthClientDetails> getCache() {
        return oauthClientDetailCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return oauthClientDetailIndexCache;
    }

    @Override
    public OauthClientDetails findById(String clientId) {
        OauthClientDetails oauthClientDetails = this.getFromCache(clientId);

        if (ObjectUtils.isEmpty(oauthClientDetails)) {
            oauthClientDetails = oauthClientDetailsRepository.findByClientId(clientId);
            cache(oauthClientDetails);
        }

        log.debug("[Herodotus] |- OauthClientDetails Service findById.");
        return oauthClientDetails;
    }

    @Override
    public void deleteById(String clientId) {
        oauthClientDetailsRepository.deleteById(clientId);
        remove(clientId);
        log.debug("[Herodotus] |- OauthClientDetails Service delete.");
    }

    @Override
    public OauthClientDetails saveOrUpdate(OauthClientDetails domain) {
        OauthClientDetails savedOauthClientDetails = oauthClientDetailsRepository.saveAndFlush(domain);
        cache(savedOauthClientDetails);
        log.debug("[Herodotus] |- OauthClientDetails Service saveOrUpdate.");
        return savedOauthClientDetails;
    }

    @Override
    public Page<OauthClientDetails> findByPage(int pageNumber, int pageSize) {
        Page<OauthClientDetails> pages = oauthClientDetailsRepository.findAll(PageRequest.of(pageNumber, pageSize));
        cache(pages.getContent());
        log.debug("[Herodotus] |- OauthClientDetails Service findByPage.");
        return pages;
    }

    public OauthClientDetails synchronize(OauthApplications oauthApplications) {
        OauthClientDetails oauthClientDetails = findById(oauthApplications.getAppKey());
        oauthClientDetails = UpmsHelper.convertOauthApplicationsToOauthClientDetails(oauthApplications, oauthClientDetails);

        log.debug("[Herodotus] |- OauthClientDetails Service synchronize.");
        return saveOrUpdate(oauthClientDetails);
    }
}
