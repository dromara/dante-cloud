<h1 align="center"> Eurynome Cloud </h1>

<p align="center">
    <a href="https://www.oracle.com/java/technologies/javase-downloads.html" target="_blank"><img src="https://img.shields.io/badge/JDK-1.8%2B-green" alt="JDK 1.8+"></a>
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-2.5.1-blue" alt="Spring Boot 2.5.1"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud-2020.0.3-blue" alt="Spring Cloud 2020.0.3"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2021.1-blue" alt="Spring Cloud Alibaba 2021.1"></a>
    <a href="https://nacos.io/zh-cn/index.html" target="_blank"><img src="https://img.shields.io/badge/Nacos-2.0.1-brightgreen" alt="Nacos 2.0.1"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue" alt="License Apache 2.0"></a>
    <a href="https://blog.csdn.net/Pointer_v" target="_blank"><img src="https://img.shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="#" target="_blank"><img src="https://img.shields.io/badge/Version-2.5.1.0-red" alt="Version 2.5.1.0"></a>
    <a href="https://gitee.com/herodotus/eurynome-cloud"><img src="https://gitee.com/herodotus/eurynome-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/herodotus/eurynome-cloud"><img src="https://gitee.com/herodotus/eurynome-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>

<p align="center">
    <a href="https://github.com/herodotus-cloud/eurynome-cloud">Github 仓库</a> &nbsp; | &nbsp; 
    <a href="https://gitee.com/herodotus/eurynome-cloud">Gitee 仓库</a>
</p>


<h1 align="center"> 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！</h1>

---

> 注意：由于Spring Boot 2.5.0 DataSource 自动初始化机制的变化，升级版本后，一定记得修改数据库的配置（Nacos中），否则会出错。
> 注意：梳理和优化了平台配置属性，更新代码后需要同步更新Nacos配置。

## 企业级技术中台微服务架构与服务能力开发平台

Eurynome Cloud是一款企业级微服务架构和服务能力开发平台。基于Spring Boot 2.5.1、Spring Cloud 2020.0.3、Spring Cloud Alibaba 2021.1、Nacos 2.0.1等最新版本开发，遵循SpringBoot编程思想，高度模块化和可配置化。具备服务发现、配置、熔断、限流、降级、监控、多级缓存、分布式事务、工作流等功能，代码简洁，架构清晰，非常适合学习和企业作为基础框架使用。

## [1]、功能介绍

<img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/eurynome.png"/>

特点：
* 更优的代码分包和包依赖，代码包职责明确，规避无意义的依赖以及重复依赖，对基础依赖组件进行高度封装，降低IDE索引时间，提升开发效率
* 遵循微服开发模式，强化整体的可配置性，依赖功能均可以通过@EnableXXX开启，支持策略化的注入改变部分核心代码的实现逻辑。提供的starter，开箱即用，可零配置创建服务，快速进行开发
* 同时支持分布式和单体式两种架构，基于单体式架构无须搭建复杂的基础设施即可快速搭建应用，并且可无缝迁移至分布式架构
* 集成微信小程序、内容审核、证照识别、消息推送、社交登录、多通道SMS等丰富的第三方技术能力输出，可快速构建面向互联网的小程序、APP等应用。

> 更多详细功能，参见：[功能说明](./documents/guides/description.md)

## [2]、技术栈和版本说明

### （1）Spring全家桶及核心技术版本
  
组件 | 版本 
---|---
Spring Boot | 2.5.1
Spring Cloud | 2020.0.3 
Spring Cloud Alibaba | 2021.1
Spring Boot Admin | 2.4.1 
Nacos | 2.0.1 |
Sentinel | 1.8.0 |
Seata | 1.3.0 |

> Spring 全家桶版本对应关系，详见：[版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

### （2）所涉及的相关的技术： 

* JSON序列化：Jackson & FastJson 
* 消息队列：Kafka 适配RabbitMQ，支持消息总线(Spring Cloud Bus)
* 数据缓存：JetCache + Redis （两级缓存）
* 数据库： Postgresql，MySQL，Oracle ...
* 前端实现：Vue + Vuetify（单体版Vue + Vuetify + Typescript + 模块化）
* 持久层框架： Spring Data Jpa & Mybatis
* API网关：Gateway
* 服务注册&发现和配置中心: Nacos 
* 服务消费：OpenFeign & RestTemplate & OkHttp3
* 负载均衡：Spring Cloud Loadbalancer
* 服务熔断&降级&限流：Sentinel
* 分布式事务：Seata
* 服务监控：Spring Boot Admin
* 链路跟踪：Skywalking
* 文件服务：阿里云OSS/Minio
* 数据调试：p6spy
* 日志中心：ELK
* 日志收集：Logstash Logback Encoder，Skywalking

## [3]、 版本号说明

本系统版本号，分为四段。

- 第一段和第二段，与Spring Boot 版本对应，根据采用的Spring Boot版本变更。例如，当前采用Spring Boot 2.4.6版本，那么就以2.4.X.X开头
- 第三段，表示系统功能的变化
- 第四段，表示系统功能维护及优化情况

## [4]、工程结构

``` 
eurynome-cloud
├── configurations -- 配置文件脚本和统一Docker build上下文目录
├── dependencies -- 工程Maven顶级依赖，统一控制版本和依赖
├── documents -- 工程相关文档
├── packages -- 基础通用依赖包
├    ├── eurynome-cloud-common -- 公共工具类
├    ├── eurynome-cloud-data -- 数据持久化、数据缓存以及Redis等数据处理相关代码组件
├    ├── eurynome-cloud-rest -- Rest相关代码组件
├    ├── eurynome-cloud-crud -- CRUD相关代码组件
├    ├── eurynome-cloud-sercurity -- Security通用代码
├    ├── eurynome-cloud-oauth -- OAuth2通用代码
├    ├── eurynome-cloud-message -- 消息队列、BUG相关代码组件
├    ├── eurynome-cloud-kernel -- 微服务接入平台必备组件
├    ├── eurynome-cloud-oauth-starter -- 自定义OAuth2 Starter
├    └── eurynome-cloud-starter -- 微服务核心Starter
├── platform -- 平台核心服务
├    ├── eurynome-cloud-gateway -- 服务网关
├    ├── eurynome-cloud-management -- Spring Boot Admin 监控服务
├    └── eurynome-cloud-uaa -- 统一认证模块
├── services -- 平台业务服务
├    ├── eurynome-cloud-upms-api -- 通用用户权限api 
├    ├── eurynome-cloud-upms-logic -- 通用用户权限service
├    ├── eurynome-cloud-upms-rest -- 通用用户权限rest 接口
├    ├── eurynome-cloud-upms-ability -- 通用用户权限服务
└──  └── eurynome-cloud-bpmn-ability -- 工作流服务 
```

## [5]、项目地址
* 后端Gitee地址：[https://gitee.com/herodotus/eurynome-cloud](https://gitee.com/herodotus/eurynome-cloud)
* 后端Github地址：[https://github.com/herodotus-cloud/eurynome-cloud](https://github.com/herodotus-cloud/eurynome-cloud)
* 单体版示例工程：[https://gitee.com/herodotus/eurynome-cloud-athena](https://gitee.com/herodotus/eurynome-cloud-athena)
* 前端Gitee地址：[https://gitee.com/herodotus/eurynome-cloud-ui](https://gitee.com/herodotus/eurynome-cloud-ui)

## [6]、用户权益
* 允许免费用于学习、毕设、公司项目、私活等。
* 遵循Apache-2.0开源协议

## [7]、交流反馈
* 欢迎提交[ISSUS](https://gitee.com/herodotus/eurynome-cloud/issues) ，请写清楚问题的具体原因，重现步骤和环境(上下文)
* 博客：https://blog.csdn.net/Pointer_v
* 邮箱：herodotus@aliyun.com
* QQ群：922565573

## [8]、特别鸣谢

* [EasyCaptcha](https://gitee.com/whvse/EasyCaptcha)
* [JustAuth](https://gitee.com/yadong.zhang/JustAuth)
* [Okhttps](https://gitee.com/ejlchina-zhxu/okhttps)
* [Guerlab-sms](https://gitee.com/guerlab_net/guerlab-sms)

## [9]、界面预览
<table>
    <tr>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/ui1.png"/></td>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/ui2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/ui3.png"/></td>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/camunda.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/nacos.png"/></td>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/elk.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/skywalking.png"/></td>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/sentinel.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/spring-boot-admin-1.png"/></td>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/spring-boot-admin-2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/oauth2-login1.png"/></td>
        <td><img src="https://gitee.com/herodotus/eurynome-cloud/raw/master/documents/readme/oauth2-login2.png"/></td>
    </tr>
</table>
