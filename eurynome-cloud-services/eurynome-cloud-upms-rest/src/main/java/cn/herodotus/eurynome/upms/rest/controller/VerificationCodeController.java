package cn.herodotus.eurynome.upms.rest.controller;

import cn.herodotus.eurynome.component.common.enums.captcha.CaptchaType;
import cn.herodotus.eurynome.component.data.properties.SecurityProperties;
import cn.herodotus.eurynome.component.security.utils.SessionUtils;
import cn.herodotus.eurynome.upms.api.service.fegin.VerificationCodeFeignService;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;

@Slf4j
@Api(tags = "应用系统管理")
@RestController
public class VerificationCodeController implements VerificationCodeFeignService {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public Boolean checkVerificationCode(String code) {
        HttpSession session = SessionUtils.session();
        //read from nacos yml
        String text = (String) session.getAttribute(securityProperties.getVerificationCode().getSessionAttribute());
        log.warn("verify code is {}", text);
        if (StringUtils.isBlank(text)) {
            return Boolean.FALSE;
        }
        if (!code.equals(text)) {
            return Boolean.FALSE;
        }
        //从session中移除验证码text
        session.removeAttribute(securityProperties.getVerificationCode().getSessionAttribute());
        return Boolean.TRUE;
    }

    @Override
    public void getVerificationCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // 设置请求头为输出图片类型
        httpServletResponse.setContentType("image/gif");
        httpServletResponse.setHeader("Pragma", "No-cache");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        Captcha captcha = createCaptcha();
        // 验证码存入session
        //read from nacos yml
        SessionUtils.session().setAttribute(securityProperties.getVerificationCode().getSessionAttribute(), captcha.text().toLowerCase());
        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }

    private Captcha createCaptcha() throws IOException, FontFormatException {
        Captcha captcha;

        CaptchaType captchaType = securityProperties.getVerificationCode().getCaptchaType();
        switch (captchaType) {
            case LETTERS_GIF:
                captcha = new GifCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                captcha.setCharType(securityProperties.getVerificationCode().getCaptchaLetterType().getType());
                break;
            case CHINESE:
                captcha = new ChineseCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                break;
            case CHINESE_GIF:
                captcha = new ChineseGifCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                break;
            case ARITHMETIC:
                captcha = new ArithmeticCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                break;
            default:
                captcha = new SpecCaptcha(securityProperties.getVerificationCode().getWidth(), securityProperties.getVerificationCode().getHeight(), securityProperties.getVerificationCode().getLength());
                captcha.setCharType(securityProperties.getVerificationCode().getCaptchaLetterType().getType());
                break;
        }

        captcha.setFont(securityProperties.getVerificationCode().getCaptchaFont().getIndex());
        return captcha;
    }
}
