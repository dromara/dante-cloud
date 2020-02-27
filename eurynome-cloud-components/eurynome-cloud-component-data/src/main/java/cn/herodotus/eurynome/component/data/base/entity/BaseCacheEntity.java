package cn.herodotus.eurynome.component.data.base.entity;

import cn.herodotus.eurynome.component.common.definitions.AbstractDomain;

/**
 * <p> Description : 基础可缓存实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/24 16:38
 */
public abstract class BaseCacheEntity extends AbstractDomain {

    /**
     * 根据该值作为缓存对象的Key
     * @return CacheKey
     */
    public abstract String getDomainCacheKey();
}
