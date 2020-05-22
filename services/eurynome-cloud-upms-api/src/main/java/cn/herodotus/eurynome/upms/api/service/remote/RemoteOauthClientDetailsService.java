package cn.herodotus.eurynome.upms.api.service.remote;

import cn.herodotus.eurynome.upms.api.constants.ServiceNameConstants;
import cn.herodotus.eurynome.upms.api.service.fegin.OauthClientDetailsFeignService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p> Description : RemoteOauthClientDetailService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 18:25
 */

@FeignClient(value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteOauthClientDetailsService extends OauthClientDetailsFeignService {
}
