package cn.herodotus.eurynome.upms.api.feign.factory;

import cn.herodotus.eurynome.upms.api.feign.fallback.VerifyCaptchaServiceFallback;
import cn.herodotus.eurynome.upms.api.feign.service.IVerifyCaptchaService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class VerifyCaptchaServiceFallbackFactory implements FallbackFactory<IVerifyCaptchaService> {

    @Override
    public IVerifyCaptchaService create(Throwable throwable) {
        VerifyCaptchaServiceFallback fallback = new VerifyCaptchaServiceFallback();
        fallback.setCause(throwable);
        return fallback;
    }
}
