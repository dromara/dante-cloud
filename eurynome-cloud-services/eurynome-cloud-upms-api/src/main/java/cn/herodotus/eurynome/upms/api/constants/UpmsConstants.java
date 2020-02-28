package cn.herodotus.eurynome.upms.api.constants;

import cn.herodotus.eurynome.component.common.enums.OAuth2GrantType;
import cn.herodotus.eurynome.component.common.enums.StatusEnum;
import cn.herodotus.eurynome.upms.api.enums.ApplicationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpmsConstants {

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
