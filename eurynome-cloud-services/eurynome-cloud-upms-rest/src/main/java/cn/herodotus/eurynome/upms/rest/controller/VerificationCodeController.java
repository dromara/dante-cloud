package cn.herodotus.eurynome.upms.rest.controller;

import cn.herodotus.eurynome.component.security.utils.SessionUtils;
import cn.herodotus.eurynome.component.security.utils.VerificationCodeUtils;
import cn.herodotus.eurynome.upms.api.feign.service.IVerifyCaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Slf4j
@Api(tags = "应用系统管理")
@RestController
public class VerificationCodeController implements IVerifyCaptchaService {
    @Value("${captcha.session-name}")
    private String sessionName;

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Override
    public byte[] getVerifiCode() throws Exception {
        //证码字符串生成验
        String text = defaultKaptcha.createText();
        //验证码存入session（可以将text加密储存）
        log.warn("verify code is {}",text);
        SessionUtils.session().setAttribute(sessionName, text);//read from nacos yml
        //验证码转换
        BufferedImage image = defaultKaptcha.createImage(text);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        //定义响应值，写入byte
        byte[] bytes = byteArrayOutputStream.toByteArray();
        VerificationCodeUtils.buildImageResponse(bytes);
        return bytes;

    }

    @Override
    public Boolean checkVerifiCode(String code) {
        HttpSession session = SessionUtils.session();
        String text = (String) session.getAttribute(sessionName);//read from nacos yml
        log.warn("verify code is {}",text);
        if (StringUtils.isBlank(text)) {
            return Boolean.FALSE;
        }
        if (!code.equals(text)) {
            return Boolean.FALSE;
        }
        //从session中移除验证码text
        session.removeAttribute(sessionName);//read from nacos yml
        return Boolean.TRUE;
    }
}
