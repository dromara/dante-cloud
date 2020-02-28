package cn.herodotus.eurynome.platform.uaa.service.feign;

import cn.herodotus.eurynome.upms.api.constants.ServiceNameConstants;
import cn.herodotus.eurynome.upms.api.feign.factory.SysApplicationServiceFallbackFactory;
import cn.herodotus.eurynome.upms.api.feign.service.ISysApplicationService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author gengwei.zheng
 */
@FeignClient(value = ServiceNameConstants.UPMS_SERVICE, fallbackFactory = SysApplicationServiceFallbackFactory.class)
public interface SysApplicationRemoteService extends ISysApplicationService {
}
