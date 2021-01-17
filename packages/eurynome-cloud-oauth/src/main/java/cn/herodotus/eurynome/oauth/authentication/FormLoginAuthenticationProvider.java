/*
 * Copyright (c) 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-oauth
 * File Name: FormLoginAuthenticationProvider.java
 * Author: gengwei.zheng
 * Date: 2021/1/17 上午10:39
 * LastModified: 2021/1/17 上午9:22
 */

package cn.herodotus.eurynome.oauth.authentication;

import cn.herodotus.eurynome.oauth.exception.VerificationCodeIsEmptyException;
import cn.herodotus.eurynome.oauth.exception.VerificationCodeIsNotExistException;
import cn.herodotus.eurynome.oauth.exception.VerificationCodeIsNotRightException;
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
public class FormLoginAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        FormLoginWebAuthenticationDetails details = (FormLoginWebAuthenticationDetails) authentication.getDetails();

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
