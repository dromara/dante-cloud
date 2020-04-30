package cn.herodotus.eurynome.upms.api.constants.enums;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Description : 供应商类型 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/19 16:17
 */
public enum SupplierType {

    /**
     * enum
     */
    CORE(0, "平台核心团队"),
    BAT(1, "互联网大厂"),
    THIRD_PARTY(2, "第三方企业"),
    Outsourcing(3, "外包团队");

    private final Integer type;
    private final String description;

    private static final Map<Integer, SupplierType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (SupplierType supplierType : SupplierType.values()) {
            indexMap.put(supplierType.type, supplierType);
            toJsonStruct.add(supplierType.type,
                    ImmutableMap.<String, Object>builder()
                            .put("value", supplierType.getType())
                            .put("key", supplierType.name())
                            .put("text", supplierType.description)
                            .build());
        }
    }

    SupplierType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static SupplierType getSupplierType(Integer type) {
        return indexMap.get(type);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
