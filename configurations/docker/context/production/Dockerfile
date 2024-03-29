# Docker image for dante-cloud application
# VERSION 1.0.0
# Author: gengwei.zheng

### 基础镜像，使用debian操作系统，使用 liberica-openjdk
### 本系统涉及图形验证码，有字体需求，而且采用的是非系统字体。
### 在 alpine 下，尝试了包括打包字体库、自己拷贝字体、重新封装JDK，更换读取字体等多种方式。
### 不管是将字体打包进镜像直接读取，还是通过读取jar包中的 resources 目录，可以实现字体文件的读取。但是在 Font.createFont() 过程中，始终出现 sun.awt.FontConfiguration.getVersion NullPointerException 问题。
### 在 debian 下则没有这问题。以目前了解的情况看，猜测 alpine 下，为了缩小体系，缺失的内容应该不只是字体库，还有其它内容。
### 当然也可能是个人能力有限，一直没有找到正确解决的办法。如果你有更好的解决办法，欢迎提 ISSUE 和 PR。

FROM bellsoft/liberica-openjdk-debian:17.0.9-11
#FROM openjdk:17-jdk

# 作者
MAINTAINER 码匠君 <herodotus@aliyun.com>

# 外部传递参数
ARG VERSION
ARG SERVICE_NAME
ARG SERVICE_PORT
ENV VERSION=$VERSION
ENV SERVICE_NAME=$SERVICE_NAME
ENV SERVICE_PORT=$SERVICE_PORT

# 系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
# 运行参数
ENV JAVA_OPTS=""
ENV SW_AGENT_SERVICE_NAME=""
ENV SW_COLLECTOR_BACKEND_SERVICE=""

# 声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

# 应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD ./target/${SERVICE_NAME}-${VERSION}.jar /usr/local/app.jar
COPY ./agent/skywalking-agent-8.15.0.jar /usr/local/sw/agent.jar

# 声明运行时端口
EXPOSE ${SERVICE_PORT}

# 启动容器时的进程
ENTRYPOINT java -javaagent:/usr/local/sw/agent.jar=agent.service_name=${SW_AGENT_SERVICE_NAME},collector.backend_service=${SW_COLLECTOR_BACKEND_SERVICE} ${JAVA_OPTS} -jar -Dfile.encoding=utf-8 /usr/local/app.jar