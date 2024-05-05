/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.gateway.configuration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * <p>Description: 使用Loadbalancer的随机负载均衡配置 </p>
 * <p>
 * spring cloud加入了一个新模块Spring-Loadbalancer来替代ribbon，有两种负载均衡模式（轮询和随机），默认是用轮询，
 * 假如想使用随机或者自定义负载均衡策略，就不能按照以前使用ribbon的模式（注入IRule类，必须引入ribbon依赖），
 *
 * @author : gengwei.zheng
 * @date : 2021/6/2 16:08
 */
@LoadBalancerClients(defaultConfiguration = GrayLoadBalancerConfiguration.class)
public class GrayLoadBalancerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GrayLoadBalancerConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Gray Load Balancer] Auto Configure.");
    }

//    @Bean
//    ReactorLoadBalancer<ServiceInstance> nacosWeightRandomLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        log.debug("[Herodotus] |- Bean [Nacos Weight Random Load Balancer] Auto Configure.");
//        return new NacosWeightRandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }
}
