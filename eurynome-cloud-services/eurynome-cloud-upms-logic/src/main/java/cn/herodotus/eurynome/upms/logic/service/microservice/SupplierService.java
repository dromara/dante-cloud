package cn.herodotus.eurynome.upms.logic.service.microservice;

import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.component.data.base.service.BaseService;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.microservice.Supplier;
import cn.herodotus.eurynome.upms.logic.repository.microservice.SupplierRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 13:58
 */
@Slf4j
@Service
public class SupplierService extends BaseService<Supplier, String> {

    private static final String CACHE_NAME = UpmsConstants.CACHE_NAME_MICROSERVICE_SUPPLIER;

    @CreateCache(name = CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Supplier> dataCache;

    @CreateCache(name = CACHE_NAME + UpmsConstants.INDEX_CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Cache<String, Supplier> getCache() {
        return this.dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return this.indexCache;
    }

    @Override
    public BaseRepository<Supplier, String> getRepository() {
        return supplierRepository;
    }
}
