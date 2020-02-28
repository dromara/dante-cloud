package cn.herodotus.eurynome.platform.uaa.service.feign;

import cn.herodotus.eurynome.upms.api.constants.ServiceNameConstants;
import cn.herodotus.eurynome.upms.api.feign.factory.VerifyCaptchaServiceFallbackFactory;
import cn.herodotus.eurynome.upms.api.feign.service.IVerifyCaptchaService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author gengwei.zheng
 */
@FeignClient(value = ServiceNameConstants.UPMS_SERVICE, fallbackFactory = VerifyCaptchaServiceFallbackFactory.class)
public interface VerifyCaptchaRemoteService extends IVerifyCaptchaService {
}
