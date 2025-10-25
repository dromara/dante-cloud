# Docker image for dante-cloud application
# VERSION 1.0.0
# Author: herodotus

### 基础镜像，使用debian操作系统，使用 liberica-openjdk
### 本系统涉及图形验证码，有字体需求，而且采用的是非系统字体。
### 在 alpine 下，尝试了包括打包字体库、自己拷贝字体、重新封装JDK，更换读取字体等多种方式。
### 不管是将字体打包进镜像直接读取，还是通过读取jar包中的 resources 目录，可以实现字体文件的读取。但是在 Font.createFont() 过程中，始终出现 sun.awt.FontConfiguration.getVersion NullPointerException 问题。
### 在 debian 下则没有这问题。以目前了解的情况看，猜测 alpine 下，为了缩小体系，缺失的内容应该不只是字体库，还有其它内容。
### 当然也可能是个人能力有限，一直没有找到正确解决的办法。如果你有更好的解决办法，欢迎提 ISSUE 和 PR。

FROM quay.io/herodotus-cloud/liberica-openjre-debian:17.0.17-11-cds

# 作者
LABEL maintainer="herodotus@aliyun.com"

#工作目录
WORKDIR /usr/local/

# 声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME ["/usr/local/logs"]

#定义参数（build构建时使用）
ARG VERSION
ARG SERVICE_NAME
ARG SERVICE_PORT

# 系统编码
#设置环境变量（程序运行时使用）
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
ENV JAVA_OPTS=""
ENV PARAMS=""
ENV VERSION=$VERSION
ENV SERVICE_NAME=$SERVICE_NAME
ENV SERVICE_PORT=$SERVICE_PORT

#声明了容器应该打开的端口并没有实际上将它打开
EXPOSE ${SERVICE_PORT}

# 应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD ./target/${SERVICE_NAME}-${VERSION}.jar app.jar

# 康检查参数设置，每5秒检查一次，接口超时时间2秒，连续10次返回1就判定该容器不健康
HEALTHCHECK --interval=5s \
            --timeout=3s \
            --retries=10 \
            CMD curl -fs http://localhost:${SERVICE_PORT}/actuator/health || exit 1

# 启动容器时的进程
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.awt.headless=true -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -jar app.jar $PARAMS"]