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
    public static final String CACHE_AREA_PREFIX = "data:upms:";

    public static final String CACHE_NAME_WEAPP_USER = CACHE_AREA_PREFIX + "weapp:user:";
    public static final String CACHE_NAME_WEAPP_USER_INDEX = CACHE_NAME_WEAPP_USER + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_MICROSERVICE_SUPPLIER = CACHE_AREA_PREFIX + "microservice:supplier:";
    public static final String CACHE_NAME_MICROSERVICE_SUPPLIER_INDEX = CACHE_NAME_MICROSERVICE_SUPPLIER + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_SYS_AUTHORITY = CACHE_AREA_PREFIX + "sys:authority:";
    public static final String CACHE_NAME_SYS_AUTHORITY_INDEX = CACHE_NAME_SYS_AUTHORITY + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_ROLE = CACHE_AREA_PREFIX + "sys:role:";
    public static final String CACHE_NAME_SYS_ROLE_INDEX = CACHE_NAME_SYS_ROLE + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_USER = CACHE_AREA_PREFIX + "sys:user:";
    public static final String CACHE_NAME_SYS_USER_INDEX = CACHE_NAME_SYS_USER + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_DEPARTMENT = CACHE_AREA_PREFIX + "sys:department:";
    public static final String CACHE_NAME_SYS_DEPARTMENT_INDEX = CACHE_NAME_SYS_DEPARTMENT + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_EMPLOYEE = CACHE_AREA_PREFIX + "sys:employee:";
    public static final String CACHE_NAME_SYS_EMPLOYEE_INDEX = CACHE_NAME_SYS_EMPLOYEE + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_ORGANIZATION = CACHE_AREA_PREFIX + "sys:organization:";
    public static final String CACHE_NAME_SYS_ORGANIZATION_INDEX = CACHE_NAME_SYS_ORGANIZATION + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_SYS_POSITION = CACHE_AREA_PREFIX + "sys:position:";
    public static final String CACHE_NAME_SYS_POSITION_INDEX = CACHE_NAME_SYS_POSITION + INDEX_CACHE_NAME;

    public static final String CACHE_NAME_OAUTH_CLIENTDETAILS = CACHE_AREA_PREFIX + "oauth:clientdetails:";
    public static final String CACHE_NAME_OAUTH_CLIENTDETAILS_INDEX = CACHE_NAME_OAUTH_CLIENTDETAILS + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_OAUTH_APPLICATIONS = CACHE_AREA_PREFIX + "oauth:applications:";
    public static final String CACHE_NAME_OAUTH_APPLICATIONS_INDEX = CACHE_NAME_OAUTH_APPLICATIONS + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_OAUTH_SCOPES = CACHE_AREA_PREFIX + "oauth:scopes:";
    public static final String CACHE_NAME_OAUTH_SCOPES_INDEX = CACHE_NAME_OAUTH_SCOPES + INDEX_CACHE_NAME;
    public static final String CACHE_NAME_OAUTH_MICROSERVICES = CACHE_AREA_PREFIX + "oauth:microservices:";
    public static final String CACHE_NAME_OAUTH_MICROSERVICES_INDEX = CACHE_NAME_OAUTH_MICROSERVICES + INDEX_CACHE_NAME;

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
