package cn.herodotus.eurynome.upms.logic.repository.oauth;

import cn.herodotus.eurynome.upms.api.entity.oauth.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> Description : OauthClientDetailRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:57
 */
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, String> {

    /**
     * 查询oauth client detail
     *
     * @param clientId clientId
     * @return OauthClientDetail
     */
    OauthClientDetails findByClientId(String clientId);

    /**
     * 删除人员基本操作
     *
     * @param clientId OauthClientDetail ID
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("delete from OauthClientDetails ocd where ocd.clientId = ?1")
    void deleteByClientId(String clientId);
}
