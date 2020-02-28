package cn.herodotus.eurynome.upms.api.feign.fallback;

import cn.herodotus.eurynome.upms.api.feign.service.IVerifyCaptchaService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VerifyCaptchaServiceFallback implements IVerifyCaptchaService {

    @Setter
    private Throwable cause;

    @Override
    public byte[] getVerifiCode() throws Exception {
        log.error("[Luban] |- VerifyCaptchaService getVerifiCode Invoke Failed!", cause);
        return new byte[0];
    }

    @Override
    public Boolean checkVerifiCode(String code) {
        log.error("[Luban] |- VerifyCaptchaService checkVerifiCode Invoke Failed!", cause);
        return Boolean.FALSE;
    }
}
