/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-kernel
 * File Name: HerodotusFeignSentinel.java
 * Author: gengwei.zheng
 * Date: 2021/12/04 15:35:04
 */

package cn.herodotus.eurynome.kernel.feign;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.alibaba.cloud.sentinel.feign.SentinelFeign;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>Description: 重写{@link SentinelFeign} 解决Feign与Sentinel冲突的问题 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/4 15:35
 */
public class HerodotusFeignSentinel {

    private HerodotusFeignSentinel() {

    }

    public static HerodotusFeignSentinel.Builder builder() {
        return new HerodotusFeignSentinel.Builder();
    }

    public static final class Builder extends Feign.Builder implements ApplicationContextAware {

        private Contract contract = new Contract.Default();

        private ApplicationContext applicationContext;

        private FeignContext feignContext;

        @Override
        public Feign.Builder invocationHandlerFactory(InvocationHandlerFactory invocationHandlerFactory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public HerodotusFeignSentinel.Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public Feign build() {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {
                @Override
                public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
                    // 查找 FeignClient 上的 降级策略
                    FeignClient feignClient = AnnotationUtils.findAnnotation(target.type(), FeignClient.class);
                    Class fallback = feignClient.fallback();
                    Class fallbackFactory = feignClient.fallbackFactory();

                    String beanName = feignClient.contextId();
                    if (!StringUtils.hasText(beanName)) {
                        beanName = feignClient.name();
                    }

                    Object fallbackInstance;
                    FallbackFactory fallbackFactoryInstance;
                    // check fallback and fallbackFactory properties
                    if (void.class != fallback) {
                        fallbackInstance = getFromContext(beanName, "fallback", fallback, target.type());
                        return new HerodotusSentinelInvocationHandler(target, dispatch,
                                new FallbackFactory.Default(fallbackInstance));
                    }
                    if (void.class != fallbackFactory) {
                        fallbackFactoryInstance = (FallbackFactory) getFromContext(beanName, "fallbackFactory",
                                fallbackFactory, FallbackFactory.class);
                        return new HerodotusSentinelInvocationHandler(target, dispatch, fallbackFactoryInstance);
                    }
                    return new HerodotusSentinelInvocationHandler(target, dispatch);
                }

                private Object getFromContext(String name, String type, Class fallbackType, Class targetType) {
                    Object fallbackInstance = feignContext.getInstance(name, fallbackType);
                    if (fallbackInstance == null) {
                        throw new IllegalStateException(String.format(
                                "No %s instance of type %s found for feign client %s", type, fallbackType, name));
                    }

                    if (!targetType.isAssignableFrom(fallbackType)) {
                        throw new IllegalStateException(String.format(
                                "Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                                type, fallbackType, targetType, name));
                    }
                    return fallbackInstance;
                }
            });

            super.contract(new SentinelContractHolder(contract));
            return super.build();
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
            feignContext = this.applicationContext.getBean(FeignContext.class);
        }

    }
}