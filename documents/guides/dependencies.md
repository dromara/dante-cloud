# Eurynome Cloud 组件说明

## 一、packages

组件 | 说明
---|---
eurynome-cloud-common | 公共通用代码包，包含通用公共基础工具以及工具包
eurynome-cloud-data | 数据访问层配置基础包
eurynome-cloud-rest | REST接口及服务运行基础包
eurynome-cloud-crud | REST接口增删改查通用代码包
eurynome-cloud-security | REST服务安全管控核心通用代码包
eurynome-cloud-oauth | Oauth通用代码包
eurynome-cloud-oauth-starter | Oauth通用启动包
eurynome-cloud-kernel | REST服务化核心配置代码包
eurynome-cloud-message | 消息通讯基础包
eurynome-cloud-operation | 远程控制和操作基础包，目前只包含Nacos的配置导入操作
eurynome-cloud-starter | 平台服务接入核心starter

## 二、platform

组件 | 说明
---|---
eurynome-cloud-gateway | 公共通用代码包，包含通用公共基础工具以及工具包
eurynome-cloud-management | 
eurynome-cloud-uaa |

## 三、services

# 组件依赖关系

## 一、packages

顺序 | 组件 | 依赖组件 | 开源组件
---|---|---|---
1 | eurynome-cloud-data | eurynome-cloud-common | spring-boot-starter-data-jpa <br> spring-boot-starter-aop <br> spring-boot-starter-data-redis <br> spring-boot-starter-cache <br> jetcache-starter-redis-lettuce <br> p6spy <br> mysql-connector-java <br> postgresql <br> h2|
2 | eurynome-cloud-rest | eurynome-cloud-common | spring-boot-configuration-processor <br> spring-cloud-starter-openfeign (依赖其中部分包，使用openfeign省事一点) <br> springfox-boot-starter <br> spring-boot-starter-web <br> pring-boot-starter-undertow
3 | eurynome-cloud-crud | eurynome-cloud-data <br> eurynome-cloud-rest | ---
4 | eurynome-cloud-security | eurynome-cloud-crud | spring-cloud-starter-oauth2 <br> kaptcha
5 | eurynome-cloud-oauth | eurynome-cloud-security | ---
6 | eurynome-cloud-oauth-starter | eurynome-cloud-oauth <br> eurynome-cloud-upms-logic | ---
7 | eurynome-cloud-kernel | eurynome-cloud-common | javax.servlet-api <br> logstash-logback-encoder <br> spring-boot-configuration-processor <br> spring-cloud-starter-alibaba-nacos-discovery <br> spring-cloud-starter-alibaba-nacos-config <br> spring-cloud-starter-alibaba-sentinel <br> spring-cloud-starter-openfeign <br> spring-boot-starter-actuator <br> jasypt-spring-boot-starter
8 | eurynome-cloud-message | eurynome-cloud-common | spring-cloud-starter-bus-kafka
9 | eurynome-cloud-operation | eurynome-cloud-kernel | ---
10 | eurynome-cloud-starter | eurynome-cloud-crud (开启数据层和REST层配置) <br> eurynome-cloud-security (开启安全配置) <br> eurynome-cloud-kernel (开启服务化接入相关配置) <br> eurynome-cloud-message (开启消息通讯，传递信息) | ---

## 二、platform

顺序 | 组件 | 依赖组件 | 开源组件
---|---|---|---
1 | eurynome-cloud-gateway | eurynome-cloud-common | spring-boot-starter-data-jpa <br> spring-boot-starter-aop <br> spring-boot-starter-data-redis <br> spring-boot-starter-cache <br> jetcache-starter-redis-lettuce <br> p6spy <br> mysql-connector-java <br> postgresql <br> h2|
2 | eurynome-cloud-management | eurynome-cloud-common | spring-boot-configuration-processor <br> spring-cloud-starter-openfeign (依赖其中部分包，使用openfeign省事一点) <br> springfox-boot-starter <br> spring-boot-starter-web <br> pring-boot-starter-undertow
3 | eurynome-cloud-uaa | eurynome-cloud-data <br> eurynome-cloud-rest | ---

## 三、services

# 注解依赖关系

## 一、packages

顺序 | 组件 | 注解 | 注入配置或开启注解
---|---|---|---
1 | eurynome-cloud-data | @EnableRedisStorage | 配置：<br> RedisConfiguration
--- | --- | @EnableHerodotusData | 注解：<br> @EnableJpaAuditing <br> @EnableRedisStorage <br> @EnableCreateCacheAnnotation <br> <br> 配置：<br> DataConfiguration
2 | eurynome-cloud-rest | @EnableHerodotusRest | 配置：<br> RestConfiguration <br> RestTemplateConfiguration <br> SwaggerConfiguration <br> UndertowWebServerFactoryCustomizer <br> <br> 属性：<br> ApplicationProperties <br> RestProperties <br> SwaggerProperties
3 | eurynome-cloud-crud | @EnableHerodotusCrud | 注解：<br> @EnableHerodotusData <br> @EnableHerodotusRest <br> <br> 配置：<br> CrudConfiguration
4 | eurynome-cloud-security | @EnableHerodotusSecurity | 配置：<br> SecurityConfiguration <br> <br> 属性：<br> SecurityProperties
5 | eurynome-cloud-kernel | @EnableLogCenter | 配置：<br> LogstashConfiguration <br> <br> 属性：<br> ManagementProperties
--- | --- | @EnableHerodotusFeign | 配置：<br> FeignConfiguration
--- | --- | @EnableHerodotusKernel | 注解：<br> @EnableLogCenter <br> @EnableHerodotusFeign
6 | eurynome-cloud-message | @EnableMessageBus | 配置：<br> MessageBusConfiguration
--- | --- | @EnableHerodotusMessage | 注解：<br> @EnableMessageBus <br> <br> 配置：<br> MessageConfiguration
7 | eurynome-cloud-operation | @EnableHerodotusManagement | 配置：<br> OperationConfiguration
8 | eurynome-cloud-starter | --- | 注解：<br> eurynome-cloud-crud <br> eurynome-cloud-security <br> @EnableHerodotusKernel <br> <br> 配置：<br> JacksonAutoConfiguration <br> ResourceServerAutoConfiguration <br> WebMvcAutoConfiguration

## 二、platform

## 三、services