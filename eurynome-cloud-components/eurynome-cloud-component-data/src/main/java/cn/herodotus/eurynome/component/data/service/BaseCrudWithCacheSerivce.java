package cn.herodotus.eurynome.component.data.service;

import cn.herodotus.eurynome.component.data.entity.BaseEntity;
import com.alicp.jetcache.Cache;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 21:43
 */
public abstract  class BaseCrudWithCacheSerivce<D extends BaseEntity, ID extends Serializable> extends BaseCrudService<D, ID>{

    public abstract Cache<String, D> getCache();

    protected void cacheCollection(Collection<D> objects) {
        if (CollectionUtils.isNotEmpty(objects) && ObjectUtils.isNotEmpty(getCache())) {
            getCache().putAll(objects.stream().collect(Collectors.toMap(D::getId, object -> object)));
        }
    }

    protected void cacheEntity(D object) {
        if (ObjectUtils.isNotEmpty(object) && ObjectUtils.isNotEmpty(getCache())) {
            getCache().put(object.getId(), object);
        }
    }
}
