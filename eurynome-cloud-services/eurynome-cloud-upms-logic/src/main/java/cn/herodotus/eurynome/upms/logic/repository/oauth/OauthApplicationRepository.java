package cn.herodotus.eurynome.upms.logic.repository.oauth;

import cn.herodotus.eurynome.upms.api.entity.oauth.OauthApplications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> Description : OauthApplicationRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:52
 */
public interface OauthApplicationRepository extends JpaRepository<OauthApplications, String> {

    /**
     * 删除人员基本操作
     * @param appKey OauthApplication ID
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("delete from OauthApplications oa where oa.appKey = ?1")
    void deleteByAppKey(String appKey);

    /**
     * 根据appKey查找OauthApplication
     * @param appKey OauthApplication ID
     * @return OauthApplication 对象
     */
    OauthApplications findByAppKey(String appKey);
}
