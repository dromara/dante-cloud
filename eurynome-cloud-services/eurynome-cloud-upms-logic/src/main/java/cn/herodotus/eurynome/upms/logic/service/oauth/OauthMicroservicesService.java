package cn.herodotus.eurynome.upms.logic.service.oauth;

import cn.herodotus.eurynome.component.common.enums.ApplicationType;
import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
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
    public OauthMicroservices saveOrUpdate(OauthMicroservices domain) {
        if(ObjectUtils.isNotEmpty(domain)) {
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
    public void deleteById(String serviceId) {
        super.deleteById(serviceId);
        oauthClientDetailsService.deleteById(serviceId);
    }
}
