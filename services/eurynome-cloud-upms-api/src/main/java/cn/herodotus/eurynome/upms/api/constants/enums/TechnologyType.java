package cn.herodotus.eurynome.upms.api.constants.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "应用采用技术类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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

    @ApiModelProperty(value = "索引")
    private final Integer index;
    @ApiModelProperty(value = "文字")
    private final String text;

    private static final Map<Integer, TechnologyType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (TechnologyType technologyType : TechnologyType.values()) {
            indexMap.put(technologyType.ordinal(), technologyType);
            toJsonStruct.add(technologyType.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", technologyType.ordinal())
                            .put("key", technologyType.name())
                            .put("text", technologyType.getText())
                            .build());
        }
    }

    TechnologyType(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     *
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum索引
     */
    @JsonValue
    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    public static TechnologyType getTechnologyType(Integer type) {
        return indexMap.get(type);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
