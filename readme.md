# Eurynome Cloud

- 基于 **Spring Cloud** 和 **Spring Cloud Alibaba** 的微服务架构

# 版本号说明

本系统版本号，分为四段。

- 第一段和第二段，与Spring Boot 版本对应，根据采用的Spring Boot版本变更。例如，当前采用Spring Boot 2.4.2版本，那么就以2.4.X.X开头
- 第三段，表示系统功能的变化
- 第四段，表示系统功能维护及优化情况

# 主要技术栈说明：

组件 | 版本 | 说明
---|---|---
Spring Boot | 2.4.2 | 功能版本要与其它组件匹配
Spring Cloud | 2020.0.0 |
Spring Cloud Alibaba | 2.2.3.RELEASE
Spring Boot Admin | 2.3.1 |
Nacos | 1.4.0 |
Sentinel | 1.8.0 |
Skywalking | latest |
Jetcache | 2.6.0 |
ELK | latest |
logstash-logback-encoder | 6.6 | 使用该组件直接向ELK发送日志
MySQL | 8.0.22 |
PostgreSQL | 12.3-1 |
Redis | 3.2.100 | 为了图省事，还是用的可以在Windows下直接安装版本老版本。使用最新版也可，只要支持lettuce就行。
Docker Desktop for Window | latest

> 相关组件版本配关系，地址： [https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E][官网说明地址]

## 组件版本关系

Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | RocketMQ Version | Dubbo Version | Seata Version
---|---|---|---|---|---
2.2.3.RELEASE or 2.1.3.RELEASE or 2.0.3.RELEASE | 1.8.0 | 1.3.3 | 4.4.0 | 2.7.8 | 1.3.0
2.2.1.RELEASE or 2.1.2.RELEASE or 2.0.2.RELEASE | 1.7.1 | 1.2.1 | 4.4.0 | 2.7.6 | 1.2.0
2.2.0.RELEASE | 1.7.1 | 1.1.4 | 4.4.0 | 2.7.4.1 | 1.0.0
2.1.2.RELEASE or 2.0.2.RELEASE | 1.7.1 | 1.2.1 | 4.4.0 | 2.7.6 | 1.1.0
2.1.1.RELEASE or 2.0.1.RELEASE or 1.5.1.RELEASE | 1.7.0 | 1.1.4 | 4.4.0 | 2.7.3 | 0.9.0
2.1.0.RELEASE or 2.0.0.RELEASE or 1.5.0.RELEASE | 1.6.3 | 1.1.1 | 4.4.0 | 2.7.3 | 0.7.1

## 毕业版本依赖关系(推荐使用)

Spring Cloud Version | Spring Cloud Alibaba Version | Spring Boot Version
---|---|---
Spring Cloud Hoxton.SR8 | 2.2.3.RELEASE | 2.3.2.RELEASE
Spring Cloud Greenwich.SR6 | 2.1.3.RELEASE | 2.1.13.RELEASE
Spring Cloud Hoxton.SR8 | 2.2.2.RELEASE | 2.3.2.RELEASE
Spring Cloud Hoxton.SR3 | 2.2.1.RELEASE | 2.2.5.RELEASE
Spring Cloud Hoxton.RELEASE | 2.2.0.RELEASE | 2.2.X.RELEASE
Spring Cloud Greenwich | 2.1.2.RELEASE | 2.1.X.RELEASE
Spring Cloud Finchley | 2.0.2.RELEASE | 2.0.X.RELEASE
Spring Cloud Edgware | 1.5.1.RELEASE(停止维护，建议升级) | 1.5.X.RELEASE