/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: ResourceAnnotationScan.java
 * Author: gengwei.zheng
 * Date: 2019/11/21 上午11:43
 * LastModified: 2019/11/18 下午2:42
 */

package cn.herodotus.eurynome.component.rest.annotation;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import cn.herodotus.eurynome.component.common.definition.RequestMappingStore;
import cn.herodotus.eurynome.component.common.domain.RequestMappingResource;
import cn.herodotus.eurynome.component.rest.enums.Architecture;
import cn.herodotus.eurynome.component.rest.properties.ApplicationProperties;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 微服务Controller注解扫描，根据扫描结果生成权限信息，通过消息队列传递到用户中心
 *
 * @author gengwei.zheng
 * @date 2019.09
 */
@Slf4j
public class RequestMappingScan implements ApplicationListener<ApplicationReadyEvent> {

    private ApplicationProperties applicationProperties;
    private RequestMappingStore requestMappingStore;
    private Class<? extends Annotation> scanAnnotationClass;

    public RequestMappingScan(RequestMappingStore requestMappingStore, ApplicationProperties applicationProperties, Class<? extends Annotation> scanAnnotationClass) {
        this.applicationProperties = applicationProperties;
        this.requestMappingStore = requestMappingStore;
        this.scanAnnotationClass = scanAnnotationClass;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        if (ObjectUtils.isNotEmpty(requestMappingStore) && applicationProperties.getRequestMapping().isRegisterRequestMapping()) {

            ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();

            // 1、获取服务ID：该服务ID对于微服务是必需的，对于Hammer只是备用。
            Environment environment = applicationContext.getEnvironment();
            String serviceId = environment.getProperty("spring.application.name", "application");

            // 2、只针对有EnableResourceServer注解的微服务进行扫描。Hammer目前不会用到EnableResourceServer所以增加的了一个Architecture判断
            if (applicationProperties.getArchitecture() == Architecture.MICROSERVICE) {
                Map<String, Object> resourceServer = applicationContext.getBeansWithAnnotation(scanAnnotationClass);
                if (MapUtils.isEmpty(resourceServer)) {
                    // 只扫描资源服务器
                    return;
                }
            }

            List<RequestMappingResource> resources = new ArrayList<>();
            // 3、微服务需要设置服务资源的上级节点
            if (applicationProperties.getArchitecture() == Architecture.MICROSERVICE) {
                RequestMappingResource requestMappingResourceRoot = new RequestMappingResource();
                requestMappingResourceRoot.setId(serviceId);
                requestMappingResourceRoot.setServiceId(serviceId);
                requestMappingResourceRoot.setParentId(applicationProperties.getAuthorityTreeRoot());
                requestMappingResourceRoot.setName(applicationProperties.getServiceDisplayName());
                resources.add(requestMappingResourceRoot);
            }


            // 4、获取所有接口映射
            RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            // 5、 获取url与类和方法的对应信息
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                RequestMappingInfo info = entry.getKey();
                HandlerMethod method = entry.getValue();

//            if (method.getMethod().getDeclaringClass().getAnnotation(RestController.class) == null) {
//                // 只扫描RestController
//                continue;
//            }
                // 5.1、有ApiIgnore注解的方法不扫描
                if (method.getMethodAnnotation(ApiIgnore.class) != null) {
                    continue;
                }

                ApiOperation apiOperation = method.getMethodAnnotation(ApiOperation.class);
                // 5.2、只扫描有Swagger ApiIgnore注解的方法
                if (ObjectUtils.isNotEmpty(apiOperation)) {
                    RequestMappingResource requestMappingResource = getRequestMappingResource(serviceId, info, method, apiOperation);
                    if (ObjectUtils.isEmpty(requestMappingResource)) {
                        continue;
                    }

                    resources.add(requestMappingResource);
                }
            }

            requestMappingStore.storeRequestMappings(resources);
            log.info("[Herodotus] |- Platform Store the Resource For Service: [{}]", serviceId);
        } else {
            log.warn("[Herodotus] |- If you want to auto scan and store REST Api, please config the ResourceStore!");
        }
    }

    private RequestMappingResource getRequestMappingResource(String serviceId, RequestMappingInfo info, HandlerMethod method, ApiOperation apiOperation) {
        // 5.2.1、获取类名
        // method.getMethod().getDeclaringClass().getName() 取到的是注解实际所在类的名字，比如注解在父类叫BaseController，那么拿到的就是BaseController
        // method.getBeanType().getName() 取到的是注解实际Bean的名字，比如注解在在父类叫BaseController，而实际类是SysUserController，那么拿到的就是SysUserController
        String className = method.getBeanType().getName();

        // 5.2.2、不是Hammer所必须的类，则忽略
        if (!isLegalGroup(className)) {
            return null;
        }

        // 5.2.3、获取不包含包路径的类名
        String classSimpleName = method.getBeanType().getSimpleName();

        // 5.2.4、获取注解对应的方法名
        String methodName = method.getMethod().getName();

        // 5.2.5、获取注解对应方法参数个数，用于区分重载方法
        int parameterCount = method.getMethod().getParameterCount();

        // 5.2.6、获取注解对应的请求类型
        RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
        String requestMethods = getMethods(requestMethodsRequestCondition.getMethods());

        // 5.2.7、获取主机对应的请求路径
        PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
        String urls = getUrls(patternsRequestCondition.getPatterns());
        // 对于Hammer Index路径一般都是menu，还是手动设置吧。
        if (applicationProperties.getArchitecture() == Architecture.MONOMER) {
            if (StringUtils.contains(urls, "index")) {
                return null;
            }
        }

        // 5.2.8、微服务范围更加粗放， Hammer应用通过classSimpleName进行细化
        String identifyingCode = applicationProperties.getArchitecture() == Architecture.MICROSERVICE ? serviceId : classSimpleName;

        // 5.2.9、根据serviceId, requestMethods, urls生成的MD5值，作为自定义主键
        String id = idGenerator(identifyingCode, urls, requestMethods);

        // 5.2.10、组装对象
        RequestMappingResource requestMappingResource = new RequestMappingResource();
        requestMappingResource.setId(id);
        requestMappingResource.setCode(SecurityConstants.AUTHORITY_PREFIX + id);
        // 微服务需要明确ServiceId，同时也知道ParentId，Hammer有办法，但是太繁琐，还是生成数据后，配置一把好点。
        if (applicationProperties.getArchitecture() == Architecture.MICROSERVICE) {
            requestMappingResource.setServiceId(identifyingCode);
            requestMappingResource.setParentId(identifyingCode);
        }
        requestMappingResource.setName(apiOperation.value());
        requestMappingResource.setDescription(apiOperation.notes());
        requestMappingResource.setRequestMethod(requestMethods);
        requestMappingResource.setUrl(urls);
        requestMappingResource.setClassName(className);
        requestMappingResource.setMethodName(methodName);
        return requestMappingResource;
    }

    private String idGenerator(String serviceId, String urls, String requestMethods) {
        StringBuilder builder = new StringBuilder();
        builder.append(serviceId);
        builder.append(SymbolConstants.DASH);
        builder.append(requestMethods);
        builder.append(SymbolConstants.DASH);
        builder.append(urls);

        byte[] md5Bytes = builder.toString().getBytes(Charset.defaultCharset());

        String md5 = DigestUtils.md5DigestAsHex(md5Bytes);
        return md5.toUpperCase();
    }

    private String getMethods(Set<RequestMethod> requestMethods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (RequestMethod requestMethod : requestMethods) {
            stringBuilder.append(requestMethod.toString()).append(SymbolConstants.COMMA);
        }
        if (requestMethods.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private String getUrls(Set<String> urls) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String url : urls) {
            stringBuilder.append(url).append(SymbolConstants.COMMA);
        }
        if (urls.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private boolean isLegalGroup(String className) {
        if (StringUtils.isNotEmpty(className)) {
            List<String> groupIds = applicationProperties.getRequestMapping().getScanGroupIds();
            List<String> result = groupIds.stream().filter(groupId -> StringUtils.contains(className, groupId)).collect(Collectors.toList());
            return !CollectionUtils.sizeIsEmpty(result);
        } else {
            return false;
        }
    }
}
