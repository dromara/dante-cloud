package cn.herodotus.eurynome.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * <p> Description : 自定义的Bean工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/11 13:36
 */
@Slf4j
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    /**
     * 方法说明：map转化为对象
     * 
     * @param map
     * @param t
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> t) {
        try {
            T instance = t.newInstance();
            populate(instance, map);
            return instance;
        } catch (Exception e) {
            log.error("[Eurynome] |- BeanUtils mapToBean error！", e);
            return null;
        }
    }
}
