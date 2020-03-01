package cn.herodotus.eurynome.upms.logic.repository.hr;

import cn.herodotus.eurynome.upms.api.entity.hr.SysOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Description: 单位 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/20 11:37
 */
public interface SysOrganizationRepository extends JpaRepository<SysOrganization, String> {

    SysOrganization findByOrganizationId(String organizationId);

    @Modifying
    @Transactional
    @Query("delete from SysOrganization so where so.organizationId = ?1")
    void deleteByOrganizationId(String organizationId);
}
