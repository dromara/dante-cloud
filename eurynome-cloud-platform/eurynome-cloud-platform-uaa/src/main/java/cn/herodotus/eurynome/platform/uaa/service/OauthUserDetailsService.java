package cn.herodotus.eurynome.platform.uaa.service;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.domain.HerodotusUserDetails;
import cn.herodotus.eurynome.platform.uaa.service.feign.SysUserRemoteService;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** 
 * <p>Description: TODO </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:02
 */
@Service
public class OauthUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserRemoteService sysUserRemoteService;

    @Override
    public HerodotusUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Result<SysUser> result = sysUserRemoteService.findByUsername(username);

        SysUser sysUser = result.getData();

        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }

        return UpmsHelper.convertSysUserToArtisanUserDetails(sysUser);
    }
}
