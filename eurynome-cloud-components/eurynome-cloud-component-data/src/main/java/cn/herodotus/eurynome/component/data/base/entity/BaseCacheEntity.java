package cn.herodotus.eurynome.component.data.base.entity;


import cn.herodotus.eurynome.component.common.definition.AbstractDomain;

/**
 * <p> Description : BaseCacheEntity </p>
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

    /**
     * 数据表中除主键以外的唯一数据属性
     *
     * 有些实体，只使用一个属性作为Cache Key {@link #getDomainCacheKey()} 不能满足需求。
     * 例如：SysUser，使用UserId和UserName查询都比较频繁，用UserId作为缓存的Key，那么用UserName就无法从缓存读取。
     * 除非用UserId作为缓存Key，缓存一遍实体，用UserName再作为缓存Key，缓存一遍实体。这样增加缓存管理复杂度。
     *
     * 通过getLinkedProperty()，在{@link cn.herodotus.eurynome.component.data.base.service.BaseCacheService} 中维护一个Map。
     * 通过这个Map，实现指定属性与CacheKey的映射。
     *
     * @return LinkedProperty
     */
    public abstract String getLinkedProperty();
}
