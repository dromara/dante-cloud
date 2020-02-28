package cn.herodotus.eurynome.upms.api.feign.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface IVerifyCaptchaService {
    /**
     * 生成图形验证码
     * @return
     * @throws Exception
     */
    @GetMapping("/captcha/getVerifiCode")
    byte[] getVerifiCode() throws Exception;

    /**
     * 验证
     * @param code
     * @return
     */
    @PostMapping("/captcha/checkVerifiCode")
    Boolean checkVerifiCode(String code);

}
