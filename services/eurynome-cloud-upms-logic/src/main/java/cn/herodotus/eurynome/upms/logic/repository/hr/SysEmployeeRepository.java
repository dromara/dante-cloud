package cn.herodotus.eurynome.upms.logic.repository.hr;

import cn.herodotus.eurynome.crud.repository.BaseRepository;
import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p>Description: 人员 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:47
 */
public interface SysEmployeeRepository extends BaseRepository<SysEmployee, String> {

    /**
     * 根据部门ID查找
     * @param departmentId
     * @return
     */
    @Query("from SysEmployee se where se.department.departmentId = ?1")
    List<SysEmployee> findAllByDepartmentId(String departmentId);
}
