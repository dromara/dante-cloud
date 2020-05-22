package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.common.enums.ApplicationType;
import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.component.management.domain.Config;
import cn.herodotus.eurynome.component.management.nacos.ConfigContentFactory;
import cn.herodotus.eurynome.component.management.nacos.NacosConfig;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.oauth.OauthMicroservices;
import cn.herodotus.eurynome.upms.logic.repository.oauth.OauthMicroservicesRepository;
import cn.hutool.core.util.IdUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
        if (ObjectUtils.isNotEmpty(domain)) {
            domain.setApplicationType(ApplicationType.SERVICE);
            if (StringUtils.isBlank(domain.getAppSecret())) {
                domain.setAppSecret(IdUtil.randomUUID());
            }
        }
        OauthMicroservices oauthMicroservices = super.saveOrUpdate(domain);
        oauthClientDetailsService.synchronize(oauthMicroservices);
        return oauthMicroservices;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String serviceId) {
        deleteConfig(serviceId);
        super.deleteById(serviceId);
        oauthClientDetailsService.deleteById(serviceId);
    }

    private Config convertOauthMicroservicesToConfig(OauthMicroservices oauthMicroservices) {
        if (ObjectUtils.isNotEmpty(oauthMicroservices) && ObjectUtils.isNotEmpty(oauthMicroservices.getSupplier())) {
            Config config = new Config();
            config.setDataId(oauthMicroservices.getAppCode() + SymbolConstants.SUFFIX_YAML);
            if (StringUtils.isNotBlank(oauthMicroservices.getSupplier().getSupplierCode())) {
                config.setGroup(oauthMicroservices.getSupplier().getSupplierCode());
            }
            config.setContent(ConfigContentFactory.createOauthProperty(oauthMicroservices.getServiceId(), oauthMicroservices.getAppSecret()));
            return config;
        }

        return null;
    }

    public boolean deleteConfig(String serviceId) {
        OauthMicroservices oauthMicroservices = findById(serviceId);
        return deleteConfig(oauthMicroservices);
    }

    private boolean deleteConfig(OauthMicroservices oauthMicroservices) {
        Config config = convertOauthMicroservicesToConfig(oauthMicroservices);
        return nacosConfig.removeConfig(config);
    }

    public boolean publishOrUpdateConfig(String clientId) {
        OauthMicroservices oauthMicroservices = findById(clientId);
        return publishOrUpdateConfig(oauthMicroservices);
    }

    private boolean publishOrUpdateConfig(OauthMicroservices oauthMicroservices) {
        Config config = convertOauthMicroservicesToConfig(oauthMicroservices);
        return nacosConfig.publishOrUpdateConfig(config);
    }
}
