package cn.herodotus.eurynome.component.data.base.service;

import cn.herodotus.eurynome.component.common.definition.AbstractDomain;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 11:56
 */
public abstract class BaseReadableService<D extends AbstractDomain, ID extends Serializable> extends BaseService<D, ID> {

    public abstract Page<D> findByPageWithEmployeeId(String employeeId, int pageNumber, int pageSize);
}
