# 来源说明

## 1. nacos-logback.xml

`nacos-logback.xml` 是从 Nacos Github 官方仓库中下载的发布包 `nacos-server-3.2.0.zip` 中获取

> 自 Nacos 3.2.0 开始，Nacos 内置很多插件，使用 Nacos Docker 镜像要开启插件，就需要将 Nacos 配置文件目录 `conf` 挂载为 `volumns`。 `conf` 的本地 `volumns` 目录下，必须要包含 `nacos-logback.xml` 文件，否则镜像无法启动

## 2. application.properties

`application.properties` 是从 Nacos Docker 官方仓库源代码中取得。

> 之所以采用 Nacos Docker 中的 `application.properties`，是因为其中包含了很多环境变量，便于对照修改。也可以使用 Nacos Github 官方仓库中下载的发布包 `nacos-server-3.2.0.zip` 中的 `application.properties`，但是需要注意别修改错误。


## 3. 默认用户

3.2.0 目前无法自动创建用户，可以使用以下脚本自己创建，用户名和密码为 ：nacos / nacos

INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', TRUE);

# 注意事项

1. 如果使用 Docker 方式部署 Nacos，如果将 Nacos conf 目录映射为本地 Volumne，那么需要手动将 `nacos-logback.xml` 和 `application.properties` 放置到指定的映射目录，否则将会 Nacos Docker 将会无法启动
2. 如果要更新 `application.properties`, 可以从 Nacos Docker 源代码中直接下载 `application.properties` 注意需要添加 Postgresql 相关配置