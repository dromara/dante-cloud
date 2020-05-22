package cn.herodotus.eurynome.data.base.service;

import cn.herodotus.eurynome.data.base.entity.Entity;
import cn.herodotus.eurynome.data.base.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * <p> Description : 只读Service，可以提供基于视图实体的操作 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 11:56
 */
public interface ReadableService<E extends Entity, ID extends Serializable> {

    BaseRepository<E, ID> getRepository();

    default List<E> findAll() {
        return getRepository().findAll();
    }

    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    default List<E> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    default List<E> findAll(Specification<E> specification) {
        return getRepository().findAll(specification);
    }

    default Page<E> findAll(Specification<E> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    default List<E> findAll(Specification<E> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    default E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    default Page<E> findByPage(int pageNumber, int pageSize) {
        return findAll(PageRequest.of(pageNumber, pageSize));
    }

    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction) {
        return findAll(PageRequest.of(pageNumber, pageSize, direction));
    }

    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return findAll(PageRequest.of(pageNumber, pageSize, direction, properties));
    }

    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    default long count() {
        return getRepository().count();
    }

    default long count(Specification<E> specification) {
        return getRepository().count(specification);
    }
}
