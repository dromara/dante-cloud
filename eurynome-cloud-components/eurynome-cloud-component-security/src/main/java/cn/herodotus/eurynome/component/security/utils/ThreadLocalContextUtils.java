package cn.herodotus.eurynome.component.security.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/5 17:22
 */
public class ThreadLocalContextUtils {

    private static final String TENANT_ID = "tenantId";

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        set(TENANT_ID, tenantId);
    }

    public static String getTenantId() {
        return getString(TENANT_ID);
    }

    public static String getString(String attribute) {
        Object object = get(attribute);
        if (ObjectUtils.isNotEmpty(object) && object instanceof String) {
            return (String) object;
        }

        return null;
    }

    /**
     * 获得线程中保存的属性.
     *
     * @param attribute 属性名称
     * @return 属性值
     */
    public static Object get(String attribute) {
        Map<String, Object> map = threadLocal.get();
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        return map.get(attribute);
    }

    public static void set(String attribute, Object value) {
        Map<String, Object> map = threadLocal.get();

        if (MapUtils.isEmpty(map)) {
            map = new ConcurrentHashMap<>(8);
        }
        map.put(attribute, value);
        threadLocal.set(map);
    }

    /**
     * 清除线程中保存的数据
     */
    public static void clear() {
        threadLocal.remove();
    }

}
