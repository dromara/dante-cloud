package cn.herodotus.eurynome.uaa.service;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.security.core.userdetails.HerodotusUserDetails;
import cn.herodotus.eurynome.security.utils.ThreadLocalContextUtils;
import cn.herodotus.eurynome.upms.api.service.remote.RemoteSysUserService;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class OauthUserDetailsService implements UserDetailsService {

    @Autowired
    private RemoteSysUserService remoteSysUserService;

    @Override
    public HerodotusUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        String tenantId = ThreadLocalContextUtils.getString("tenantId");

        log.debug("[Herodotus] |- UserDetailsService Fetched tenantId is : [{}]", tenantId);

        Result<SysUser> result = remoteSysUserService.findByUsername(username);

        SysUser sysUser = result.getData();

        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }

        ThreadLocalContextUtils.clear();

        return UpmsHelper.convertSysUserToHerodotusUserDetails(sysUser);
    }
}
