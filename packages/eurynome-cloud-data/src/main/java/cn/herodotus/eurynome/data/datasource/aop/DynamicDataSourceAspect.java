/*
 * Copyright (c) 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-data
 * File Name: DynamicDataSourceAnnotationInterceptor.java
 * Author: gengwei.zheng
 * Date: 2020/5/24 上午7:36
 * LastModified: 2020/5/24 上午7:36
 */

package cn.herodotus.eurynome.data.datasource.aop;

import cn.herodotus.eurynome.data.datasource.DynamicDataSourceContextHolder;
import cn.herodotus.eurynome.data.datasource.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DynamicDataSourceAnnotationInterceptor </p>
 *
 * <p>Description: DataSource 注解切面 </p>
 *
 * · @Order(-1) 使得在事务的AOP外层拦截
 *
 * @author : gengwei.zheng
 * @date : 2020/5/24 7:36
 */
@Slf4j
@Order(Integer.MAX_VALUE - 2000)
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Around(value = "@within(cn.herodotus.eurynome.data.datasource.annotation.DataSource)")
    public Object classAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return switchDataSource(joinPoint);
    }

    @Around(value = "@annotation(cn.herodotus.eurynome.data.datasource.annotation.DataSource)")
    public Object methodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return switchDataSource(joinPoint);
    }

    private Object switchDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        String dataSourceName = getDataSourceAnnotation(joinPoint).value();
        DynamicDataSourceContextHolder.push(dataSourceName);
        log.debug("[Herodotus] |- @DataSource annotation switch datasource to [{}]", dataSourceName);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 根据类或方法获取数据源注解
     */
    private DataSource getDataSourceAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSource methodAnnotation =  methodSignature.getMethod().getAnnotation(DataSource.class);
        // 先判断方法的注解，再判断类注解
        if (ObjectUtils.isNotEmpty(methodAnnotation)) {
            return methodAnnotation;
        } else {
            Class<?> targetClass = joinPoint.getTarget().getClass();
           return targetClass.getAnnotation(DataSource.class);
        }
    }
}
