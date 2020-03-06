package cn.herodotus.eurynome.component.security.authentication;

import cn.herodotus.eurynome.component.security.properties.SecurityProperties;
import cn.herodotus.eurynome.component.security.utils.SymmetricUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 14:29
 */
public class FormLoginWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean codeRight = true;
    private boolean codeEmpty = false;
    private boolean codeNotExist = false;

    private SecurityProperties securityProperties;

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public FormLoginWebAuthenticationDetails(HttpServletRequest request, SecurityProperties securityProperties) {
        super(request);
        this.securityProperties = securityProperties;
        checkVerificationCode(request);
    }

    private void checkVerificationCode(HttpServletRequest request) {
        String encryptedCode = request.getParameter(securityProperties.getVerificationCode().getVerificationCodeParameter());

        String key = request.getParameter("symmetric");

        HttpSession session = request.getSession();
        String savedCode = (String) session.getAttribute(securityProperties.getVerificationCode().getSessionAttribute());

        if (!checkCodeExist(savedCode) && !checkCodeEmpty(encryptedCode)) {
            checkCodeRight(encryptedCode, savedCode, key);
        }
    }

    private boolean checkCodeExist(String savedCode) {
        codeNotExist = StringUtils.isEmpty(savedCode);
        return codeNotExist;
    }

    private boolean checkCodeEmpty(String code) {
        codeEmpty = StringUtils.isEmpty(code);
        return codeEmpty;
    }

    private boolean checkCodeRight(String encryptedCode, String savedCode, String key) {
        byte[] byteKey = SymmetricUtils.getDecryptedSymmetricKey(key);
        String code = SymmetricUtils.decrypt(encryptedCode, byteKey);
        codeRight = StringUtils.equals(code, savedCode);
        return codeRight;
    }

    public boolean isCodeRight() {
        return codeRight;
    }

    public boolean isCodeEmpty() {
        return codeEmpty;
    }

    public boolean isCodeNotExist() {
        return codeNotExist;
    }

    public boolean isClose() {
        return securityProperties.getVerificationCode().isClosed();
    }
}
