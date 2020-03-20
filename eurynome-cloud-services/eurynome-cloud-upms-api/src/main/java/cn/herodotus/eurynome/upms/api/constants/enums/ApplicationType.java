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

    /**
     * 应用类型
     */
    WEB(0, "PC网页应用"),
    SERVICE(1, "服务应用"),
    APP(2, "手机应用"),
    WAP(3, "手机网页应用"),
    MINI(4, "小程序应用");


    private Integer type;
    private String description;

    private static Map<Integer, ApplicationType> indexMap = new HashMap<>();
    private static List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (ApplicationType applicationType : ApplicationType.values()) {
            indexMap.put(applicationType.type, applicationType);
            toJsonStruct.add(applicationType.type,
                    ImmutableMap.<String, Object>builder()
                            .put("value", applicationType.getType())
                            .put("key", applicationType.name())
                            .put("text", applicationType.description)
                            .build());
        }
    }


    ApplicationType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * 因此 使用@JsonValue @JsonDeserializer类里面要做相应的处理
     * @return
     */
    @JsonValue
    public Integer getType() {
        return this.type;
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
