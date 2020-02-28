package cn.herodotus.eurynome.component.data.cache;

import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * 自定义 Redis Caffeine缓存Key生成规则，用于解决手动通过注解编写Key
 * @author gengwei.zheng
 * @date 2019.09
 */
@Slf4j
public class RedisCaffeineCacheKeyGenerator implements KeyGenerator {

    private static final int NO_PARAM_KEY = 0;
    private static final int NULL_PARAM_KEY = 53;

    @Override
    public Object generate(Object target, Method method, Object... params) {

        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(SymbolConstants.PERIOD).append(method.getName()).append(SymbolConstants.COLON);
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
                log.warn("[Luban] |- RedisCaffeineCache : input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(SymbolConstants.COMMA);
                }
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                key.append(param);
            } else {
                log.warn("[Luban] |- RedisCaffeineCache : Using an object as a cache key may lead to unexpected results. " +
                        "Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append(SymbolConstants.DASH);
        }

        String finalKey = key.toString();
        long cacheKeyHash = Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
        log.debug("[Luban] |- RedisCaffeineCache : using cache key={} hashCode={}", finalKey, cacheKeyHash);
        return key.toString();
    }
}
