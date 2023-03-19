<p align="center"><img src="./readme/logo2.png" height="150" width="200" alt="logo"/></p>
<h2 align="center">简洁优雅 · 稳定高效 | 宁静致远 · 精益求精 </h2>
<h4 align="center">基于 Spring Authorization Server 全新适配 OAuth 2.1 协议的企业级微服务架构</h4>

---

<p align="center">
    <a href="https://github.com/spring-projects/spring-authorization-server" target="_blank"><img src="https://img.shields.io/badge/Spring%20Authorization%20Server-1.1.0-blue" alt="Spring Authorization Server 1.1.0"></a>
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://shields.io/badge/Spring%20Boot-3.0.4-blue" alt="Spring Boot 3.0.4"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://shields.io/badge/Spring%20Cloud-2022.0.1-blue" alt="Spring Cloud 2022.0.1"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://shields.io/badge/Spring%20Cloud%20Alibaba-2022.0.0.0-blue" alt="Spring Cloud Alibaba 2022.0.0.0"></a>
    <a href="https://github.com/Tencent/spring-cloud-tencent" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Tencent-1.10.0--2022.0.1-blue" alt="Spring Cloud Tencent 1.10.0-2022.0.1"></a>
    <a href="https://nacos.io/zh-cn/index.html" target="_blank"><img src="https://shields.io/badge/Nacos-2.2.1-brightgreen" alt="Nacos 2.2.1"></a>
</p>
<p align="center">
    <a href="#" target="_blank"><img src="https://shields.io/badge/Version-3.0.4.2-red" alt="Version 3.0.4.2"></a>
    <a href="https://bell-sw.com/pages/downloads/#downloads" target="_blank"><img src="https://img.shields.io/badge/JDK-17%2B-green" alt="Java 17"></a>
    <a href="./LICENSE"><img src="https://shields.io/badge/License-Apache--2.0-blue" alt="License Apache 2.0"></a>
    <a href="https://blog.csdn.net/Pointer_v" target="_blank"><img src="https://shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://img.shields.io/github/stars/herodotus-cloud/dante-cloud?style=flat&logo=github" alt="Github star"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://img.shields.io/github/forks/herodotus-cloud/dante-cloud?style=flat&logo=github" alt="Github fork"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://gitee.com/dromara/dante-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://gitee.com/dromara/dante-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>
<p align="center">
    <a href="https://github.com/herodotus-cloud/dante-cloud">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/dromara/dante-cloud">Gitee 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/herodotus/dante-engine">核心组件仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/dromara/dante-cloud">v2.7.X</a> &nbsp; |
    <a href="https://www.herodotus.cn">文档</a>
</p>

<h1 align="center"> 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！</h1>

---

## 企业级技术中台微服务架构与服务能力开发平台

Dante Cloud  (但丁，原 Eurynome Cloud) 是一款企业级微服务架构和服务能力开发平台，是采用领域驱动模型(DDD)设计思想的、全面拥抱 Spring Authorization Server 的、基于 OAuth2.1 协议的微服务架构。基于 Spring Authorization Server 1.1.0、Spring Boot 3.0.4、Spring Cloud 2022.0.1、Spring Cloud Tencent 1.10.0-2022.0.1、Spring Cloud Alibaba 2022.0.0.0、Nacos 2.2.1 等主流技术栈开发的多租户系统，遵循 SpringBoot 编程思想，高度模块化和可配置化。具备服务发现、配置、熔断、限流、降级、监控、多级缓存、分布式事务、工作流等功能。

## 平台定位

- 构建成熟的、完善的、全面的，基于 OAuth2 的、前后端分离的微服务架构解决方案。
- 面向企业级应用和互联网应用设计开发，既兼顾传统项目的微服务化，又满足互联网应用开发建设、快速迭代的使用需求。
- 平台架构使用微服务领域及周边相关的各类新兴技术或主流技术进行建设，是帮助快速跨越架构技术选型、研究探索阶段的利器。
- 代码简洁规范、结构合理清晰，是新技术开发应用的典型的、综合性案例，助力开发人员对新兴技术的学习和掌握。

## 发布背景

自11月24日，Spring Boot 3.0 以及 Spring Cloud 2022.0.1、Spring Cloud Tencent 等全新版本发布，整个Java 社区也步入的 Java 17 和 Spring Boot 3 的新时代。紧跟 Java 技术和 Spring 社区的发展，让更多质量更好、性能更优的新特性服务于实际的开发工作，Dante Cloud 也同步进行升级及适配，开发了全新的 3.0 版本。

## Dante Cloud 3.0.0 新特性

### 1. 核心基础依赖便捷切换

- 新增 `Spring Cloud Tencent` 和 `Spring Cloud` 原生微服务全家桶等两种基础设施支持。
- 新增 `Spring Cloud Alibaba`、`Spring Cloud Tencent` 和 `Spring Cloud` 原生微服务全家桶三种基础设值切换能力，可以以相对便捷的方式切换使用 Alibaba、Tencent、Spring 等基础设施环境。可根据自身实际需求选择，不再局限于只能在某一种基础设施环境中运行。

### 2. 支持 `GraalVM` 原生镜像

- 整体调整各类模块 pom build 配置，适当增加冗余重复配置，以支持 `Spring Native` 或 `GraalVM` 编译需要。规避对所有模块进行 Native 编译，而导致错误问题。

### 3. `Spring Authorization Server` 全特性支持及扩展
- 基于 `Spring Authorization Server` 和 `Spring Data JPA` 实现多租户系统架构， 支持 Database 和 Schema 两种模式。
- 基于 `Spring Data JPA`，重新构建 `Spring Authorization Server` 基础数据存储代码，替代原有 JDBC 数据访问方式，破除 `Spring Authorization Server` 原有数据存储局限，扩展为更符合实际应用的方式和设计。
- 基于 `Spring Authorization Server`，在 OAuth 2.1 规范基础之上，增加自定义 `Resource Ownership Password` (密码) 认证模式，以兼容现有基于 OAuth 2 规范的、前后端分离的应用，支持 `Refresh Token` 的使用。
- 基于 `Spring Authorization Server`，在 OAuth 2.1 规范基础之上，增加自定义 `Social Credentials` (社会化登录) 认证模式，支持手机短信验证码、微信小程序、基于 `JustAuth` 的第三方应用登录， 支持 `Refresh Token` 的使用。
- 扩展 `Spring Authorization Server` 默认的 `Client Credentials` 模式，实现真正的使用 Scope 权限对接口进行验证。 增加客户端 Scope 的权限配置功能，并与已有的用户权限体系解耦
- 支持 `Spring Authorization Server` `Authorization Code PKCE` 认证模式
- 在 `Spring Authorization Server` 的标准的 `JWT Token` 加密校验方式外，支持基于自定义证书的 `JWT Token` 加密校验方式，可通过配置动态修改。
- 支持 `Opaque Token` (不透明令牌) 格式及校验方式，降低 `JWT Token` 被捕获解析的风险。可通过修改配置参数，设置默认 Token 格式是采用 `Opaque Token` 格式还是 `JWT Token` 格式。
- 全面支持 `OpenID Connect` (OIDC) 协议，系统使用时可根据使用需求，通过前端开关配置，快速切换 OIDC 模式和传统 OAuth2 模式
- 深度扩展 `Authorization Code`、`Resource Ownership Password`、`Social Credentials` 几种模式，全面融合 `IdToken`、`Opaque Token`、`JWT Token` 与现有权限体系，同时提供 `IdToken` 和 自定义 Token 扩展两种无须二次请求的用户信息传递方式，减少用户信息的频繁请求。
- 自定义 `Spring Authorization Server` 授权码模式登录认证页面和授权确认页面，授权码模式登录采用数据加密传输。支持多种验证码类型，暂不支持行为验证码。
- 无须在代码中配置 `Spring Security` 权限注解以及权限方法，即可实现接口鉴权以及权限的动态修改。采用分布式鉴权方案，规避 Gateway 统一鉴权的压力以及重复鉴权问题
- OAuth2 UserDetails 核心数据支持直连数据库获取和 Feign 远程调用两种模式。OAuth2 直连数据库模式性能更优，Feign 访问远程调用可扩展性更强。可通过配置动态修改采用策略方式。
- 基于自定义 Session，混合国密 `SM2` (非对称) 和 `SM4` (对称加密) 算法，实现基于数字信封技术的秘钥动态生成加密传输。利用 “一人一码机制”，实现密码模式登录数据进行动态加密传输。配合 OAuth2 Client 验证，保护接口调用和前后端数据传输的合理性及安全性。

### 4. 采用 `pnpm monorepo` 重构前端

- 前端工程包管理器变更为 pnpm。
- 采用 `monorepo` 模式对前端工程进行重构，抽取 utils、components、apis、bpmn-designer 等相关代码，形成共享模块
- 共享模块已进行优化配置，利用 Vite 可编译成独立的组件，单独以组件形式进行发布
- 代码以共享模块的方式进行单独维护开发，降低现有工程代码复杂度，便于后续功能的扩展和代码的复用。

## 升级说明

- 当前版本虽然仍旧集成了 Spring Cloud Alibaba，但目前仅能使用 Nacos 的配置中心功能，同时配合 Zookeeper 作为服务发现和注册使用。Sentinel 等相关功能暂时无法使用。建议现阶段先使用 Spring Cloud Tencent 作为基础设施支撑环境。

> 待 Spring Cloud Alibaba 完成适配，Dante Cloud 会马上进行更新升级。

- 因 Spring Boot 3 周边内容全部成熟和适配还需要一段时间，所以 2.7.X 将继续维护。想要体验最新的 3.0.0 版本，请检出 3.0.0 分支代码。

## 重要信息

> 不一定非要捐赠或者参与编写代码，才是参与开源项目的正确方式。点个 `Star`、提个格式规范的 ISSUE，也是在积极参与开源项目，更是对作者莫大的支持和鼓励。

> 开发新手在群内提问或新开 Issue 提问前，请先阅读 [【提问的智慧】](https://www.herodotus.cn/others/question/)，并确保认真、详细地查阅过本项目 [【在线文档】](https://www.herodotus.cn)，特别是【常见问题】章节。避免浪费大家的宝贵时间；

## [1]、总体架构

![输入图片说明](./readme/architecture.jpg)

> 部分功能演示，正在逐步添加

### （1） 方法级动态权限

![输入图片说明](./readme/preview/oauth2expression.gif)

### （2） 服务调用链监控

![输入图片说明](./readme/preview/skywalking.gif)

### （3） 灵活定制验证码

- 滑块拼图验证码

![滑块拼图验证码](./readme/captcha-jigsaw.png)

- 文字点选验证码

![文字点选验证码](./readme/captcha-word-click.png)

## [2]、功能介绍

<a href="https://www.herodotus.cn">详情见在线文档</a>

## [3]、技术栈和版本说明

### （1）Spring 全家桶及核心技术版本

| 组件                          | 版本             |
|-----------------------------|----------------|
| Spring Boot                 | 3.0.4          |
| Spring Cloud                | 2022.0.1       |
| Spring Cloud Alibaba        | 2022.0.0.0     |
| Spring Cloud Tencent        | 1.10.0-2022.0.1 |
| Spring Authorization Server | 1.1.0          |
| Spring Boot Admin           | 3.0.1          |
| Nacos                       | 2.2.1          |
| Sentinel                    | 1.8.5          |
| Seata                       | 1.5.2          |

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

- Vue3
- Vite4
- Pinia
- Typescript
- Quasar2
- Vue-Router 4
- Vueliate

## [4]、 版本号说明

本系统版本号，分为四段。

- 第一段和第二段，与 Spring Boot 版本对应，根据采用的 Spring Boot 版本变更。例如，当前采用 Spring Boot 2.4.6 版本，那么就以 2.4.X.X 开头
- 第三段，表示系统功能的变化
- 第四段，表示系统功能维护及优化情况

## [5]、工程结构

```
dante-cloud
├── configurations -- 配置文件脚本和统一Docker build上下文目录
├── dependencies -- 工程Maven顶级依赖，统一控制版本和依赖
├── module -- 依赖组件半成品拼装工程
├    ├── dante-module-common -- Module 相关模块公共辅助代码模块
├    ├── dante-module-metadata -- 权限元数据同步模块
├    ├── dante-module-security -- Security 相关配置代码模块
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
└──  └── dante-cloud-bpmn-logic -- 工作流基础代码包
```

## [6]、项目地址

- 后端主工程地址：[https://gitee.com/dromara/dante-cloud](https://gitee.com/dromara/dante-cloud)
- 后端核心组件库地址：[https://gitee.com/herodotus/dante-engine](https://gitee.com/herodotus/dante-engine)
- 后端单体版示例工程地址：[https://gitee.com/herodotus/dante-cloud-athena](https://gitee.com/herodotus/dante-cloud-athena)
- 前端工程地址：[https://gitee.com/herodotus/dante-cloud-ui](https://gitee.com/herodotus/dante-cloud-ui)

## [7]、用户权益

- 允许免费用于学习、毕设、公司项目、私活等。
- 遵循 Apache-2.0 开源协议 (保留代码中作者信息及版权说明即可)

## [8]、交流反馈

- 详见[【在线文档】](https://www.herodotus.cn) 交流反馈章节。

## [9]、界面预览

<table>
    <tr>
        <td><img src="./readme/ui1.jpg" alt="前端界面1"/></td>
        <td><img src="./readme/ui2.jpg" alt="前端界面2"/></td>
        <td><img src="./readme/ui3.jpg" alt="前端界面1"/></td>
    </tr>
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

## [10]、谁在使用 Dante Cloud

| 序号 | 名称     | 官网                    |
| ---- | -------- | ----------------------- |
| 1    | 轻喜到家 | <https://qxdaojia.com/> |

## [11]、鸣谢

### 赞助人列表

| 赞助人      | 赞助时间   |
| ----------- | ---------- |
| 偷土豆的人  | 2021-11-24 |
| lorron      | 2022-04-04 |
| 在云端      | 2022-12-26 |
| 西晽        | 2022-12-27 |
| p911gt3rs   | 2023-01-03 |
| jacsty      | 2023-01-31 |
| hubert_rust | 2023-03-16 |
| Zkey Z      | 2023-03-18 |

### 开源项目

- [Soybean Admin](https://gitee.com/honghuangdc/soybean-admin)
- [Vue Next Admin](https://gitee.com/lyt-top/vue-next-admin)
- [Vue VBen Admin](https://gitee.com/annsion/vue-vben-admin)
- [Quasar Admin Template](https://gitee.com/jinjinge/quasar-admin-template)

### 感谢 JetBrains 提供的免费开源 License

![https://jb.gg/OpenSourceSupport](./readme/jb_beam.svg)
