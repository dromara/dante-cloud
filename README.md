<h1 align="center"> Eurynome Cloud </h1>

---

<p align="center">
    <a href="https://www.oracle.com/java/technologies/javase-downloads.html" target="_blank"><img src="https://img.shields.io/badge/JDK-1.8%2B-green" alt="JDK 1.8+"></a>
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-2.4.5-blue" alt="Spring Boot 2.4.5"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud-2020.0.2-blue" alt="Spring Cloud 2020.0.2"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2021.1-blue" alt="Spring Cloud Alibaba 2021.1"></a>
    <a href="https://nacos.io/zh-cn/index.html" target="_blank"><img src="https://img.shields.io/badge/Nacos-2.0.1-brightgreen" alt="Nacos 2.0.1"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue" alt="License Apache 2.0"></a>
    <a href="https://gitee.com/herodotus/eurynome-cloud"><img src="https://gitee.com/herodotus/eurynome-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/herodotus/eurynome-cloud"><img src="https://gitee.com/herodotus/eurynome-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>

# 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！

# Eurynome Cloud

- 基于 **Spring Cloud** 和 **Spring Cloud Alibaba** 的微服务架构

# 版本号说明

本系统版本号，分为四段。

- 第一段和第二段，与Spring Boot 版本对应，根据采用的Spring Boot版本变更。例如，当前采用Spring Boot 2.4.2版本，那么就以2.4.X.X开头
- 第三段，表示系统功能的变化
- 第四段，表示系统功能维护及优化情况

## 工程结构
``` 
SpringBlade
├── blade-auth -- 授权服务提供
├── blade-common -- 常用工具封装包
├── blade-gateway -- Spring Cloud 网关
├── blade-ops -- 运维中心
├    ├── blade-admin -- spring-cloud后台管理
├    ├── blade-develop -- 代码生成
├    ├── blade-resource -- 资源管理
├    ├── blade-seata-order -- seata分布式事务demo
├    ├── blade-seata-storage -- seata分布式事务demo
├── blade-service -- 业务模块
├    ├── blade-desk -- 工作台模块 
├    ├── blade-log -- 日志模块 
├    ├── blade-system -- 系统模块 
├    └── blade-user -- 用户模块 
├── blade-service-api -- 业务模块api封装
├    ├── blade-desk-api -- 工作台api 
├    ├── blade-dict-api -- 字典api 
├    ├── blade-system-api -- 系统api 
└──  └── blade-user-api -- 用户api 
```

界面预览

<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow3.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow5.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow6.png"/></td>
    </tr>
</table>

# 主要技术栈说明：

组件 | 版本 | 说明
---|---|---
Spring Boot | 2.4.5 | 功能版本要与其它组件匹配
Spring Cloud | 2020.0.2 |
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

# 开源协议

Apache Licence 2.0 [（英文原文）](https://www.apache.org/licenses/LICENSE-2.0.html) Apache Licence是著名的非盈利开源组织Apache采用的协议。该协议和BSD类似，同样鼓励代码共享和尊重原作者的著作权，同样允许代码修改，再发布（作为开源或商业软件）。 需要满足的条件如下：

- 需要给代码的用户一份Apache Licence
- 如果你修改了代码，需要在被修改的文件中说明。 
- 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。 
- 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。 Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售。
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