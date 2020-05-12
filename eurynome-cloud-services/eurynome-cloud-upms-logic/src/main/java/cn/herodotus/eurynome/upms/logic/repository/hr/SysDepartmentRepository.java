package cn.herodotus.eurynome.upms.logic.repository.hr;

import cn.herodotus.eurynome.component.data.base.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Description: 部门 Repository</p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:47
 */
public interface SysDepartmentRepository extends BaseRepository<SysDepartment, String> {

    List<SysDepartment> findAllByOrganizationId(String organizationId);
}
