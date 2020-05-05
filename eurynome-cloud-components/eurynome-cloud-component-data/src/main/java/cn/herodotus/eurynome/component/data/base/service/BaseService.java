package cn.herodotus.eurynome.component.data.base.service;

import cn.herodotus.eurynome.component.data.base.entity.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * <p> Description : BaseService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/29 18:22
 */
@Slf4j
public abstract class BaseService<E extends AbstractEntity, ID extends Serializable> extends AbstractCacheService<E, ID> {

    @Override
    public E findById(ID id) {
        E domain = getFromCache(String.valueOf(id));
        if (ObjectUtils.isEmpty(domain)) {
            domain = super.findById(id);
            cache(domain);
        }

        log.debug("[Herodotus] |- AbstractCrudService Service findById.");
        return domain;
    }

    @Override
    public void deleteById(ID id) {
        super.deleteById(id);
        remove(String.valueOf(id));
        log.debug("[Herodotus] |- AbstractCrudService Service delete.");
    }

    @Override
    public E saveOrUpdate(E domain) {
        E savedDomain = super.saveAndFlush(domain);
        cache(savedDomain);
        log.debug("[Herodotus] |- AbstractCrudService Service saveOrUpdate.");
        return savedDomain;
    }

    @Override
    public Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = getPageFromCache(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(pages.getContent())) {
            pages = super.findByPage(pageNumber, pageSize, direction, properties);
            cachePage(pages);
        }

        log.debug("[Herodotus] |- AbstractCrudService Service findByPage.");
        return pages;
    }

    @Override
    public List<E> findAll() {
        List<E> domains = getFindAllFromCache();
        if (CollectionUtils.isEmpty(domains)) {
            domains = super.findAll();
            cacheFindAll(domains);
        }
        log.debug("[Herodotus] |- AbstractCrudService Service findAll.");
        return domains;
    }
}
