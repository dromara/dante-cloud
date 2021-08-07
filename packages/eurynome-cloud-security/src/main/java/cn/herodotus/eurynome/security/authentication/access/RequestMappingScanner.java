/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-security
 * File Name: RequestMappingScanner.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 10:58:13
 */

package cn.herodotus.eurynome.security.authentication.access;

import cn.herodotus.eurynome.assistant.utils.PropertyResolver;
import cn.herodotus.eurynome.constant.enums.Architecture;
import cn.herodotus.eurynome.constant.magic.PlatformConstants;
import cn.herodotus.eurynome.constant.magic.SecurityConstants;
import cn.herodotus.eurynome.constant.magic.SymbolConstants;
import cn.herodotus.eurynome.rest.properties.PlatformProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
import cn.herodotus.eurynome.security.definition.domain.RequestMapping;
import cn.herodotus.eurynome.security.service.RequestMappingGatherService;
import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: RequestMappingScanner </p>
 *
 * <p>Description: RequestMapping扫描器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/2 19:52
 */
public class RequestMappingScanner implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingScanner.class);

    private final RestProperties restProperties;
    private final PlatformProperties platformProperties;
    private final RequestMappingGatherService requestMappingGatherService;

    private ApplicationContext applicationContext;

    /**
     * 在外部动态指定扫描的注解，而不是在内部写死
     */
    private Class<? extends Annotation> scanAnnotationClass = EnableResourceServer.class;

    public RequestMappingScanner(RestProperties restProperties, PlatformProperties platformProperties, RequestMappingGatherService requestMappingGatherService) {
        this.restProperties = restProperties;
        this.platformProperties = platformProperties;
        this.requestMappingGatherService = requestMappingGatherService;
    }

    public void setScanAnnotationClass(Class<? extends Annotation> scanAnnotationClass) {
        this.scanAnnotationClass = scanAnnotationClass;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.applicationContext = event.getApplicationContext();
        String ddlAuto = PropertyResolver.getDdlAuto(applicationContext.getEnvironment());
        if (StringUtils.isNotEmpty(ddlAuto) && !StringUtils.equalsIgnoreCase(ddlAuto, PlatformConstants.NONE)) {
            onApplicationEvent(applicationContext);
        }
    }

    public void onApplicationEvent(ApplicationContext applicationContext) {
        // 1、获取服务ID：该服务ID对于微服务是必需的。
        String serviceId = PropertyResolver.getApplicationName(applicationContext.getEnvironment());

        // 2、只针对有EnableResourceServer注解的微服务进行扫描。如果变为单体架构目前不会用到EnableResourceServer所以增加的了一个Architecture判断
        if (isDistributedArchitecture()) {
            Map<String, Object> resourceServer = applicationContext.getBeansWithAnnotation(scanAnnotationClass);
            if (MapUtils.isEmpty(resourceServer)) {
                // 只扫描资源服务器
                return;
            }
        }

        // 3、获取所有接口映射
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 4、 获取url与类和方法的对应信息
        List<RequestMapping> resources = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            // 4.1、如果是被排除的requestMapping，那么就进行不扫描
            if (isExcludedRequestMapping(handlerMethod)) {
                continue;
            }

            // 4.2、拼装扫描信息
            RequestMapping requestMapping = createRequestMapping(serviceId, requestMappingInfo, handlerMethod);
            if (ObjectUtils.isEmpty(requestMapping)) {
                continue;
            }

            resources.add(requestMapping);
        }

        if (CollectionUtils.isNotEmpty(resources)) {
            requestMappingGatherService.postProcess(resources, applicationContext, serviceId, isDistributedArchitecture());
        }

        log.info("[Eurynome] |- Request Mapping Scan for Service: [{}] FINISHED!", serviceId);
    }

    /**
     * 检测RequestMapping是否需要被排除
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    private boolean isExcludedRequestMapping(HandlerMethod handlerMethod) {
        if (!isSpringAnnotationMatched(handlerMethod)) {
            return true;
        }

        return !isSwaggerAnnotationMatched(handlerMethod);
    }

    /**
     * 如果开启isJustScanRestController，那么就只扫描RestController
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    private boolean isSpringAnnotationMatched(HandlerMethod handlerMethod) {
        if (restProperties.getRequestMapping().isJustScanRestController()) {
            return handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null;
        }

        return true;
    }

    /**
     * 有ApiIgnore注解的方法不扫描, 没有ApiOperation注解不扫描
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    private boolean isSwaggerAnnotationMatched(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(ApiIgnore.class) != null) {
            return false;
        }

        return handlerMethod.getMethodAnnotation(ApiOperation.class) != null;
    }

    /**
     * 如果当前class的groupId在GroupId列表中，那么就进行扫描，否则就排除
     *
     * @param className 当前扫描的controller类名
     * @return Boolean
     */
    private boolean isLegalGroup(String className) {
        if (StringUtils.isNotEmpty(className)) {
            List<String> groupIds = restProperties.getRequestMapping().getScanGroupIds();
            List<String> result = groupIds.stream().filter(groupId -> StringUtils.contains(className, groupId)).collect(Collectors.toList());
            return !CollectionUtils.sizeIsEmpty(result);
        } else {
            return false;
        }
    }

    private RequestMapping createRequestMapping(String serviceId, RequestMappingInfo info, HandlerMethod method) {
        // 4.2.1、获取类名
        // method.getMethod().getDeclaringClass().getName() 取到的是注解实际所在类的名字，比如注解在父类叫BaseController，那么拿到的就是BaseController
        // method.getBeanType().getName() 取到的是注解实际Bean的名字，比如注解在在父类叫BaseController，而实际类是SysUserController，那么拿到的就是SysUserController
        String className = method.getBeanType().getName();

        // 4.2.2、检测该类是否在GroupIds列表中
        if (!isLegalGroup(className)) {
            return null;
        }

        // 5.2.3、获取不包含包路径的类名
        String classSimpleName = method.getBeanType().getSimpleName();

        // 4.2.4、获取RequestMapping注解对应的方法名
        String methodName = method.getMethod().getName();

        // 5.2.5、获取注解对应的请求类型
        RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
        String requestMethods = StringUtils.join(requestMethodsRequestCondition.getMethods(), SymbolConstants.COMMA);

        // 5.2.6、获取主机对应的请求路径
        PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
        String urls = StringUtils.join(patternsRequestCondition.getPatterns(), SymbolConstants.COMMA);
        // 对于单体架构路径一般都是menu，还是手动设置吧。
        if (!isDistributedArchitecture()) {
            if (StringUtils.contains(urls, "index")) {
                return null;
            }
        }

        // 5.2.7、微服务范围更加粗放， 单体架构应用通过classSimpleName进行细化
        String identifyingCode = isDistributedArchitecture() ? serviceId : classSimpleName;

        // 5.2.8、根据serviceId, requestMethods, urls生成的MD5值，作为自定义主键
        String flag = serviceId + SymbolConstants.DASH + requestMethods + SymbolConstants.DASH + urls;
        String id = SecureUtil.md5(flag);
        int code = HashUtil.fnvHash(flag);

        // 5.2.9、组装对象
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setMetadataId(id);
        requestMapping.setMetadataCode(SecurityConstants.AUTHORITY_PREFIX + code);
        // 微服务需要明确ServiceId，同时也知道ParentId，Hammer有办法，但是太繁琐，还是生成数据后，配置一把好点。
        if (isDistributedArchitecture()) {
            requestMapping.setServiceId(identifyingCode);
            requestMapping.setParentId(identifyingCode);
        }
        ApiOperation apiOperation = method.getMethodAnnotation(ApiOperation.class);
        if (ObjectUtils.isNotEmpty(apiOperation)) {
            requestMapping.setMetadataName(apiOperation.value());
            requestMapping.setDescription(apiOperation.notes());
        }
        requestMapping.setRequestMethod(requestMethods);
        requestMapping.setUrl(urls);
        requestMapping.setClassName(className);
        requestMapping.setMethodName(methodName);
        return requestMapping;
    }

    private boolean isDistributedArchitecture() {
        return platformProperties.getArchitecture() == Architecture.DISTRIBUTED;
    }
}
