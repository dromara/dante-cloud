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

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import feign.Feign;
import feign.InvocationHandlerFactory.MethodHandler;
import feign.MethodMetadata;
import feign.Target;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import static feign.Util.checkNotNull;

/**
 * {@link InvocationHandler} handle invocation that protected by Sentinel.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class HerodotusSentinelInvocationHandler implements InvocationHandler {

    private final Target<?> target;

    private final Map<Method, MethodHandler> dispatch;

    private FallbackFactory<?> fallbackFactory;

    private Map<Method, Method> fallbackMethodMap;

    HerodotusSentinelInvocationHandler(Target<?> target, Map<Method, MethodHandler> dispatch, FallbackFactory<?> fallbackFactory) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
        this.fallbackFactory = fallbackFactory;
        this.fallbackMethodMap = toFallbackMethod(dispatch);
    }

    HerodotusSentinelInvocationHandler(Target<?> target, Map<Method, MethodHandler> dispatch) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
    }

    static Map<Method, Method> toFallbackMethod(Map<Method, MethodHandler> dispatch) {
        Map<Method, Method> result = new LinkedHashMap<>();
        for (Method method : dispatch.keySet()) {
            method.setAccessible(true);
            result.put(method, method);
        }
        return result;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        switch (method.getName()) {
            case "equals" -> {
                try {
                    Object otherHandler = args.length > 0 && args[0] != null
                            ? Proxy.getInvocationHandler(args[0])
                            : null;
                    return equals(otherHandler);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
            case "hashCode" -> {
                return hashCode();
            }
            case "toString" -> {
                return toString();
            }
        }

        Object result;
        MethodHandler methodHandler = this.dispatch.get(method);
        // only handle by HardCodedTarget
        if (target instanceof Target.HardCodedTarget<?> hardCodedTarget) {
            MethodMetadata methodMetadata = SentinelContractHolder.METADATA_MAP
                    .get(hardCodedTarget.type().getName()
                            + Feign.configKey(hardCodedTarget.type(), method));
            // resource default is HttpMethod:protocol://url
            if (methodMetadata == null) {
                result = methodHandler.invoke(args);
            } else {
                String resourceName = methodMetadata.template().method().toUpperCase()
                        + ":" + hardCodedTarget.url() + methodMetadata.template().path();
                Entry entry = null;
                try {
                    ContextUtil.enter(resourceName);
                    entry = SphU.entry(resourceName, EntryType.OUT, 1, args);
                    result = methodHandler.invoke(args);
                } catch (Throwable ex) {
                    // fallback handle
                    if (!BlockException.isBlockException(ex)) {
                        Tracer.traceEntry(ex, entry);
                    }
                    if (fallbackFactory != null) {
                        try {
                            return fallbackMethodMap.get(method).invoke(fallbackFactory.create(ex), args);
                        } catch (IllegalAccessException e) {
                            // shouldn't happen as method is public due to being an
                            // interface
                            throw new AssertionError(e);
                        } catch (InvocationTargetException e) {
                            throw new AssertionError(e.getCause());
                        }
                    } else {
                        // throw exception if fallbackFactory is null
                        throw ex;
                    }
                } finally {
                    if (entry != null) {
                        entry.exit(1, args);
                    }
                    ContextUtil.exit();
                }
            }
        } else {
            // other target type using default strategy
            result = methodHandler.invoke(args);
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HerodotusSentinelInvocationHandler sentinelInvocationHandler) {
            return target.equals(sentinelInvocationHandler.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }

}
