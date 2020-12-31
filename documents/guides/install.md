# Eurynome Cloud 部署说明

主要版本说明：

组件 | 版本 | 说明
---|---|---
Spring Boot | 2.3.7.RELEASE | 功能版本要与其它组件匹配
Spring Cloud | Hoxton.SR9 | 
Spring Cloud Alibaba | 2.2.3.RELEASE 
Spring Boot Admin | 2.3.0 | 
Nacos | 1.3.2 |
Sentinel | 1.8.0 |
Skywalking | latest |
Jetcache | 2.6.0 | 
ELK | latest |
logstash-logback-encoder | 6.4 | 使用该组件直接向ELK发送日志
MySQL | 5.7.26 |
PostgreSQL | 12.3-1 |
Redis | 3.2.100 | 为了图省事，还是用的可以在Windows下直接安装版本老版本。使用最新版也可，只要支持lettuce就行。
Docker Desktop for Window | latest

Alibaba相关组件版本配关系，地址： [https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E][官网说明地址]

**组件版本关系**

Spring Cloud Alibaba Version | Sentinel Version | Nacos Version | RocketMQ Version | Dubbo Version | Seata Version
---|---|---|---|---|---
2.2.3.RELEASE or 2.1.3.RELEASE or 2.0.3.RELEASE | 1.8.0 | 1.3.3 | 4.4.0 | 2.7.8 | 1.3.0
2.2.1.RELEASE or 2.1.2.RELEASE or 2.0.2.RELEASE | 1.7.1 | 1.2.1 | 4.4.0 | 2.7.6 | 1.2.0
2.2.0.RELEASE | 1.7.1 | 1.1.4 | 4.4.0 | 2.7.4.1 | 1.0.0
2.1.2.RELEASE or 2.0.2.RELEASE | 1.7.1 | 1.2.1 | 4.4.0 | 2.7.6 | 1.1.0
2.1.1.RELEASE or 2.0.1.RELEASE or 1.5.1.RELEASE | 1.7.0 | 1.1.4 | 4.4.0 | 2.7.3 | 0.9.0
2.1.0.RELEASE or 2.0.0.RELEASE or 1.5.0.RELEASE | 1.6.3 | 1.1.1 | 4.4.0 | 2.7.3 | 0.7.1

**毕业版本依赖关系(推荐使用)**

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

## （一）基础环境

### 1、MySQL安装

> 目前Nacos还是依赖于Mysql数据，等Nacos不依赖于Mysql数据库以后，根据情况进行迁移，最好与现有存储一致，减少应用软件的使用减低复杂度
（Nacos后续会解决对MySQL依赖问题， [https://nacos.io/zh-cn/docs/feature-list.html][官网说明] ）

（1）、安装Mysql数据库

正常安装即可，由于Mysql8和Mysql5版本的驱动差异比较大，建议安装Mysql5，可以减少不可预知的问题。

> nacos 1.3.2 已经将存储升级为MySQL 8 refer：https://github.com/nacos-group/nacos-docker/blob/master/README_ZH.md
> 这个版本的nacos docker，想要连接到外部的MySQL，需要修改容器中的数据库连接，否则无法连接上数据库会出错。

```
vim conf/application.properties

// 在数据库连接中增加
allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8
```

（2）、创建Nacos使用的数据库

即新建用户、创建数据库、分配权限等。不会的请Baidu。

（3）、导入Nacos初始脚本。

下载最新稳定版的Nacos压缩包，[https://github.com/alibaba/nacos/releases][下载地址]。解压后，在{NACOS_HOME}/conf目录下，找到nacos-mysql.sql导入到数据库中。

> 在本工程的${project home}/eurynome-cloud/documents/scripts目录下也保存了一份，如果不是最新请重新下载。

（4）、开放数据IP访问

本地开发，安装的mysql服务，使用localhost可以连接就以足够。但是要使用Docker，必须通过ip地址才能访问。这就需要开放数据的IP访问。
否则会出现Docker 容器发无法连接数据库的问题。

进入mysql命令界面，输入：

```mysql
mysql> select host,user from mysql.user;
```

结果示例：

host | user
---|---
localhost | root
localhost | mysql.infoschema
localhost | mysql.session
localhost | mysql.sys

> host字段的值是localhost，说明只允许本地访问

设置允许任意IP访问，执行语句：

```mysql
mysql> update mysql.user set host = '%' where user = 'root';

mysql> flush privileges;
```

### 2、PostgreSQL安装

目前，主要服务都是采用PostgreSQL进行数据存储，所以需要安装PostgreSQL，如果你不是使用PostgreSQL可以跳过。

（1）、安装PostgreSQL数据库

正常安装即可。跟随安装的Wizard一步一步进行即可。

> 注意：在window下面安装高版本的pg，注意在安装配置过程中，选择locale一项时，设置本地化语言，一般选择 C，而不要使用默认的Default Locale。否则试过几次都会失败。
> 最新的postgre12，加上最新的windows，直接Default_locale可以，反倒是C不行。

（2）、创建数据库

主要脚本：

```postgresql
CREATE USER herodotus WITH PASSWORD 'herodotus';
CREATE DATABASE herodotus OWNER herodotus;
GRANT ALL PRIVILEGES ON DATABASE herodotus TO herodotus;
```

（3）、导入建表数据

先导入Oauth2相关表即可，其它的表可以通过JPA自动创建。文件位置： ${project home}/eurynome-cloud/eurynome-cloud-platform/eurynome-cloud-platform-uaa/src/main/resources/sql/oauth2-schema-postgresql.sql

（4）、开放IP访问

如果想要使用Docker封装的服务，就需要开放PostgreSQL的IP访问。Windows环境，链接PostgreSQL是主机名填写“localhost”可以正常链接，使用IP地址不能进行链接

在${PostgreSQL_HOME}/12/data目录下，找到postgresql.conf和pg_hba.conf。

- 打开postgresql.conf，在59行，找到listen_addresses，修改为：

```postgresql
listen_addresses = '*'
```

- 修改端口号打开postgresql.conf，在63行，找到port

> PostgreSQL 12 中，该项默认就为 ‘*’，所以可以不用修改

- 打开pg_hba.conf，在最后增加：
```postgresql
host    all             all             192.168.0.0/16          trust
```

> 允许访问PostgreSQL服务器的客户端IP地址, 其中：192.168.0.0/16表示允许192.168.0.1-192.168.255.255网段访问。可根据实际情况调整

- 重启数据服务

### 3、Redis安装

（1）、安装Redis

正常安装即可

（2）、开放IP访问

在redis安装目录下，找到redis.windows-service.conf

- 将 56行 bind 127.0.0.1 注释掉，修改为

```
#bind 127.0.0.1
```

- 将 75 行 protected-mode参数改为no

```
protected-mode no
```

> 生产环境不建议这样，还是要指定具体IP安全一些

- 重启服务

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

如果是采用WSL2 的方式运行容器，那么在Linux中输入以下命令

sudo sysctl vm.max_map_count=524288

另一种解决办法：
- open powershell
- wsl -d docker-desktop
- echo "vm.max_map_count = 262144" > /etc/sysctl.d/99-docker-desktop.conf

@See : https://github.com/docker/for-win/issues/5202

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

[官网说明]: https://nacos.io/zh-cn/docs/feature-list.html）

[]: https://nacos.io/zh-cn/docs/feature-list.html

[下载地址]: https://github.com/alibaba/nacos/releases

[]: https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E