package cn.herodotus.eurynome.component.data.base.entity;


import cn.herodotus.eurynome.component.common.definition.AbstractDomain;

/**
 * <p> Description : TODO </p>
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
