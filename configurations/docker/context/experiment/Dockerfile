# Docker image for herodotus-cloud application
# VERSION 1.0.0
# Author: herodotus

### 基础镜像，使用alpine操作系统，openjkd使用
FROM apache/skywalking-java-agent:8.9.0-jdk8

# 作者
MAINTAINER herodotus <pointer_v@qq.com>

# 外部传递参数
ARG VERSION
ARG SERVICE_NAME
ARG SERVICE_PORT
ENV VERSION ${VERSION}
ENV SERVICE_NAME ${SERVICE_NAME}
ENV SERVICE_PORT ${SERVICE_PORT}

# 系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
# 运行参数
ENV JAVA_OPTS=""
ENV SW_AGENT_SERVICE_NAME=""
ENV SW_COLLECTOR_BACKEND_SERVICE=""

# 声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

# 修改源
RUN echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories
#增加字体，解决验证码没有字体报空指针问题
RUN set -xe && apk --no-cache add ttf-dejavu fontconfig
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

# 应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD ./target/${SERVICE_NAME}-${VERSION}.jar /usr/local/app.jar

# 声明运行时端口
EXPOSE ${SERVICE_PORT}

# 启动容器时的进程
ENTRYPOINT java -jar /usr/local/app.jar