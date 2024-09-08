## Docker 应用部署

### 一、部署MySQL

1. 搜索mysql镜像

```shell
docker search mysql
```

2. 拉取mysql镜像

```shell
docker pull mysql:5.6
```
```shell
docker pull mysql:8
```

3. 创建容器，设置端口映射、目录映射

```shell
# 在/root目录下创建mysql目录用于存储mysql数据信息
mkdir ~/mysql
cd ~/mysql
```

```shell
docker run -id \
-p 3307:3306 \
--name=c_mysql \
-v $PWD/conf:/etc/mysql/conf.d \
-v $PWD/logs:/logs \
-v $PWD/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
mysql:5.6
```

- 参数说明：
  - **-p 3307:3306**：将容器的 3306 端口映射到宿主机的 3307 端口。
  - **-v $PWD/conf:/etc/mysql/conf.d**：将主机当前目录下的 conf/my.cnf 挂载到容器的 /etc/mysql/my.cnf。配置目录
  - **-v $PWD/logs:/logs**：将主机当前目录下的 logs 目录挂载到容器的 /logs。日志目录
  - **-v $PWD/data:/var/lib/mysql** ：将主机当前目录下的data目录挂载到容器的 /var/lib/mysql 。数据目录
  - **-e MYSQL_ROOT_PASSWORD=123456：**初始化 root 用户的密码。

```shell
docker run -id \
-p 3307:3306 \
--name=c_mysql \
-v $PWD/conf:/etc/mysql/conf.d \
-v $PWD/logs:/logs \
-v $PWD/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
mysql:8
```
```shell
如果想把配置文件映射出来,则需要事先手动创建Mysql的配置文件,/opt/docker/mysql/conf配置文件my.cnf如下所示:
```

```shell
参数说明:
  [mysqld]
#datadir=/usr/local/mysql/data
default_authentication_plugin=mysql_native_password  #使用mysql8以前的密码插件，以便navicat等工具能够正常连接
default-storage-engine=INNODB
character_set_server = utf8
secure_file_priv=/var/lib/mysql
[mysqld_safe]
character_set_server = utf8
[mysql]
default-character-set = utf8
[mysql.server]
default-character-set = utf8
[client]
default-character-set = utf8


用宿主机的配置文件直接映射过去，可能会导致安全问题并不能生效。如果提示的话可以进入容器执行如下命令：

chmod 644 /etc/my.cnf
# 设置编码格式
show variables like 'character_set_%';
set character_set_server=utf8;

```

4. 进入容器，操作mysql

```shell
docker exec –it c_mysql /bin/bash
```

5. 使用外部机器连接容器中的mysql

![1573636765632](.\imgs\1573636765632.png)


















### 二、部署Tomcat

1. 搜索tomcat镜像

```shell
docker search tomcat
```

2. 拉取tomcat镜像

```shell
docker pull tomcat
```

3. 创建容器，设置端口映射、目录映射

```shell
# 在/root目录下创建tomcat目录用于存储tomcat数据信息
mkdir ~/tomcat
cd ~/tomcat
```

```shell
docker run -id --name=c_tomcat \
-p 8080:8080 \
-v $PWD:/usr/local/tomcat/webapps \
tomcat 
```

- 参数说明：
  - **-p 8080:8080：**将容器的8080端口映射到主机的8080端口
  
    **-v $PWD:/usr/local/tomcat/webapps：**将主机中当前目录挂载到容器的webapps



4. 使用外部机器访问tomcat

![1573649804623](./imgs\1573649804623.png)
















### 三、部署Nginx

1. 搜索nginx镜像

```shell
docker search nginx
```

2. 拉取nginx镜像

```shell
docker pull nginx
```

3. 创建容器，设置端口映射、目录映射


```shell
# 在/root目录下创建nginx目录用于存储nginx数据信息
mkdir ~/nginx
cd ~/nginx
mkdir conf
cd conf
# 在~/nginx/conf/下创建nginx.conf文件,粘贴下面内容
vim nginx.conf
```
```shell

user  nginx;
worker_processes  1;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;
}


```




```shell
docker run -id --name=c_nginx \
-p 80:80 \
-v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf \
-v $PWD/logs:/var/log/nginx \
-v $PWD/html:/usr/share/nginx/html \
nginx
```

- 参数说明：
  - **-p 80:80**：将容器的 80端口映射到宿主机的 80 端口。
  - **-v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf**：将主机当前目录下的 /conf/nginx.conf 挂载到容器的 :/etc/nginx/nginx.conf。配置目录
  - **-v $PWD/logs:/var/log/nginx**：将主机当前目录下的 logs 目录挂载到容器的/var/log/nginx。日志目录

4. 使用外部机器访问nginx

![1573652396669](.\imgs\1573652396669.png)











### 四、部署Redis

1. 搜索redis镜像

```shell
docker search redis
```

2. 拉取redis镜像

```shell
docker pull redis:5.0
```

3. 创建容器，设置端口映射

```shell
docker run -id --name=c_redis -p 6379:6379 redis:5.0
```

4. 使用外部机器连接redis

```shell
./redis-cli.exe -h 192.168.149.135 -p 6379
```

### 五、部署ribbitmp
```shell
docker run -d --privileged=true --restart=always --name rabbitmq -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=123456 -v /d/Docker/Rabbitmq/data:/var/lib/rabbitmq  -v /d/Docker/Rabbitmq/log/:/var/log/rabbitmq/log/ rabbitmq:management
```
### 六、Nacos
```shell
docker run -d --privileged=true --restart=always  --name nacos -p 8848:8848 -p 9848:9848 -p 9849:9849 --env MODE=standalone nacos/nacos-server
docker run -d --privileged=true  --name nacos -p 8848:8848 -p 9848:9848 -p 9849:9849 --env MODE=standalone  -v D:\docker\nacos\logs:/home/nacos/logs -v D:\docker\nacos\conf\application.properties:/home/nacos/conf/application.properties nacose:v1

```

### 七、Minio
```shell
docker run  -p 9090:9090  -p 9091:9091 --name minio \
 -d --restart=always \
 -e "MINIO_ROOT_USER=admin" \
 -e "MINIO_ROOT_PASSWORD=admin@123.com" \
 -v /data/oss/jun/data:/data \
 -v /data/oss/jun/config:/root/.minio \
  minio/minio server /data  --console-address ":9090"  --address ":9091"
```

### 八、Gitlab
```shell
sudo docker run --detach --publish 8930:443 --publish 8929:8929 --publish 8928:22 --name gitlab --restart always --volume $GITLAB_HOME/config:/etc/gitlab --volume $GITLAB_HOME/logs:/var/log/gitlab --volume $GITLAB_HOME/data:/var/opt/gitlab --shm-size 256m registry.gitlab.cn/omnibus/gitlab-jh:latest

```
### 九、Nexus
```shell
docker run -d --name nexus3 --restart=always -p 8081:8081 --mount src=nexus-data,target=/nexus-data sonatype/nexus3
```

### 十、Jenkins
```shell
docker run -d -u root -p 8888:8080 -v /opt/docker/jenkins-data:/var/jenkins_home -v /opt/docker.sock:/var/run/docker.sock -v "$HOME":/home --privileged=true --restart=always --name jendemo jenkinsci/blueocean

```
### 十一、Mariadb
```shell
docker run -d -p 3306:3309 --name mariadb -v /opt/mariadb/data/:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root mariadb:latest
mysql -h 127.0.0.1:3309 -u root -p root
```

### 十二、xxl-job
```shell
docker run \ 
-e PARAMS="--spring.datasource.url=jdbc:mysql://172.17.0.3:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai --spring.datasource.username=root --spring.datasource.password=123456" \
-p 28080:8080 \
--name xxl-job-admin \
-d xuxueli/xxl-job-admin:2.3.1
```






