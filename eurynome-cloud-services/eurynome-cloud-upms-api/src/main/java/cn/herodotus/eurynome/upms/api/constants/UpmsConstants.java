package cn.herodotus.eurynome.upms.api.constants;

import cn.herodotus.eurynome.component.security.oauth2.GrantType;
import cn.herodotus.eurynome.component.common.enums.StatusEnum;
import cn.herodotus.eurynome.upms.api.constants.enums.ApplicationType;
import cn.herodotus.eurynome.upms.api.constants.enums.TechnologyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 20:06
 */
public class UpmsConstants {

    public static final int DEFAULT_UPMS_CACHE_EXPIRE = 86400;
    public static final int DEFAULT_UPMS_LOCAL_LIMIT = 1000;


    public static final String INDEX_CACHE_NAME = "index:";

    public static final String CACHE_NAME_SYSACCOUNT = "data:upms:sysaccount:";
    public static final String CACHE_NAME_SYSACCOUNT_INDEX = CACHE_NAME_SYSACCOUNT + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_SYS_APPLICATION = "data:upms:sys:application:";
    public static final String CACHE_NAME_SYS_APPLICATION_INDEX = CACHE_NAME_SYS_APPLICATION + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_AUTHORITY = "data:upms:sys:authority:";
    public static final String CACHE_NAME_SYS_AUTHORITY_INDEX = CACHE_NAME_SYS_AUTHORITY + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_ROLE = "data:upms:sys:role:";
    public static final String CACHE_NAME_SYS_ROLE_INDEX = CACHE_NAME_SYS_ROLE + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_USER = "data:upms:sys:user:";
    public static final String CACHE_NAME_SYS_USER_INDEX = CACHE_NAME_SYS_USER + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_OAUTH_CLIENTDETAILS = "data:upms:oauth:clientdetails:";
    public static final String CACHE_NAME_OAUTH_CLIENTDETAILS_INDEX = CACHE_NAME_OAUTH_CLIENTDETAILS + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_OAUTH_APPLICATIONS = "data:upms:oauth:applications:";
    public static final String CACHE_NAME_OAUTH_APPLICATIONS_INDEX = CACHE_NAME_OAUTH_APPLICATIONS + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_OAUTH_SCOPES = "data:upms:oauth:scopes:";
    public static final String CACHE_NAME_OAUTH_SCOPES_INDEX = CACHE_NAME_OAUTH_SCOPES + INDEX_CACHE_NAME;

    private static final List<Map<String, Object>> STATUS_ENUM = StatusEnum.getToJsonStruct();
    private static final List<Map<String, Object>> APPLICATION_TYPE_ENUM = ApplicationType.getToJsonStruct();
    private static final List<Map<String, Object>> OAUTH2_GRANT_TYPE_ENUM = GrantType.getToJsonStruct();
    private static final List<Map<String, Object>> TECHNOLOGY_TYPE_ENUM = TechnologyType.getToJsonStruct();


    public static Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", STATUS_ENUM);
        map.put("applicationType", APPLICATION_TYPE_ENUM);
        map.put("grantType", OAUTH2_GRANT_TYPE_ENUM);
        map.put("technologyType", TECHNOLOGY_TYPE_ENUM);
        return map;
    }
}
