package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.management.nacos.ConfigContentFactory;
import cn.herodotus.eurynome.component.management.nacos.NacosConfig;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthMicroservicesRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p> Description : OauthMicroservicesService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 13:58
 */
@Slf4j
@Service
public class OauthMicroservicesService extends BaseService<OauthMicroservices, String> {

    private static final String CACHE_NAME = UpmsConstants.CACHE_NAME_OAUTH_MICROSERVICES;

    @CreateCache(name = CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, OauthMicroservices> dataCache;

    @CreateCache(name = CACHE_NAME + UpmsConstants.INDEX_CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    @Autowired
    private OauthMicroservicesRepository oauthMicroservicesRepository;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private NacosConfig nacosConfig;

    @Override
    public Cache<String, OauthMicroservices> getCache() {
        return this.dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return this.indexCache;
    }

    @Override
    public BaseRepository<OauthMicroservices, String> getRepository() {
        return oauthMicroservicesRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthMicroservices saveOrUpdate(OauthMicroservices domain) {
        OauthMicroservices oauthMicroservices = super.saveOrUpdate(domain);
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.synchronize(oauthMicroservices);
        createOrUpdateConfig(oauthMicroservices, oauthClientDetails);
        return oauthMicroservices;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String serviceId) {
        OauthMicroservices oauthMicroservices = findById(serviceId);
        super.deleteById(serviceId);
        oauthClientDetailsService.deleteById(serviceId);
        nacosConfig.removeConfig(getDataId(oauthMicroservices), getGroup(oauthMicroservices));
    }

    private void createOrUpdateConfig(OauthMicroservices oauthMicroservices, OauthClientDetails oauthClientDetails) {
        if(ObjectUtils.isNotEmpty(oauthMicroservices)&&ObjectUtils.isNotEmpty(oauthMicroservices.getSupplier())) {
            String dataId = getDataId(oauthMicroservices);
            String group = getGroup(oauthMicroservices);
            String content = ConfigContentFactory.createOauthProperty(oauthClientDetails.getClientId(), oauthClientDetails.getClientSecret());
            nacosConfig.publishOrUpdateConfig(dataId, group, content);
        }
    }

    private String getGroup(OauthMicroservices oauthMicroservices) {
        return ObjectUtils.isNotEmpty(oauthMicroservices.getSupplier()) ? oauthMicroservices.getSupplier().getSupplierCode() : NacosConfig.DEFAULT_GROUP;
    }

    private String getDataId(OauthMicroservices oauthMicroservices) {
        return oauthMicroservices.getServiceId() + SymbolConstants.SUFFIX_YML;
    }
}
