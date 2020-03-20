package cn.herodotus.eurynome.upms.api.constants;

import cn.herodotus.eurynome.component.security.enums.OAuth2GrantType;
import cn.herodotus.eurynome.component.common.enums.StatusEnum;
import cn.herodotus.eurynome.upms.api.constants.enums.ApplicationType;

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

    public static final String INDEX_CACHE_NAME = "index:";

    public static final String CACHE_NAME_SYSACCOUNT = "data:upms:sysaccount:";
    public static final String CACHE_NAME_SYSACCOUNT_INDEX = CACHE_NAME_SYSACCOUNT + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_SYSAPPLICATION = "data:upms:sysapplication:";
    public static final String CACHE_NAME_SYSAPPLICATION_INDEX = CACHE_NAME_SYSAPPLICATION + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYSAUTHORITY = "data:upms:sysauthority:";
    public static final String CACHE_NAME_SYSAUTHORITY_INDEX = CACHE_NAME_SYSAUTHORITY + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYSROLE = "data:upms:sysrole:";
    public static final String CACHE_NAME_SYSROLE_INDEX = CACHE_NAME_SYSROLE + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYSUSER = "data:upms:sysuser:";
    public static final String CACHE_NAME_SYSUSER_INDEX = CACHE_NAME_SYSUSER + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_OAUTHCLIENTDETAIL = "data:upms:oauthclientdetails:";
    public static final String CACHE_NAME_OAUTHCLIENTDETAIL_INDEX = CACHE_NAME_OAUTHCLIENTDETAIL + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_OAUTHSCOPE = "data:upms:oauthscopes:";
    public static final String CACHE_NAME_OAUTHSCOPE_INDEX = CACHE_NAME_OAUTHSCOPE + INDEX_CACHE_NAME;

    private static final List<Map<String, Object>> STATUS_ENUM = StatusEnum.getToJsonStruct();
    private static final List<Map<String, Object>> APPLICATION_TYPE_ENUM = ApplicationType.getToJsonStruct();
    private static final List<Map<String, Object>> OAUTH2_GRANT_TYPE_ENUM = OAuth2GrantType.getToJsonStruct();


    public static Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", STATUS_ENUM);
        map.put("applicationType", APPLICATION_TYPE_ENUM);
        map.put("grantType", OAUTH2_GRANT_TYPE_ENUM);
        return map;
    }
}
