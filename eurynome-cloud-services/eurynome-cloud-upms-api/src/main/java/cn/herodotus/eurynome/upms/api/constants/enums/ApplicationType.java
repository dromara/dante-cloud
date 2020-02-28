package cn.herodotus.eurynome.upms.api.constants.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplicationType {

    WEB(0, "常规Web应用"),
    NODE(1, "纯网页应用"),
    MOBILE(2, "手机移动端"),
    WEAPP(3, "微信小程序");


    private Integer status;
    private String description;

    private static Map<Integer, ApplicationType> indexMap = new HashMap<>();
    private static List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (ApplicationType applicationType : ApplicationType.values()) {
            indexMap.put(applicationType.status, applicationType);
            toJsonStruct.add(applicationType.status,
                    ImmutableMap.<String, Object>builder()
                            .put("value", applicationType.getStatus())
                            .put("key", applicationType.name())
                            .put("text", applicationType.description)
                            .build());
        }
    }


    ApplicationType(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * 因此 使用@JsonValue @JsonDeserializer类里面要做相应的处理
     * @return
     */
    @JsonValue
    public Integer getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public static ApplicationType getStatus(Integer status) {
        return indexMap.get(status);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
