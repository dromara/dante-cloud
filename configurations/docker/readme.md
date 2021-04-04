# Compose 方式启动说明
## 1、启动顺序
> You can control the order of service startup and shutdown with the depends_on option. Compose always starts and stops containers in dependency order, where dependencies are determined by depends_on, links, volumes_from, and network_mode: "service:...".

> However, for startup Compose does not wait until a container is “ready” (whatever that means for your particular application) - only until it’s running. There’s a good reason for this.

> The problem of waiting for a database (for example) to be ready is really just a subset of a much larger problem of distributed systems. In production, your database could become unavailable or move hosts at any time. Your application needs to be resilient to these types of failures.

原文地址：https://docs.docker.com/compose/startup-order/

在docker-compose的配置文件中，通过配置depends_on, links, volumes_from, 以及 network_mode: "service:...".可以控制服务的启动顺序，但是却不能知道被依赖的服务是否启动完毕，在一个服务必须要依赖另一个服务完成的时候，这样就会有问题

由此，导致如果cloud服务和nacos等放在一个compose.yml中，一并启动。会导致找不到nacos 配置的错误。

## 2、解决办法
解决的办法有以下几种：

1. 足够的容错和重试机制，比如连接数据库，在初次连接不上的时候，服务消费者可以不断重试，直到连接上位置

2. docker-compose拆分，分成两部分部署，将要先启动的服务放在一个docker-compose中，后启动的服务放在两一个docker-compose中，启动两次，两者使用同一个网络。

3. 同步等待，使用wait-for-it.sh或者其他shell脚本将当前服务启动阻塞，直到被依赖的服务加载完毕
wait-for-it的github地址为：[wait-for-it](https://github.com/vishnubob/wait-for-it)

作者：billJiang
链接：https://www.jianshu.com/p/9446f210e327
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## 3、目前办法
目前采用，分开两个yml的方法。先启动环境相关的服务。然后再启动自己服务。
