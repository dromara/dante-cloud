package cn.herodotus.eurynome.component.rest.feign;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/1 18:06
 */
@AllArgsConstructor
public class HerodotusFallbackFactory<T> implements FallbackFactory<T> {

    private final Target<T> target;

    @Override
    @SuppressWarnings("unchecked")
    public T create(Throwable throwable) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new HerodotusFeignFallback<>(targetType, targetName, throwable));
        return (T) enhancer.create();
    }
}
