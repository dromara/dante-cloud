package cn.herodotus.eurynome.upms.api.constants;

import cn.herodotus.eurynome.common.enums.StatusEnum;
import cn.herodotus.eurynome.data.constants.CacheConstants;
import cn.herodotus.eurynome.security.definition.domain.GrantType;
import cn.herodotus.eurynome.common.enums.ApplicationType;
import cn.herodotus.eurynome.upms.api.constants.enums.Gender;
import cn.herodotus.eurynome.upms.api.constants.enums.Identity;
import cn.herodotus.eurynome.upms.api.constants.enums.SupplierType;
import cn.herodotus.eurynome.upms.api.constants.enums.TechnologyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : Upms服务常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 20:06
 */
public class UpmsConstants extends CacheConstants {

    /**
     * 服务名称
     */
    public static final String SERVICE_NAME = "eurynome-cloud-upms-ability";

    public static final String CACHE_AREA_PREFIX = "data:upms:";

    public static final String CACHE_NAME_WEAPP_USER = CACHE_AREA_PREFIX + "weapp:user:";

    public static final String CACHE_NAME_DEVELOPMENT_SUPPLIER = CACHE_AREA_PREFIX + "development:supplier:";

    public static final String CACHE_NAME_SYS_AUTHORITY = CACHE_AREA_PREFIX + "sys:authority:";
    public static final String CACHE_NAME_SYS_ROLE = CACHE_AREA_PREFIX + "sys:role:";
    public static final String CACHE_NAME_SYS_USER = CACHE_AREA_PREFIX + "sys:user:";
    public static final String CACHE_NAME_SYS_DEPARTMENT = CACHE_AREA_PREFIX + "sys:department:";
    public static final String CACHE_NAME_SYS_EMPLOYEE = CACHE_AREA_PREFIX + "sys:employee:";
    public static final String CACHE_NAME_SYS_ORGANIZATION = CACHE_AREA_PREFIX + "sys:organization:";
    public static final String CACHE_NAME_SYS_POSITION = CACHE_AREA_PREFIX + "sys:position:";

    public static final String CACHE_NAME_OAUTH_CLIENTDETAILS = CACHE_AREA_PREFIX + "oauth:clientdetails:";
    public static final String CACHE_NAME_OAUTH_APPLICATIONS = CACHE_AREA_PREFIX + "oauth:applications:";
    public static final String CACHE_NAME_OAUTH_SCOPES = CACHE_AREA_PREFIX + "oauth:scopes:";
    public static final String CACHE_NAME_OAUTH_MICROSERVICES = CACHE_AREA_PREFIX + "oauth:microservices:";

    private static final List<Map<String, Object>> STATUS_ENUM = StatusEnum.getToJsonStruct();
    private static final List<Map<String, Object>> APPLICATION_TYPE_ENUM = ApplicationType.getToJsonStruct();
    private static final List<Map<String, Object>> OAUTH2_GRANT_TYPE_ENUM = GrantType.getToJsonStruct();
    private static final List<Map<String, Object>> TECHNOLOGY_TYPE_ENUM = TechnologyType.getToJsonStruct();
    private static final List<Map<String, Object>> SUPPLIER_TYPE_ENUM = SupplierType.getToJsonStruct();
    private static final List<Map<String, Object>> GENDER_ENUM = Gender.getToJsonStruct();
    private static final List<Map<String, Object>> IDENTITY_ENUM = Identity.getToJsonStruct();


    public static Map<String, Object> getAllEnums() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", STATUS_ENUM);
        map.put("applicationType", APPLICATION_TYPE_ENUM);
        map.put("grantType", OAUTH2_GRANT_TYPE_ENUM);
        map.put("technologyType", TECHNOLOGY_TYPE_ENUM);
        map.put("supplierType", SUPPLIER_TYPE_ENUM);
        map.put("gender", GENDER_ENUM);
        map.put("identity", IDENTITY_ENUM);
        return map;
    }
}
