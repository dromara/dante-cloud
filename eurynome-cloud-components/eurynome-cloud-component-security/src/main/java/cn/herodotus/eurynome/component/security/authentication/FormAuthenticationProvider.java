package cn.herodotus.eurynome.component.security.authentication;

import cn.herodotus.eurynome.component.security.exception.VerificationCodeIsEmptyException;
import cn.herodotus.eurynome.component.security.exception.VerificationCodeIsNotExistException;
import cn.herodotus.eurynome.component.security.exception.VerificationCodeIsNotRightException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p> Description : Security Form登录提供者。 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/16 18:01
 */
public class FormAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        FormWebAuthenticationDetails details = (FormWebAuthenticationDetails) authentication.getDetails();

        if (!details.isClose()) {

            if (details.isCodeEmpty()) {
                throw new VerificationCodeIsEmptyException("Please Input Verification Code");
            }

            if (details.isCodeNotExist()) {
                throw new VerificationCodeIsNotExistException("Verification Code is Not Exist, Please Check The Session Storage Operation");
            }

            if (!details.isCodeRight()) {
                throw new VerificationCodeIsNotRightException("Verification Code is Not Right, Please Retry!");
            }
        }

        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
