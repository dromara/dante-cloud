package cn.herodotus.eurynome.component.data.service;

import cn.herodotus.eurynome.component.common.domain.AbstractDomain;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/9 10:11
 */
public abstract class BaseCrudService<D extends AbstractDomain, ID extends Serializable> extends BaseService<D, ID> {

    public abstract D findById(ID id);

    public abstract void deleteById(ID id);

    public abstract D saveOrUpdate(D domain);

    public abstract Page<D> findByPage(int pageNumber, int pageSize);
}
