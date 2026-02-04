<p align="center"><img src="./readme/new-logo.png" height="300" width="400" alt="logo"/></p>
<h2 align="center">简洁优雅 · 稳定高效 | 宁静致远 · 精益求精 </h2>
<p align="center">“一套代码、两种架构，可以灵活切换微服务和单体两种架构”的企业级云原生微服务基座</p>

---

<p align="center">
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-4.0.2-blue.svg?logo=springboot" alt="Spring Boot 4.0.2"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud-2025.1.1-blue.svg?logo=springboot" alt="Spring Cloud 2025.1.1"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.1.0.0-blue.svg?logo=alibabadotcom" alt="Spring Cloud Alibaba 2025.1.0.0"></a>
    <a href="https://github.com/Tencent/spring-cloud-tencent" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Tencent-2.1.0.3--2024.0.2-blue.svg?logo=qq" alt="Spring Cloud Tencent 2.1.0.3-2024.0.2"></a>
    <a href="https://nacos.io/zh-cn/index.html" target="_blank"><img src="https://img.shields.io/badge/Nacos-3.1.1-brightgreen.svg?logo=alibabadotcom" alt="Nacos 3.1.1"></a>
</p>
<p align="center">
    <a href="#" target="_blank"><img src="https://img.shields.io/badge/Version-4.0.2.0-M3-red.svg?logo=spring" alt="Version 4.0.2.0-M3"></a>
    <a href="https://gitee.com/dromara/dante-cloud/issues/IDKCZD" target="_blank"><img src="https://img.shields.io/badge/Dante%20Engine-4.0.2.0-M3-red.svg?logo=spring" alt="Dante Engine 4.0.2.0-M3"></a>
    <a href="https://bell-sw.com/pages/downloads/#downloads" target="_blank"><img src="https://img.shields.io/badge/JDK-25%2B-green.svg?logo=openjdk" alt="Java 25"></a>
    <a href="./LICENSE"><img src="https://shields.io/badge/License-Apache--2.0-blue.svg?logo=apache" alt="License Apache 2.0"></a>
    <a href="https://blog.csdn.net/Pointer_v" target="_blank"><img src="https://img.shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="https://github.com/dromara/dante-cloud"><img src="https://img.shields.io/github/stars/dromara/dante-cloud?style=flat&logo=github" alt="Github star"></a>
    <a href="https://github.com/dromara/dante-cloud"><img src="https://img.shields.io/github/forks/dromara/dante-cloud?style=flat&logo=github" alt="Github fork"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://gitee.com/dromara/dante-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://gitee.com/dromara/dante-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
    <a href='https://gitcode.com/dromara/dante-cloud'><img src='https://gitcode.com/dromara/dante-cloud/star/badge.svg' alt='Gitee star'></a>
</p>
<p align="center">
    <a href="https://gitcode.com/dromara/dante-cloud">GitCode 仓库</a> &nbsp; | &nbsp;
    <a href="https://github.com/dromara/dante-cloud">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/dromara/dante-cloud">Gitee 仓库</a> &nbsp; | &nbsp;
    <a href="https://www.herodotus.vip">在线文档</a>
</p>

<h1 align="center"> 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！</h1>

---

## 重要说明

因诸多周边依赖尚未适配 Spring Boot 4 和 Spring Cloud 2025.1.1。当前 4.0.X 版本还在持续开发中。之所以将不完善代码同步至 Master 分支，仅是为了作为代码标记、补充Git变更记录、验证组件库自动发布。便于后续开发以及未来用户更新代码，防止变更过多引起检出失败等问题。目前很多功能不保证可用，如需使用建议使用 **3.5.X 分支** 代码。

## 企业级技术中台与云原生微服务基座

**Dante Cloud** 国内首个支持阻塞式和响应式服务并行的、开箱即用的企业级云原生微服务基座。是采用**领域驱动模型(DDD)**设计思想，以「**高质量代码、低安全漏洞**」为核心，基于 Spring 生态全域开源技术，高度**模块化和组件化设计**，支持**智能电视、IoT等物联网设备**认证，满足**国家三级等保要求**，支持**接口国密数字信封加解密**等一系列安全体系的一站式多租户微服务解决方案。独创的可以“**一套代码实现微服务和单体两种架构灵活切换**”的企业级应用系统。

### 一、项目理念

**Dante Cloud** 一直秉承着“简洁、高效、包容、务实”的理念，使用微服务领域及周边相关的各类新兴技术或主流技术进行建设，不断地深耕细作、去粗取精、用心打造。目标是构建一款`代码质量高、维护投入低、安全防护强`的微服务基座，可以帮助用户快速跨越架构技术选型、技术研究探索、基础架构搭建阶段，直接聚焦业务开发。极大地降低传统项目中因安全漏洞、技术负债、低质代码等潜在隐患所产生的高维护投入。期望像项目名字寓意一样，构建一套可以在在行业变革的时期承上启下，助力企业信息化建设和数字化转型的产品。

**Dante Cloud** 核心关注点是：**「高质量的系统代码」**、**「合理的系统架构」**、**「低耦合的模块划分」**、**「高安全性系统实现」**、**「灵活的功能扩展能力」**，**「优质的微服务防范」**。不会像其它一些系统一样，追求 **业务功能** 的 **丰富** 性。堆叠大量无法做到真正通用的功能，反倒会成为负担和干扰，不如由用户自己按照需求灵活设计和实现。

### 二、架构设计

**Dante Cloud** 优秀的模块化能力，为系统提供了高度灵活的配置能力、**功能的“可插拔”能力** 以及不同需求场景的适配能力。正因为优秀的模块化体系，使得 **Dante Cloud** 不仅是一套完整的微服务架构，还是一套高质量的 **「单体模块化」** 系统。这里的微服务架构和单体架构并不是分离的两套代码，也不是分离的两个项目。而是完全融合的一整套代码，使用时可以根据需要选择是以微服务模式或者单体模式运行，配合灵活的模块能力，实现系统的多样化定制和功能的管控。

这是 Dante Cloud 微服务最大的特色之一：**“一套代码、两种架构”**。可以帮助企业在项目早期以单体架构快速建设项目、方便开发人员在本地进行开发以及新技术研究。在项目后期随着用户规模增大以及并发需求提升时，可以快速无缝迁移至微服务架构。

### 三、适用用户

微服务技术并不是 **「落伍」** 了，而是进入了 **「成熟期」**，它的 **「适用场景和边界被更清晰地定义」** 了。微服务不再是一个 **「必须要有」** 的选项，而是一个 **「权衡之后」** 的选择。

**Dante Cloud** 也并未使用任何复杂难懂或难以上手掌握的技术，项目中所涉及核心关键组件中，其中 **「近 80% 均为 Spring 生态原生组件」**。技术实现均为各组件标准用法的组合与应用，编码风格和代码设计一直也在极尽努力尽量与 Spring 生态的标准规范用法保持一致，只不过经过大量的版本迭代和重构之后逐渐形成了一定的封装与抽象。

因此，我们推荐以下用户选择和使用 **Dante Cloud**

- **「传统项目用户」**：可以先体验和使用单体版，先从“前后端分离”以及“多端适配”开始，尝试不同于传统内嵌页面的开发模式。之后也可以平滑迁移至微服务版。
- **「数字转型用户」**：如果您正在考虑进行数字化转型，可以直接选择使用微服务版，不用再为“基础组件碎片化，需花大量时间整合、踩坑版本兼容”等问题而苦恼。
- **「复杂项目用户」**：如果您的业务复杂度上升到一定阶段，可以直接选择使用微服务版，直接聚焦于业务开发，节省大量前期搭建基础设施、解决通用技术问题的时间。
- **「初创团队用户」**：可以先使用单体版进行开发，只要代码放置规范、模块划分合理，后期可以根据需要无缝迁移至微服务架构
- **「技术突破用户」**：本项目并不拘泥局限于常规成熟的技术内容，目标是探索新型技术并用其来为业务的创新服务。喜欢技术尝鲜和突破的用户推荐选择使用。
- **「学习提升用户」**：本项目代码实现优雅和领域划分清晰，编码风格和模块实现尽最大可能与 Spring 生态规范保持一致，是深入学习 Spring 生态组件和提升技能的优秀案例

> 想要从传统项目转型至微服务项目的用户，**建议详细阅读《企业IT架构转型之道：阿里巴巴中台战略思想与架构实战》一书（可以先读前几章）之后再上手本项目！**

对于以下用户我们不建议选择 **Dante Cloud**

- **「单体拥趸用户」**：如果您觉得单体架构可以满足您所有的架构需求，微服务繁琐庞大无法比拟单体的“方便”、“快捷”，那么建议选择其它更专业的单体项目。
- **「单一前端用户」**：如果您的项目只会有Web端，不会涉及小程序、移动端、桌面端等其它类型终端，未来也不需要考虑并发或者扩展等问题，选择本项目大材小用。
- **「主流技术用户」**：如果您只擅长 mysql、mybatis 等主流技术，而且并不希望使用任何新技术。那么本项目所涉及的基础技术体系可能会让您觉得格格不入建议选择更适配您技术体系项目。
- **「功能丰富用户」**：本项目定位是基础平台，自认为没有能力做到功能既丰富又通用，如果您追求拥有丰富的、开箱即用的功能后台系统，那么本项目并不适合。
- **「极简编程用户」**：如果您希望基于自己现有知识，不看文档不用学习就可以快速搭建应用，或者通过拖拽生成代码简化开发，这与本项目的产品定位、开发理念、设计哲学背道而驰。
- **「审美品鉴用户」**：本项目前端是使用组件库纯手搓构建，没有专业的美工也没有照搬主流框架，初衷是为后端开发人员接触前端提供一条更友好的途径，所以不能保证符合您的审美。

## [1]、开源协议

### 1. 变更声明

自 **v3.3.6.0** 版本起，Dante Cloud 以及 Dante OSS 和 Dante Engine 子项目开源协议 **【永久】** 变更为 Apache License Version 2.0。可用于个人学习、毕设，允许商业使用，但禁止二次开源。

### 2. 补充条款

使用时务必遵守以下补充条款。

- 不得将本软件应用于危害国家安全、荣誉和利益的行为，不能以任何形式用于非法为目的的行为。
- 在延伸的代码中（修改现有源代码衍生的代码中）需要带有原来代码中的协议、版权声明和其他原作者 规定需要包含的说明（请尊重原作者的著作权，不要删除或修改文件中的Copyright和@author信息） 更不要，全局替换源代码中的 Dante Cloud、Herodotus 或 码匠君 等字样，否则你将违反本协议条款承担责任。
- 您若套用本软件的一些代码或功能参考，请保留源文件中的版权和作者，需要在您的软件介绍明显位置 说明出处，举例：本软件基于 Dante Cloud 微服务架构或 Dante Engine，并附带链接：<https://www.herodotus.vip>
- 任何基于本软件而产生的一切法律纠纷和责任，均与作者无关。
- 如果你对本软件有改进，希望可以贡献给我们，双向奔赴互相成就才是王道。
- 本项目已申请软件著作权，请尊重开源。

### 3. 重要说明

因频繁出现修改包名、删除作者版权信息、二次开源的行为，为保护作者权益，Dante Engine 子项目需登记申请，等审批后开放源码，[【查看详情或登记】](https://gitee.com/dromara/dante-cloud/issues/IDKCZD)

## [2]、安全测试

Dante Cloud 已通过由第三方进行的软件出厂安全测试以及等保测试。详情参见：[安全测试说明](https://www.herodotus.vip/guide/security/compliance.html)

## [3]、总体架构

<p align="center">
  <img width="100%" src="./readme/architecture.svg" alt="Dante Cloud Architecture"/>
</p>

## [4]、功能展示

### （1） Passkey 通用密钥「无密码」登录

![Passkey功能示意图](./readme/preview/passkey.gif)

### （2） 方法级动态权限

![方法级动态权限功能示意](./readme/preview/oauth2expression.gif)

### （3） Minio 控制台使用 Dante Cloud 登录认证

![Minio登录示意](./readme/preview/minio-login.gif)

### （4） 服务调用链监控

![服务调用链](./readme/preview/skywalking.gif)

## [5]、工程结构

### 1. 主工程结构

```shell
dante-cloud
├── configurations -- 配置文件脚本和统一Docker build上下文目录
├── dependencies -- 工程Maven顶级依赖，统一控制版本和依赖
├── module -- “半成品”组件模块分组(可以再此建立业务代码模块，以模块的形式实现代码的共享以及架构的切换)
├    ├── dante-module-common -- 微服务版公共辅助代码模块
├    └── dante-monomer-autoconfigure -- 单体版自动配置模块
├── packages -- 基础核心Starter
├    ├── authentication-spring-boot-starter -- OAuth2 授权服务器自动配置 Starter(主要用于微服务版和单体版统一认证)
├    ├── authorization-servlet-spring-boot-starter -- 阻塞式 OAuth2 资源服务器自动配置 Starter(除了 Monitor 等特殊服务以外，所有服务均需依赖)
├    ├── facility-spring-boot-starter -- 基础设施切换依赖 Starter(用于切换 Alibaba、Tencent和原生微服务基础设施环境)
├    ├── rpc-client-uaa-spring-boot-starter -- 服务间通信客户端自动配置 Starter(UAA 作为客户端端访问其它服务)
├    └── rpc-server-upms-spring-boot-starter -- 服务间通信服务端自动配置 Starter(UPMS 作为服务端为其它服务提供访问)
├── platform -- 平台核心服务
├    ├── dante-cloud-gateway -- 统一网关服务
├    ├── dante-cloud-message -- 平台消息服务
├    ├── dante-cloud-monitor -- Spring Boot Admin 监控服务
├    ├── dante-cloud-uaa -- 账户管理和统一认证模块
├    └── dante-cloud-upms -- 统一权限管理系统服务
├── services -- 平台业务服务
├    ├── dante-cloud-bpmn-ability -- 工作流服务
├    ├── dante-cloud-bpmn-logic -- 工作流基础代码模块
├    ├── dante-cloud-oss-ability -- 对象存储服务
└──  └── dante-monomer-application -- Dante Cloud 单体版应用模块
```

### 2. 组件库结构

```shell
dante-engine
├── dante-assistant -- 辅助功能模块
├    ├── dante-assistant-access -- 第三方登录接入辅助功能模块
├    ├── dante-assistant-captcha -- 验证码辅助功能模块
├    └── dante-assistant-oss -- AWS SDK V2 对象存储辅助功能模
├── dante-bom -- 工程 Maven 顶级依赖，统一控制版本和依赖
├── dante-cache -- 缓存功能模块
├    ├── dante-cache-caffeine -- Caffeine 缓存功能封装模块
├    ├── dante-cache-commons -- 缓存通用代码模块
├    ├── dante-cache-jetcache -- JetCache 缓存功能封装模块
├    └── dante-cache-redis -- Redis 缓存功能封装模块
├── dante-data -- 数据访问模块
├    ├── dante-data-commons -- 数据访问通用代码模块
├    ├── dante-data-jpa -- 以 JPA 作为数据访问层的通用代码模块
├    ├── dante-data-mongodb -- 以 MongoDB 作为数据访问层的通用代码模块
├    └── dante-data-rest -- 关联数据访问层的REST开发通用代码模块
├── dante-framework -- Dante Cloud 框架核心基础模块
├    ├── dante-autoconfigure -- 基础自动配置模块
├    ├── dante-core -- 核心定义模块
├    ├── dante-hibernate -- Hibernate 扩展模块
├    ├── dante-security -- Security 相关基础代码模块
├    ├── dante-spring -- SpringBoot 相关基础代码模块
├    └── dante-web -- Web 服务 相关基础代码模块
├── dante-logic -- 系统内置功能业务逻辑模块
├    ├── dante-logic-identity -- 身份认证功能业务逻辑模块
├    ├── dante-logic-message -- 系统消息业务逻辑模块
├    └── dante-logic-upms -- UPMS 业务逻辑模块
├── dante-message -- 消息模块
├    ├── dante-message-autoconfigure -- 消息自动配置模块
├    ├── dante-message-commons -- 消息通用代码模块
├    └── dante-message-servlet-websocket -- 基于 Servlet 环境下的 Websocket 功能封装模块
├── dante-oauth2 -- OAuth2 认证模块
├    ├── dante-authentication-autoconfigure -- OAuth2 授权服务器基础内容自动配置模块
├    ├── dante-authorization-autoconfigure -- OAuth2 资源服务器基础内容自动配置模块
├    ├── dante-oauth2-authentication -- Spring Authorization Server 授权服务器核心功能封装模块
├    ├── dante-oauth2-authorization -- Spring Authorization Server 资源服务器核心功能封装模块
├    ├── dante-oauth2-commons -- OAuth2 共性通用代码模块
├    ├── dante-oauth2-extension -- Spring Authorization Server 功能扩展模块
├    └── dante-oauth2-persistence-jpa -- 以 JPA 作为 SAS 核心数据访问层代码实现模块
├── dante-rest -- 系统内置功能 REST 接口模块
├    ├── dante-rest-oss -- 对象存储功能 REST 接口模块
├    ├── dante-rest-identity -- 身份认证功能 Servlet 环境 REST 接口模块
├    ├── dante-rest-message -- 消息功能 Servlet 环境 REST 接口模块
├    └── dante-rest-upms -- UPMS 功能 Servlet 环境 REST 接口模块
├── dante-starter -- Starters
├    ├── cache-spring-boot-starter -- 缓存自动配置 Starter
├    ├── captcha-spring-boot-starter -- 验证码自动配置 Starter
├    ├── data-mongodb-spring-boot-starter -- MongoDB 数据访问层自动配置 Starter
├    ├── data-rdbms-spring-boot-starter -- 关系型数据库数据访问层自动配置 Starter
├    ├── facility-alibaba-spring-boot-starter -- 面向 Spring Cloud Alibaba 的微服务基础设施适配 Starter
├    ├── facility-gateway-spring-boot-starter -- Alibaba Sentinel 在 Gateway 环境下基础设施适配 Starter
├    ├── facility-kafka-spring-boot-starter -- 基于 Kafka 的消息事件自动配置 Starter
├    ├── facility-tencent-spring-boot-starter -- 面向 Spring Cloud Tencent 的微服务基础设施适配模块 Starter
├    ├── logging-spring-boot-starter -- 日志收集和聚合自动配置 Starter
├    ├── oss-spring-boot-starter -- 基于 AWS SDK V2 对象存储自动配置 Starter
├    ├── reactive-container-spring-boot-starter -- Reactive 容器基础配置自动配置 Starter
├    ├── servlet-container-spring-boot-starter -- 基于 JPA 的多租户自动配置 Starter
├    ├── servlet-message-spring-boot-starter -- Servlet 环境消息模块自动配置 Starter
├    ├── tenant-spring-boot-starter -- 基于 JPA 的多租户自动配置 Starter
├    ├── web-spring-boot-starter -- Web 微服务通用 Starter
├    └── webmvc-spring-boot-starter -- WebMvc 类型应用自动配置 Starter
└──  readme -- README 相关素材放置目录
```

## [6]、 版本和分支

### 一、版本号说明

本系统版本号，分为四段。

- 第一段和第二段，与 Spring Boot 版本对应，根据采用的 Spring Boot 版本变更。例如，当前采用 Spring Boot 2.4.6 版本，那么就以
  2.4.X.X 开头
- 第三段，表示系统功能的变化
- 第四段，表示系统功能维护及优化情况

### 二、分支说明

|          分支名称          | 对应 Spring 生态版本                          | 对应 JDK 版本 | 用途             | 现状                                                          |
|:----------------------:|-----------------------------------------|-----------|----------------|-------------------------------------------------------------|
|         master         | Spring Boot 4.0 和 Spring Cloud 2025.1.X | JDK 25    | 主要发布分支         | 推荐使用代码分支                                                    |
|        develop         | Spring Boot 4.0 和 Spring Cloud 2025.1.X | JDK 25    | Development 分支 | 新功能、ISSUE 均以此分支作为开发，发布后会 PR 至 master 分支                     |
|         3.5.X          | Spring Boot 3.5 和 Spring Cloud 2025.0.X | JDK 17    | 历史版本           | 基于 Spring Boot 3.5 时代开发的代码分支，稳定可用                           |
|         3.4.X          | Spring Boot 3.4 和 Spring Cloud 2024.0.X | JDK 17    | 历史版本，停止维护      | 基于 Spring Boot 3.4 时代开发的代码分支，稳定可用，不再维护                      |
|      3.2.X-3.3.X       | Spring Boot 3.3 和 Spring Cloud 2023.0.X | JDK 17    | 历史版本，停止维护      | 基于 Spring Boot 3.3 时代开发的代码分支，稳定可用，不再维护                      |
|         3.1.X          | Spring Boot 3.1 和 Spring Cloud 2022.0.X | JDK 17    | 历史版本，停止维护      | 基于 Spring Boot 3.1 时代开发的代码分支，稳定可用，不再维护                      |
|         2.7.X          | Spring Boot 2.7 和 Spring Cloud 2021.0.X | JDK 8     | 历史版本，停止维护      | 基于 Spring Boot 2.7 时代开发的代码分支，稳定可用，不再维护                      |
| spring-security-oauth2 | Spring Boot 2.6 和 Spring Cloud 2021.0.X | JDK 8     | 历史代码，不再维护      | 基于原 Spring Security OAuth2 实现的微服务，稳定可用，因相关组件均不在维护，所以该版本不再维护 |

## [7]、如何升级

尽量不修改 Dante Cloud 以外的项目源码，如 Dante Engine、Dante OSS。如果修改了，请 Pull Requests 上来，否则代码与官方不同步，可能会将对你的日后升级增加难度。

每个版本升级，我们都会附带详细更新日志：https://www.herodotus.vip/logs/ 在这里，你可以看到 Dante Cloud 新增哪些新功能和改进。
针对有较大变化、差异的版本，我们都会负载详细的升级指南：https://www.herodotus.vip/breaking/ 在这里，你可以看到 Dante Cloud 不同版本对应升级方法。

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
    <tr>
        <td><img src="./readme/tempo-1.png" alt="轻量级日志聚合及链路分析1"/></td>
        <td><img src="./readme/tempo-2.png" alt="轻量级日志聚合及链路分析2"/></td>
        <td><img src="./readme/zipkin-1.png" alt="轻量级链路分析1"/></td>
    </tr>
    <tr>
        <td><img src="./readme/zipkin-2.png" alt="轻量级链路分析2"/></td>
        <td><img src="./readme/zipkin-3.png" alt="轻量级链路分析3"/></td>
        <td><img src="./readme/zipkin-4.png" alt="轻量级链路分析4"/></td>
    </tr>
</table>

## [9]、鸣谢

### 赞助人列表

| 序号 |                    赞助人                     |    赞助时间    | 序号 |                      赞助人                       |    赞助时间    | 序号 |                   赞助人                    |    赞助时间    |
|:--:|:------------------------------------------:|:----------:|:--:|:----------------------------------------------:|:----------:|:--:|:----------------------------------------:|:----------:|
| 1  |                   ご沉默菋噵                    | 2021-10-25 | 2  |    [偷土豆的人](https://gitee.com/dongzongyang)     | 2021-11-24 | 3  |    [lorron](https://gitee.com/lorron)    | 2022-04-04 |
| 4  |                    在云端                     | 2022-12-26 | 5  |        [西晽](https://gitee.com/syllvin)         | 2022-12-27 | 6  | [p911gt3rs](https://gitee.com/p911gt3rs) | 2023-01-03 |
| 7  |     [jacsty](https://gitee.com/jacsty)     | 2023-01-31 | 8  |  [hubert_rust](https://gitee.com/hubert_rust)  | 2023-03-16 | 9  |   [Zkey Z](https://gitee.com/zkeyzvip)   | 2023-03-18 |
| 10 |      [志国欧巴](https://gitee.com/zzgf16)      | 2023-03-27 | 11 |  [michael](https://gitee.com/yiminyangguang)   | 2023-04-07 | 12 |  [大叔丨小巷](https://gitee.com/yushui_wang)  | 2023-04-11 |
| 13 |   [sun_left](https://gitee.com/sun_left)   | 2023-04-19 | 14 | [time、sand](https://gitee.com/tzy15680905763)  | 2023-07-06 | 15 |   [印第安老斑鸠](https://gitee.com/deadncut)   | 2023-09-09 |
| 16 |                    一阵风                     | 2023-12-10 | 17 |                      Jack                      | 2024-03-01 | 18 | [onehelper](https://gitee.com/onehelper) | 2024-05-24 |
| 19 | [宁哥](https://gitee.com/seabuckthorn-syrup) | 2024-05-29 | 20 | [cryptoneedle](https://gitee.com/cryptoneedle) | 2024-11-01 | 21 |                  别吓跑我的鱼                  | 2025-01-10 |
| 22 |   [代码裁缝](https://gitee.com/code-tailor)    | 2025-03-01 | 23 |                    Go ahead                    | 2025-03-10 | 24 |    [hoku](https://gitee.com/hoku888)     | 2025-06-18 |
| 25 |                     飞翔                     | 2025-07-19 | 26 |                                                |            | 27 |                                          |            |

## [10]、关注我

<table align="center">
  <tr>
    <th align="center">
      <p>公众号：码匠君</p>
    </th>
  </tr>
  <tr>
    <td align="center">
      <img src="./readme/公众号.jpg" alt="公众号" height="200px">
    </td>
  </tr>
</table>