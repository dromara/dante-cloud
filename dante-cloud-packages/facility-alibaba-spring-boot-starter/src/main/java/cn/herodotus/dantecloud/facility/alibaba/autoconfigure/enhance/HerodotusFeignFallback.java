/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dantecloud.facility.alibaba.autoconfigure.enhance;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.domain.Result;
import cn.herodotus.dante.core.exception.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>Description: 统一 fallback 实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/30 15:12
 */
public class HerodotusFeignFallback<T> implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HerodotusFeignFallback.class);

    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    public HerodotusFeignFallback(Class<T> targetType, String targetName, Throwable cause) {
        this.targetType = targetType;
        this.targetName = targetName;
        this.cause = cause;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        String errorMessage = cause.getMessage();
        String path = targetType.getName() + SymbolConstants.FORWARD_SLASH + method.getName();

        Result<String> result = GlobalExceptionHandler.resolveException((Exception) cause, path);
        log.error("[Herodotus] |- Feign remote call fallback : [{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusFeignFallback<?> that = (HerodotusFeignFallback<?>) o;
        return Objects.equals(targetType, that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(targetType);
    }
}
