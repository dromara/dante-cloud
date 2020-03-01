package cn.herodotus.eurynome.upms.logic.repository.system;

import cn.herodotus.eurynome.upms.api.entity.system.SysApplication;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SysApplicationRepository extends JpaRepository<SysApplication, String> {

    @EntityGraph(value = "SysApplicationWithAuthority", type = EntityGraph.EntityGraphType.FETCH)
    SysApplication findByApplicationId(String applicationId);

    @Query("from SysApplication sa where sa.sysClientDetail.clientId = ?1")
    SysApplication findByClientId(String clientId);

    @Modifying
    @Transactional
    @Query("delete from SysApplication sa where sa.applicationId = ?1")
    void deleteByApplicationId(String applicationId);
}
