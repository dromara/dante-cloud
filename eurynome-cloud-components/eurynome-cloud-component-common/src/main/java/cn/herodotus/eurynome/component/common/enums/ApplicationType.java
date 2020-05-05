package cn.herodotus.eurynome.component.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 应用类别 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/4 12:01
 */
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


    private final Integer type;
    private final String description;

    private static final Map<Integer, ApplicationType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

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

    public Integer getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public static ApplicationType getApplicationType(Integer type) {
        return indexMap.get(type);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
