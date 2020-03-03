package cn.herodotus.eurynome.upms.api.service.fegin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description: VerificationCodeFeignService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/3 16:19
 */
public interface VerificationCodeFeignService {
    /**
     * 生成图形验证码
     * @throws Exception
     */
    /**
     * 生成图形验证码
     * @param httpServletRequest 请求对象
     * @param httpServletResponse 响应对象
     * @throws Exception 绘制错误
     */
    @GetMapping("/captcha")
    void getVerificationCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;

    /**
     * 验证
     * @param code 传输代码
     * @return 是否正确
     */
    @PostMapping("/captcha")
    Boolean checkVerificationCode(@RequestBody String code);

}
