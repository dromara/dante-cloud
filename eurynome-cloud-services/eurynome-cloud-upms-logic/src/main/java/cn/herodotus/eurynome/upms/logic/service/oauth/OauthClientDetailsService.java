package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import cn.herodotus.eurynome.component.common.enums.ApplicationType;
import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.management.domain.Config;
import cn.herodotus.eurynome.component.management.nacos.ConfigContentFactory;
import cn.herodotus.eurynome.component.management.nacos.NacosConfig;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthClientDetailsRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
    private NacosConfig nacosConfig;

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

    @Override
    public void deleteById(String clientId) {
        deleteConfig(clientId);
        super.deleteById(clientId);
    }

    public boolean deleteConfig(String clientId) {
        OauthClientDetails oauthClientDetails = findById(clientId);
        return deleteConfig(oauthClientDetails);
    }

    private boolean deleteConfig(OauthClientDetails oauthClientDetails) {
        Config config = convertOauthClientDetailsToConfig(oauthClientDetails);
        return nacosConfig.removeConfig(config);
    }

    public boolean publishOrUpdateConfig(String clientId) {
        OauthClientDetails oauthClientDetails = findById(clientId);
        return publishOrUpdateConfig(oauthClientDetails);
    }

    private boolean publishOrUpdateConfig(OauthClientDetails oauthClientDetails) {
        Config config = convertOauthClientDetailsToConfig(oauthClientDetails);
        return nacosConfig.publishOrUpdateConfig(config);
    }

    private Config convertOauthClientDetailsToConfig(OauthClientDetails oauthClientDetails) {
        OauthMicroservices oauthMicroservices = getOauthMicroservices(oauthClientDetails);
        if(ObjectUtils.isNotEmpty(oauthMicroservices)&&ObjectUtils.isNotEmpty(oauthMicroservices.getSupplier())) {
            Config config = new Config();
            config.setDataId(oauthMicroservices.getAppCode() + SymbolConstants.SUFFIX_YML);
            if (StringUtils.isNotBlank(oauthMicroservices.getSupplier().getSupplierCode())) {
                config.setGroup(oauthMicroservices.getSupplier().getSupplierCode());
            }
            config.setContent(ConfigContentFactory.createOauthProperty(oauthClientDetails.getClientId(), oauthClientDetails.getClientSecret()));
            return config;
        }

        return null;
    }

    private OauthMicroservices getOauthMicroservices(OauthClientDetails oauthClientDetails) {
        if (ObjectUtils.isNotEmpty(oauthClientDetails) && StringUtils.isNotBlank(oauthClientDetails.getAdditionalInformation())) {
            JSONObject jsonObject = JSON.parseObject(oauthClientDetails.getAdditionalInformation());
            String applicationType = jsonObject.getString("applicationType");
            if (StringUtils.isNotBlank(applicationType) && StringUtils.equals(applicationType, ApplicationType.SERVICE.name())) {
                return JSON.toJavaObject(jsonObject, OauthMicroservices.class);
            }
        }

        return null;
    }
}
