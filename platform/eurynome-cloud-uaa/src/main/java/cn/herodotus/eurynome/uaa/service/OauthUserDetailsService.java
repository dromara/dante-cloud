package cn.herodotus.eurynome.uaa.service;

import cn.herodotus.eurynome.crud.repository.BaseRepository;
import cn.herodotus.eurynome.crud.service.BaseService;
import cn.herodotus.eurynome.security.definition.core.HerodotusUserDetails;
import cn.herodotus.eurynome.upms.api.constants.UpmsConstants;
import cn.herodotus.eurynome.upms.api.entity.system.SysUser;
import cn.herodotus.eurynome.upms.api.helper.UpmsHelper;
import cn.herodotus.eurynome.upms.api.repository.system.SysUserRepository;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>Description: UserDetailsService核心类 </p>
 *
 * 之前一直使用Fegin进行UserDetailsService的远程调用。现在直接改为数据库访问。主要原因是：
 * 1. 根据目前的设计，Oauth的表与系统权限相关的表是在一个库中的。因此UAA和UPMS分开是为了以后提高新能考虑，逻辑上没有必要分成两个服务。
 * 2. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，调用频繁增加一道远程调用增加消耗而已。
 * 3. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，只是UAA在使用。
 * 4. UserDetailsService 和 ClientDetailsService 是Oauth核心内容，是各种验证权限之前必须调用的内容。
 *    一方面：使用feign的方式调用，只能采取作为白名单的方式，安全性无法保证。
 *    另一方面：会产生调用的循环。
 * 因此，最终考虑把这两个服务相关的代码，抽取至UPMS API，采用UAA直接访问数据库的方式。
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 11:02
 */
@Slf4j
@Service
public class OauthUserDetailsService extends BaseService<SysUser, String> implements UserDetailsService {

    private static final String CACHE_NAME = UpmsConstants.CACHE_NAME_SYS_USER;

    @CreateCache(name = CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, SysUser> dataCache;

    @CreateCache(name = CACHE_NAME + UpmsConstants.INDEX_CACHE_NAME, expire = UpmsConstants.DEFAULT_UPMS_CACHE_EXPIRE, cacheType = CacheType.BOTH, localLimit = UpmsConstants.DEFAULT_UPMS_LOCAL_LIMIT)
    private Cache<String, Set<String>> indexCache;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public Cache<String, SysUser> getCache() {
        return dataCache;
    }

    @Override
    public Cache<String, Set<String>> getIndexCache() {
        return indexCache;
    }

    @Override
    public BaseRepository<SysUser, String> getRepository() {
        return sysUserRepository;
    }

    @Override
    public HerodotusUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = findByUserName(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }

        log.debug("[Herodotus] |- UserDetailsService loaded user : [{}]", username);

        return UpmsHelper.convertSysUserToHerodotusUserDetails(sysUser);
    }

    private SysUser findByUserName(String userName) {
        SysUser sysUser = readOneFromCacheByLink(userName);
        if (ObjectUtils.isEmpty(sysUser)) {
            sysUser = sysUserRepository.findByUserName(userName);
            writeToCache(sysUser);
        }
        log.debug("[Herodotus] |- SysUser Service findSysUserByUserName.");
        return sysUser;
    }
}
