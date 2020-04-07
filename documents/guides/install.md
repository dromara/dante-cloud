# Eurynome Cloud 部署说明

## （一）基础环境

### 1、MySQL安装
### 2、PostgreSQL安装
### 3、Redis安装
### 4、Nacos部署
### 5、Kafka安装
### 6、ELK部署
### 7、本地docker-compose部署

除了MySQL、PostgreSQL和Redis以外，其它相关运行环境组件，可以通过docker-compose方式进行部署，执行以下命令即可：

- 运行容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/env.base.yml  up -d
```
> 该命令会查看本地是否已经有了相关镜像，如果没有会首先下载镜像，然后再运行，请先保证网络畅通

- 停止容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/env.base.yml stop
```

- 停止并删除容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/env.base.yml down
```

## （二）运行监控管理工具
### 1、Sentinel部署
### 2、Skywalking部署
### 3、本地docker-compose部署

> 注意：由于Skywalking采用elasticsearch进行数据存储，因此Skywalking运行之前，要保证elasticsearch已经正常运行。虽然docker-compose有depends_on属性，但是该属性只能解决容器启动的先后顺序问题，并不能实现某一个容器及容器内部所有应用启动成功之后再运行的问题。该问题目前还没有时间深入研究解决、

> 因此，这里将环境依赖的应用，拆分为两个docker-compose文件，先运行env.base.yml，待所有容器及内部服务成功运行之后，再运行env.skywalking.yml

通过docker-compose方式进行部署，执行以下命令即可：

- 运行容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/env.skywalking.yml  up -d
```
> 该命令会查看本地是否已经有了相关镜像，如果没有会首先下载镜像，然后再运行，请先保证网络畅通

- 停止容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/env.skywalking.yml stop
```

- 停止并删除容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/env.skywalking.yml down
```

## （三）服务打包部署

### 1、多环境配置

#### 找到根目录中的pom.xml文件，找到profiles配置，根据不同的环境进行相关信息的配置。

- Profiles说明

Profile | 环境 | 说明
---|---|---
development | 本地开发环境 | 一般该环境都使用127.0.0.1作为开发IP地址
docker | docker环境 | docker开发打包环境。即使在本地使用Docker由于虚拟机网络问题，所以在这种模式下，需要是用本机真实IP
testing | 测试环境 | 根据实际情况定义
production | 正式生产环境 | 根据实际情况定义

- 配置说明

配置项 | 说明 | 用途
---|---|---
profile.name | Profile 名称 | 用于从Nacos中根据Profile获取不同的配置
database.type | 数据库类型 | 用于从Nacos中获取不同数据库连接的配置
config.server-addr | Nacos配置中心地址 |
config.namespace | Nacos配置中心命名空间 |用于支持多环境.这里必须使用ID，不能使用名称,默认为空
discovery.server-add | Nacos服务发现地址 | 
sentinel.server-addr | alibaba sentinel 地址 | 具体参见Sentinel用途
docker.build.directory | 自定义docker-compose构建文件夹 |  为了便于Docker的构建，减少上下文影响，将所有Docker资源放置在同一个目录中进行构建

### 2、Jar文件部署

这个就不再多说了。

### 3、Docker-compose部署

为了解决服务链路跟踪及监控的问题，服务中引入了Skywalking。

服务如果要使用Skywalking，需要在服务的运行命令中指定Skywalking的agent，如下命令

```
java -javaagent:/skywalking-agent.jar=agent.service_name=${SW_AGENT_SERVICE_NAME},collector.backend_service=${SW_COLLECTOR_BACKEND_SERVICE} ${JAVA_OPTS} -jar /app.jar
```

如果服务的docker也要使用Skywalking，就需要把skywalking-agent.jar一并打入docker中。

由于Docker-compose打包上下文的问题，就需要在每一个模块中都复制一份skywalking-agent.jar。很不好管理，看着也不舒服。为了解决这个问题，就在所有模块之外之指定了一个统一的目录，将需要打包的jar以及dockerfile全部放入该目录，通过该目录形成一个统一的上下文环境。

- （1）设置Skywalking-oap-service地址

> 工程中已有的services.yml文件，是在假设所有服务和Skywalking-oap-service运行在同一个网络情况下进行的设定，多用于本地调试和开发。参见：

```docker
SW_COLLECTOR_BACKEND_SERVICE: oap:11800
```

> 如果不是以上情况，请结合实际进行修改

- 复制服务jar到Docker-compose打包指定目录
1. 可以通过手工方式，在mvn clean install 之后，手工复制服务的jar包到指定目录。该目录是通过根目录中pom.xml进行配置
2. 各个服务中，已经增加了Maven Plugin，可以通过 mvn clean package进行jar的拷贝。

- 执行Docker-compose.yml

在命令行中，执行以下命令即可

- 运行容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/services.yml  up -d
```

> 该命令会查看本地是否已经有了相关镜像，如果没有会首先构建镜像，然后再运行，请先保证网络畅通

- 停止容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/services.yml stop
```

- 停止并删除容器

```docker
docker-compose -f ${project home}/eurynome-cloud/documents/docker/docker-compose/herodotus/services.yml down
```

### 3、Docker-compose部署