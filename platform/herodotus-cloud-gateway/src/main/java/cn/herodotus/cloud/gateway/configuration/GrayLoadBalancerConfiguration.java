/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
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
