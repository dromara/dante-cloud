# 一、服务
> 注意点：
> 1. Spring Boot 容器化之后所有的localhost或者127.0.0.1都要改成实际的IP
> 2. 对应的Postgresql要开放允许通过IP访问
> 3. 对应的Redis要对其 bind 的配置进行修改，如果是本机，要在 bind 后面增加 本地的实际ip
> 4. Spring Boot 容器化之后，通过IP访问Kafka会被映射为主机名，所以要修改hosts


- -d 是指定后台运行  
- --name是指定容器名称  
- -p 8080:8080 是指将容器的8080端口映射给宿主机的8080端口 格式为：主机(宿主)端口:容器端口  
```docker
docker run -d --name gateway -p 8847:8847 eurynome.cloud/eurynome-cloud-platform-gateway:1.0.0
docker run -d --name oauth -p 8846:8846 eurynome.cloud/eurynome-cloud-platform-uaa:1.0.0
docker run -d --name upms -p 7070:7070 eurynome.cloud/eurynome-cloud-upms-ability:1.0.0
```
# 二、zookeeper 和 kafka
## 1、zookeeper
- --name    启动容器名称为 zookeeper
- --restart always  docker 重启时，zookeeper容器 会自动重启
- --d       后台线程启动
- --p   左边为容器外宿主机端口右边为容器内部zookeeper启动端口
```docker
docker pull zookeeper
docker run --name zookeeper --restart always -d -p 2181:2181 zookeeper
```
## 2、 kafka
```docker
docker pull wurstmeister/kafka
docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.101.10:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.101.10:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
```
> 链接的地址还有 监听的地址 一定要选宿主机地址，不然会启动失败

# 三、存储服务 minio
```docker
docker run -p 9000:9000 --name minio1 -e "MINIO_ACCESS_KEY=AKIAIOSFODNN7EXAMPLE" -e "MINIO_SECRET_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" -v D:\Development\file-storage\data:/data -v D:\Development\file-storage\minio\config:/root/.minio minio/minio server /data
```

# 四、日志服务 ELK

> for example I use the path: /c/Users/nick/elastic/stack-docker
> Note: you're paths must be in the form of /c/path/to/place using C:\path\to\place will not work

```docker
dcoker pull sebp/elk
docker run -p 5601:5601 -p 9200:9200 -p 9300:9300 -p 5044:5044 -e ES_MIN_MEM=128m  -e ES_MAX_MEM=1024m -v D:\Development\docker-files:/data -it --name elk sebp/elk
```
> sebp/elk经常出现问题
> 以下采用独立安装

```docker
docker cp elasticsearch:/usr/share/elasticsearch/config/ D:/Development/docker-volumes/elasticsearch/config/

docker cp logstash:/usr/share/logstash/config D:/Development/docker-volumes/logstash
docker cp logstash:/usr/share/logstash/pipeline D:/Development/docker-volumes/logstash
docker cp logstash:/usr/share/logstash/logstash-core/lib/jars D:/Development/docker-volumes/logstash

```
 
# 五、Nacos
```docker
docker pull nacos/nacos-server
docker run --env MODE=standalone --name nacos-server -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=192.168.101.10 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_DB_NAME=luban-cloud-platform -e MYSQL_SERVICE_USER=itcraftsman -e MYSQL_SERVICE_PASSWORD=itcraftsman -d -p 8848:8848 nacos/nacos-server
```