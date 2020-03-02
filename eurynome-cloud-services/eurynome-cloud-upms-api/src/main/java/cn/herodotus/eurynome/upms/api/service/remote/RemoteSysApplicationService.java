package cn.herodotus.eurynome.upms.api.service.remote;

import cn.herodotus.eurynome.upms.api.constants.ServiceNameConstants;
import cn.herodotus.eurynome.upms.api.service.fegin.SysApplicationFeignService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author gengwei.zheng
 */
@FeignClient(value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteSysApplicationService extends SysApplicationFeignService {
}
