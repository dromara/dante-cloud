package cn.herodotus.eurynome.upms.api.service.fegin;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.component.security.oauth2.provider.HerodotusClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p> Description : OauthClientDetailFeignService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 18:10
 */
public interface OauthClientDetailsFeignService {

    /**
     * 获取OauthClientDetail
     *
     * @param clientId OauthClientDetail ID
     * @return OauthClientDetail
     */
    @GetMapping("/oauth/client_details")
    Result<HerodotusClientDetails> findByClientId(@RequestParam(value = "clientId") String clientId);
}
