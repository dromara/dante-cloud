package cn.herodotus.eurynome.kernel.feign;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.exception.GlobalExceptionHandler;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p> Description : 自定义通用的Feign Fallback处理器 </p>
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

    @Nullable
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String errorMessage = cause.getMessage();

        log.error("[Eurynome] |- Feign Fallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);

        if (method.getReturnType() == Result.class) {
            Result<String> result = new Result<>();
            if (cause instanceof FeignException) {
                FeignException exception = (FeignException) cause;
                result.failed()
                        .status(exception.status())
                        .message(exception.getMessage())
                        .error(cause);
            } else {
                result = GlobalExceptionHandler.resolveException((Exception) cause, method.getName());
            }

            return result;
        } else {
            log.warn("[Eurynome] |- Suggest use Result Entity as fegin interface return type, OR customize your own Feign fallBackFactory!");
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
