package cn.herodotus.eurynome.upms.api.constants.enums;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : 应用技术类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:17
 */
public enum TechnologyType {

    /**
     * enum
     */
    JAVA(0, "Java PC网页应用"),
    NET(1, ".Net PC网页应用"),
    PHP(2, "PHP PC网页应用"),
    NODE(3, "前后端分离 PC网页应用"),
    IOS(4, "苹果手机应用"),
    ANDROID(5, "安卓手机应用"),
    WEAPP(6, "微信小程序应用"),
    ALIAPP(7, "支付宝小程序应用"),
    DUAPP(8, "百度小程序应用");

    private final Integer type;
    private final String description;

    private static final Map<Integer, TechnologyType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (TechnologyType technologyType : TechnologyType.values()) {
            indexMap.put(technologyType.type, technologyType);
            toJsonStruct.add(technologyType.type,
                    ImmutableMap.<String, Object>builder()
                            .put("value", technologyType.getType())
                            .put("key", technologyType.name())
                            .put("text", technologyType.description)
                            .build());
        }
    }

    TechnologyType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static TechnologyType getTechnologyType(Integer type) {
        return indexMap.get(type);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
