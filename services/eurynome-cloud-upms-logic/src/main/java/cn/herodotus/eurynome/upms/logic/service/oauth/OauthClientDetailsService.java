package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.data.base.service.BaseService;
import cn.herodotus.eurynome.operation.nacos.HerodotusNacosConfig;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.api.repository.oauth.OauthClientDetailsRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OauthClientDetailsService extends BaseService<OauthClientDetails, String> {

    private static final String CACHE_NAME = UpmsConstants.CACHE_NAME_OAUTH_CLIENTDETAILS;

    @CreateCache(name = CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, OauthClientDetails> dataCache;

    @CreateCache(name = CACHE_NAME + UpmsConstants.INDEX_CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;
    @Autowired
    private HerodotusNacosConfig herodotusNacosConfig;

    @Override
    public Cache<String, OauthClientDetails> getCache() {
        return dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return indexCache;
    }

    @Override
    public BaseRepository<OauthClientDetails, String> getRepository() {
        return oauthClientDetailsRepository;
    }

    public OauthClientDetails synchronize(OauthApplications oauthApplications) {
        OauthClientDetails oauthClientDetails = findById(oauthApplications.getAppKey());
        oauthClientDetails = UpmsHelper.convertOauthApplicationsToOauthClientDetails(oauthApplications, oauthClientDetails);

        log.debug("[Herodotus] |- OauthClientDetails Service synchronize.");
        return saveOrUpdate(oauthClientDetails);
    }

    public OauthClientDetails synchronize(OauthMicroservices oauthMicroservices) {
        OauthClientDetails oauthClientDetails = findById(oauthMicroservices.getServiceId());
        oauthClientDetails = UpmsHelper.convertOauthMicroserviceToOauthClientDetails(oauthMicroservices, oauthClientDetails);

        log.debug("[Herodotus] |- OauthClientDetails Service synchronize.");
        return saveOrUpdate(oauthClientDetails);
    }
}
