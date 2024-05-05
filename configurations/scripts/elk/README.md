# 说明

该文件是 Logstash 的输入输出配置文件。主要是用于指定日志收集TCP端口与Elasticsearch的连接。

> 请参考 `env.base.yml` 中，ELK 的 volumns 配置，将该文件放入到 conf.d 目录下，再运行 ELK