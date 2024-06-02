<p align="center"><img src="./readme/new-logo.png" height="300" width="400" alt="logo"/></p>
<h2 align="center">简洁优雅 · 稳定高效 | 宁静致远 · 精益求精 </h2>
<h4 align="center">基于 Spring Authorization Server 全新适配 OAuth 2.1 协议的企业级微服务架构</h4>

---

<p align="center">
    <a href="https://github.com/spring-projects/spring-authorization-server" target="_blank"><img src="https://img.shields.io/badge/Spring%20Authorization%20Server-1.3.0-blue.svg?logo=spring" alt="Spring Authorization Server 1.3.0"></a>
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-3.3.0-blue.svg?logo=spring" alt="Spring Boot 3.3.0"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud-2023.0.2-blue.svg?logo=spring" alt="Spring Cloud 2023.0.2"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2023.0.1.0-blue.svg?logo=alibabadotcom" alt="Spring Cloud Alibaba 2023.0.1.0"></a>
    <a href="https://github.com/Tencent/spring-cloud-tencent" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Tencent-1.14.0--2023.0.0-blue.svg?logo=tencentqq" alt="Spring Cloud Tencent 1.14.0--2023.0.0-SNAPSHOT"></a>
    <a href="https://nacos.io/zh-cn/index.html" target="_blank"><img src="https://img.shields.io/badge/Nacos-2.3.2-brightgreen.svg?logo=alibabadotcom" alt="Nacos 2.3.2"></a>
</p>
<p align="center">
    <a href="#" target="_blank"><img src="https://img.shields.io/badge/Version-3.3.0.2-red.svg?logo=spring" alt="Version 3.3.0.2"></a>
    <a href="https://gitee.com/herodotus/dante-engine" target="_blank"><img src="https://img.shields.io/badge/Dante%20Engine-3.3.0.2-red.svg?logo=spring" alt="Dante Engine 3.3.0.2"></a>
    <a href="https://gitee.com/herodotus/dante-oss" target="_blank"><img src="https://img.shields.io/badge/Dante%20OSS-3.3.0.2-red.svg?logo=spring" alt="Dante OSS 3.3.0.2"></a>
    <a href="https://bell-sw.com/pages/downloads/#downloads" target="_blank"><img src="https://img.shields.io/badge/JDK-17%2B-green.svg?logo=openjdk" alt="Java 17"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg?logo=apache" alt="License Apache 2.0"></a>
    <a href="https://www.herodotus.cn"><img src="https://visitor-badge.laobi.icu/badge?page_id=dante-cloud&title=Total%20Visits" alt="Total Visits"></a>
    <a href="https://blog.csdn.net/Pointer_v" target="_blank"><img src="https://img.shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://img.shields.io/github/stars/herodotus-cloud/dante-cloud?style=flat&logo=github" alt="Github star"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://img.shields.io/github/forks/herodotus-cloud/dante-cloud?style=flat&logo=github" alt="Github fork"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://gitee.com/dromara/dante-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://gitee.com/dromara/dante-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>
<p align="center">
    <a href="https://github.com/dromara/dante-cloud">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/dromara/dante-cloud">Gitee 仓库</a> &nbsp; | &nbsp;
    <a href="https://www.herodotus.vip">文档</a>
</p>

<h1 align="center"> 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！</h1>

---

## 企业级技术中台微服务架构与服务能力开发和管理平台

**Dante Cloud** 是国内首个支持阻塞式和响应式融合的微服务。以「**高质量代码、低安全漏洞**」为核心，**采用领域驱动模型(DDD)设计思想，完全基于 Spring 生态全域开源技术和 OAuth2.1 协议，支持智能电视、IoT等物联网设备认证**，满足**国家三级等保要求**、支持接口**国密数字信封加解密**、防刷、高防XSS和SQL注入等一系列安全体系的**多租户微服务解决方案**。

## 定位

- 构建成熟的、完善的、全面的，基于 OAuth2.1 的、前后端分离的微服务架构解决方案。
- 面向企业级应用和互联网应用设计开发，既兼顾传统项目的微服务化，又满足互联网应用开发建设、快速迭代的使用需求。
- 平台架构使用微服务领域及周边相关的各类新兴技术或主流技术进行建设，是帮助快速跨越架构技术选型、研究探索阶段的利器。
- 代码简洁规范、结构合理清晰，是新技术开发应用的典型的、综合性案例，助力开发人员对新兴技术的学习和掌握。

## Dante Cloud 响应式版本特性

- `Spring Boot` 已升级至 3.3.0
- `Spring Authorization Server` 已升级至 1.3.0
- 全面采用 Java 21，默认开启虚拟线程，以改善阻塞操作的处理降低系统资源的消耗
- 支持传统的 `阻塞式` 微服务与基于 `Reactor` 和 `WebFlux` 的 `响应式` 微服务同时运行在一套系统之中
- 不强制使用 `响应式` 方式开发，可根据自身项目对资源吞吐量、资源消耗、特殊功能性能保障的需求，灵活的选择是采用 `响应式` 还是 `阻塞式` 来开发对应的服务。
- 在保持 Dante Cloud 原有 `Spring Authorization Server` 深度扩展的各种特性的前提下，实现 `响应式` 服务的动态鉴权与现有体系的完全融合（无需在代码中使用 `@PreAuthorize` 写死权限，全部通过后台动态管理）
- 向“响应式编程”转变，基于 `Reactor` 重构大量核心代码，进一步提升本系统代码质量和运行效能
- 重新架构所有核心组件模块，进一步降低各模块的耦合性，减少第三方组件依赖深度，简化各模块使用的复杂度，使用更贴近 Spring Boot 生态官方写法，提升模块组件的可插拔性以及 `响应式` 和 `阻塞式` 不同环境下自动配置的适配能力
- 实现 `响应式` 和 `阻塞式` 不同类型服务，Session 共享体系以及自定义 Session 体系的完美融合（谁说微服务就一定用不到 Session ：））。
- 新增 `GRPC` 服务间调用和通信方式，系统核心服务间调用支持 `OpenFeign` 和 `GRPC` 两种方式，可通过修改配置实现两种方式的切换。
- 基于 `RSocket` 全面重写 `WebSocket` 消息系统，实现 `WebSocket` 的 `响应式` 改造以及 `RSocket` 与 Spring Security 体系的全面集成。支持多实例、跨服务的私信和广播
- 新增 OAuth2 独立客户端，可用于客户端动态注册以及授权码模式
- 新增基于 `Loki + Grafana` 生态的轻量级日志中心和链路追踪解决方案，使用 OSS 作为数据存储，极大地降低资源需求，可作为原有 Skywalking 和 ELK 重量级体系的备选方案，根据实际需要切换。
- 开放纯手写动态表单功能。可实现BPMN、动态表单、Camunda 流程引擎的串联，实现工作流程运转（目前仅支持简单工作流）
- 开放包含自定义属性面板的 BPMN 在线设计器功能。
- 开放物联网设备认证和管理模块，支持基于 Emqx 的物联网设备通信和管理。
- 开放阿里云内容审核、百度 OCR、环信、Emqx、天眼查、Nacos、PolarisMash等第三方 OpenApi 封装模块
- 前端工程支持 Docker 运行，相关参数可通过配置环境变量修改。已上传至 Docker Hub，可以直接下载运行。

## [1]、开源协议

![开源协议](./readme/copyright/agplv3-155x51.png)

## [2]、总体架构

![输入图片说明](./readme/architecture.jpg)

## [3]、功能讲解

<a href="https://www.herodotus.vip>详情见在线文档</a>

## [4]、技术栈和版本说明

### （1）Spring 全家桶及核心技术版本

| 组件                          | 版本              |
|-----------------------------|-----------------|
| Spring Boot                 | 3.3.0           |
| Spring Cloud                | 2023.0.2        |
| Spring Cloud Alibaba        | 2023.0.1.0      |
| Spring Cloud Tencent        | 1.14.0-2023.0.0 |
| Spring Authorization Server | 1.3.0           |
| Spring Boot Admin           | 3.2.2           |
| Nacos                       | 2.3.2           |
| Sentinel                    | 1.8.7           |
| Seata                       | 1.7.0           |

> Spring 全家桶版本对应关系，详见：[版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

### （2）所涉及的相关的技术：

- 持久层框架： Spring Data Jpa & Mybatis Plus
- API 网关：Spring Cloud Gateway
- 服务注册&发现和配置中心: Alibaba Nacos、Tencent Polaris
- 服务消费：Spring Cloud OpenFeign & RestTemplate & OkHttps
- 负载均衡：Spring Cloud Loadbalancer
- 服务熔断&降级&限流：Alibaba Sentinel、Tencent Polaris
- 服务监控：Spring Boot Admin
- 消息队列：使用 Spring Cloud 消息总线 Spring Cloud Bus 默认 Kafka 适配 RabbitMQ
- 链路跟踪：Skywalking
- 分布式事务：Seata
- 数据缓存：JetCache (Redis + Caffeine) 多级缓存
- 数据库： Postgresql，MySQL，Oracle ...
- JSON 序列化：Jackson & FastJson
- 文件服务：阿里云 OSS/Minio
- 数据调试：p6spy
- 日志中心：ELK
- 日志收集：Logstash Logback Encoder

### (3) 前端工程技术栈

- Vue 3
- Vite 5
- Pinia
- Typescript 5
- Quasar 2
- Vue-Router 4
- Vueliate

## [5]、 版本和分支

### 一、版本号说明

本系统版本号，分为四段。

- 第一段和第二段，与 Spring Boot 版本对应，根据采用的 Spring Boot 版本变更。例如，当前采用 Spring Boot 2.4.6 版本，那么就以
  2.4.X.X 开头
- 第三段，表示系统功能的变化
- 第四段，表示系统功能维护及优化情况

### 二、分支说明

| 分支名称                   | 对应 Spring 生态版本                          | 对应 JDK 版本 | 用途                 | 现状                                                          |
|------------------------|-----------------------------------------|-----------|--------------------|-------------------------------------------------------------|
| master                 | Spring Boot 3.3 和 Spring Cloud 2023.0.2 | JDK 17    | 主要发布分支             | 推荐使用代码分支                                                    |
| develop                | Spring Boot 3.3 和 Spring Cloud 2023.0.2 | JDK 17    | Development 分支     | 新功能、ISSUE 均以此分支作为开发，发布后会 PR 至 master 分支。开发分支不保证可用           |
| reactive-master        | Spring Boot 3.3 和 Spring Cloud 2023.0.2 | JDK 21    | 响应式主要发布分支          | 推荐使用的响应式代码分支                                                |
| reactive-develop       | Spring Boot 3.3 和 Spring Cloud 2023.0.2 | JDK 21    | 响应式 Development 分支 | 下一代响应式微服务版本开发分支。开发分支不保证可用                                   |
| 3.1.X                  | Spring Boot 3.1 和 Spring Cloud 2022.0.X | JDK 17    | 历史代码，不再维护          | 基于 Spring Boot 3.1 时代开发的代码分支，稳定可用，不再维护                      |
| 2.7.X                  | Spring Boot 2.7 和 Spring Cloud 2021.0.X | JDK 8     | 历史代码，不再维护          | 基于 Spring Boot 2.7 时代开发的代码分支，稳定可用，不再维护                      |
| spring-security-oauth2 | Spring Boot 2.6 和 Spring Cloud 2021.0.X | JDK 8     | 历史代码，不再维护          | 基于原 Spring Security OAuth2 实现的微服务，稳定可用，因相关组件均不在维护，所以该版本不再维护 |

## [6]、工程结构

```
dante-cloud
├── configurations -- 配置文件脚本和统一Docker build上下文目录
├── dependencies -- 工程Maven顶级依赖，统一控制版本和依赖
├── module -- 依赖组件半成品拼装工程
├    ├── dante-module-common -- Module 相关模块公共辅助代码模块
├    ├── dante-module-metadata -- 权限元数据同步模块
├    ├── dante-module-social -- 社交登录模块
├    └── dante-module-strategy -- UAA 核心数据访问策略模块
├── packages -- 基础核心Starter
├    ├── authorization-spring-boot-starter -- OAuth2 认证基础Starter，主要用于 UAA 认证服务器以及单体版 Dante Cloud
├    ├── facility-spring-boot-starter -- 基础设施切换依赖starter
├    └── service-spring-boot-starter -- 平台接入应用服务通用 Starter
├── platform -- 平台核心服务
├    ├── dante-cloud-gateway -- 服务网关
├    ├── dante-cloud-message -- 消息服务
├    ├── dante-cloud-monitor -- Spring Boot Admin 监控服务
├    ├── dante-cloud-upms -- 统一权限管理系统服务
├    └── dante-cloud-uaa -- 账户管理和统一认证模块
├── services -- 平台业务服务
├    ├── dante-cloud-bpmn-ability -- 工作流服务
├    ├── dante-cloud-bpmn-logic -- 工作流基础代码包
└──  └── dante-cloud-oss-ability -- 对象存储服务
```

## [7]、项目地址

- 后端主工程地址：[https://gitee.com/dromara/dante-cloud](https://gitee.com/dromara/dante-cloud)
- 后端单体版示例工程地址：[https://gitee.com/herodotus/dante-cloud-athena](https://gitee.com/herodotus/dante-cloud-athena)
- 前端工程地址：仅支持 Docker 运行，购买授权后开放私库
- 核心组件地址：购买授权后开放私库

## [8]、界面预览

<table>
    <tr>
        <td><img src="./readme/ui1.png" alt="前端界面1"/></td>
        <td><img src="./readme/ui2.png" alt="前端界面2"/></td>
        <td><img src="./readme/ui3.png" alt="前端界面1"/></td>
    </tr>
    <tr>
        <td><img src="./readme/ui6.png" alt="前端界面6"/></td>
        <td><img src="./readme/ui7.png" alt="前端界面7"/></td>
        <td><img src="./readme/ui8.png" alt="前端界面8"/></td>
    </tr>
    <tr>
        <td><img src="./readme/ui4.jpg" alt="前端界面2"/></td>
        <td><img src="./readme/ui5.jpg" alt="前端界面3"/></td>
        <td><img src="./readme/oauth2-login1.png" alt="OAuth2 效果示例"/></td>
    </tr>
    <tr>
        <td><img src="./readme/oauth2-login2.png" alt="前端界面3"/></td>
        <td><img src="./readme/oauth2-login3.png" alt="OAuth2 效果示例"/></td>
        <td><img src="./readme/nacos.png" alt="Nacos示例界面"/></td>
    </tr>
    <tr>
        <td><img src="./readme/elk.png" alt="日志中心示例界面"/></td>
        <td><img src="./readme/sentinel.png" alt="Sentinel 效果示例"/></td>
        <td><img src="./readme/spring-boot-admin-1.png" alt="Spring Boot Admin 效果示例1"/></td>
    </tr>
    <tr>
        <td><img src="./readme/spring-boot-admin-2.png" alt="Spring Boot Admin 效果示例2"/></td>
        <td><img src="./readme/skywalking.png" alt="Skywalking 效果示例1"/></td>
        <td><img src="./readme/skywalking2.png" alt="Skywalking 效果示例2"/></td>
    </tr>
</table>

## [9]、谁在使用 Dante Cloud

| 序号 | 名称   | 官网                      |
|----|------|-------------------------|
| 1  | 轻喜到家 | <https://qxdaojia.com/> |

## [10]、鸣谢

### 赞助人列表

| 序号 |   赞助人    |    赞助时间    | 序号 |     赞助人     |    赞助时间    | 序号 |    赞助人    |    赞助时间    |
|:--:|:--------:|:----------:|:--:|:-----------:|:----------:|:--:|:---------:|:----------:|
| 1  |  ご沉默菋噵   | 2021-10-25 | 2  |    偷土豆的人    | 2021-11-24 | 3  |  lorron   | 2022-04-04 |
| 4  |   在云端    | 2022-12-26 | 5  |     西晽      | 2022-12-27 | 6  | p911gt3rs | 2023-01-03 |
| 7  |  jacsty  | 2023-01-31 | 8  | hubert_rust | 2023-03-16 | 9  |  Zkey Z   | 2023-03-18 |
| 10 |   志国欧巴   | 2023-03-27 | 11 |   michael   | 2023-04-07 | 12 |   大叔丨小巷   | 2023-04-11 |
| 13 | sun_left | 2023-04-19 | 14 | time 丶 sand | 2023-07-06 | 15 |  印第安老斑鸠   | 2023-09-09 |
| 16 |   一阵风    | 2023-12-10 | 17 |    Jack     | 2024-03-01 | 18 | onehelper | 2024-05-24 |
| 19 |    宁哥    | 2024-05-29 | 20 |             |            | 21 |           |            |

### 开源项目

- [Soybean Admin](https://gitee.com/honghuangdc/soybean-admin)
- [Vue Next Admin](https://gitee.com/lyt-top/vue-next-admin)
- [Vue VBen Admin](https://gitee.com/annsion/vue-vben-admin)
- [Quasar Admin Template](https://gitee.com/jinjinge/quasar-admin-template)

### 感谢 JetBrains 提供的免费开源 License

![https://jb.gg/OpenSourceSupport](./readme/jb_beam.svg)
