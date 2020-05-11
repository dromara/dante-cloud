package cn.herodotus.eurynome.component.data.base.entity;


import cn.herodotus.eurynome.component.data.base.service.AbstractCacheService;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> Description : BaseCacheEntity </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/24 16:38
 */
public abstract class AbstractEntity implements Entity {

    /**
     * 数据表中除主键以外的唯一数据属性
     *
     * 有些实体，只使用一个属性作为Cache Key {@link #getId()} ()} 不能满足需求。
     * 例如：SysUser，使用UserId和UserName查询都比较频繁，用UserId作为缓存的Key，那么用UserName就无法从缓存读取。
     * 除非用UserId作为缓存Key，缓存一遍实体，用UserName再作为缓存Key，缓存一遍实体。这样增加缓存管理复杂度。
     *
     * 通过getLinkedProperty()，在{@link AbstractCacheService} 中维护一个Map。
     * 通过这个Map，实现指定属性与CacheKey的映射。
     *
     * @return LinkedProperty
     */
    @ApiModelProperty(hidden = true)
    public abstract String getLinkedProperty();
}
