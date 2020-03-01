package cn.herodotus.eurynome.upms.logic.repository.hr;

import cn.herodotus.eurynome.upms.api.entity.hr.SysDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface SysDepartmentRepository extends JpaRepository<SysDepartment, String> {

    SysDepartment findByDepartmentId(String departmentId);

    List<SysDepartment> findAllByOrganizationId(String organizationId);

    Page<SysDepartment> findAllByOrganizationId(String organizationId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from SysDepartment sd where sd.departmentId = ?1")
    void deleteByDepartmentId(String organizationId);
}
