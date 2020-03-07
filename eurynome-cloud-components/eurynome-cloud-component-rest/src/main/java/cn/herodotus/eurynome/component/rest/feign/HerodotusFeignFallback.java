package cn.herodotus.eurynome.component.rest.feign;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.exception.GlobalExceptionHandler;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p> Description : 自定义通用的Feign Fallback处理工厂 </p>
 * <p>
 * {@see: https://blog.csdn.net/ttzommed/article/details/90669320}
 *
 * @author : gengwei.zheng
 * @date : 2020/3/1 18:09
 */
@Slf4j
@AllArgsConstructor
public class HerodotusFeignFallback<T> implements MethodInterceptor {

    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String errorMessage = cause.getMessage();

        log.error("[Herodotus] |- Feign Fallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);

        if (method.getReturnType() == Result.class) {
            Result<String> result = new Result<>();
            if (cause instanceof FeignException) {
                FeignException exception = (FeignException) cause;
                result.failed()
                        .httpStatus(exception.status())
                        .message(exception.getMessage())
                        .error(cause);
            } else {
                result = GlobalExceptionHandler.resolveException((Exception) cause, method.getName());
            }

            return result;
        } else {
            log.warn("[Herodotus] |- Suggest use Result Entity as fegin interface return type, OR customize your own Feign fallBackFactory!");
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusFeignFallback<?> that = (HerodotusFeignFallback<?>) o;
        return targetType.equals(that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType);
    }
}
