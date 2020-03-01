package cn.herodotus.eurynome.upms.logic.repository.hr;

import cn.herodotus.eurynome.upms.api.entity.hr.SysEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Description: 人员 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:47
 */
public interface SysEmployeeRepository extends JpaRepository<SysEmployee, String> {

    /**
     * 根据ID查找
     * @param employeeId
     * @return
     */
    SysEmployee findByEmployeeId(String employeeId);

    /**
     * 根据手机号码查找
     * @param mobilePhoneNumber
     * @return
     */
    SysEmployee findByMobilePhoneNumber(String mobilePhoneNumber);

    /**
     * 根据部门ID查找
     * @param departmentId
     * @return
     */
    List<SysEmployee> findAllByDepartmentId(String departmentId);

    Page<SysEmployee> findAllByDepartmentId(String departmentId, Pageable pageable);

    /**
     * 删除人员基本操作
     * @param employeeId
     */
    @Modifying
    @Transactional
    @Query("delete from SysEmployee se where se.employeeId = ?1")
    void deleteByEmployeeId(String employeeId);
}
