package cn.herodotus.eurynome.upms.api.service.remote;

import cn.herodotus.eurynome.upms.api.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/3 16:01
 */
@FeignClient(value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteVerificationCodeService {
}
