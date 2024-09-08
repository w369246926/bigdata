## Docker Compose

### 一、安装Docker Compose

```shell
# Compose目前已经完全支持Linux、Mac OS和Windows，在我们安装Compose之前，需要先安装Docker。下面我 们以编译好的二进制包方式安装在Linux系统中。 
curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
# 设置文件可执行权限 
chmod +x /usr/local/bin/docker-compose
# 查看版本信息 
docker-compose -version
```

### 二、卸载Docker Compose

```shell
# 二进制包方式安装的，删除二进制文件即可
rm /usr/local/bin/docker-compose
```



### 三、 使用docker compose编排nginx+springboot项目

1. 创建docker-compose目录

```shell
mkdir ~/docker-compose
cd ~/docker-compose
```

2. 编写 docker-compose.yml 文件

```shell
version: '3'
services:
  nginx:
   image: nginx
   ports:
    - 80:80
   links:
    - app
   volumes:
    - ./nginx/conf.d:/etc/nginx/conf.d
  app:
    image: app
    expose:
      - "8080"
```

3. 创建./nginx/conf.d目录

```shell
mkdir -p ./nginx/conf.d
```



4. 在./nginx/conf.d目录下 编写itheima.conf文件

```shell
server {
    listen 80;
    access_log off;

    location / {
        proxy_pass http://app:8080;
    }
   
}
```

5. 在~/docker-compose 目录下 使用docker-compose 启动容器

```shell
docker-compose up
```

6. 测试访问

```shell
http://192.168.149.135/hello
```

7. 常用Docker Compose+Spring Boot项目

```shell
version: "3.8"

services:
    mysql:
        build:
            context: ./db
        container_name: glmx-mysql
        ports: 
            - "6001:3306"
        environment:
            MYSQL_ROOT_HOST: "%"
            MYSQL_ROOT_PASSWORD: 123456
        networks:
            - glnet
        restart: always
        privileged: true

    redis:
        image: glmx-redis
        container_name: glmx-redis
        ports:
            - "6002:6379"
        command: ["redis-server","--requirepass 123456","--appendonly yes"]
        networks:
            - glnet
        restart: always
        privileged: true

    rabbitmq:
        image: glmx-rabbitmq
        container_name: glmx-rabbitmq
        ports:
            - 6003:5672
            - 6004:15672
        networks:
            - glnet
        restart: always
        privileged: true
    

    nacos:
        image: glmx-nacos
        container_name: nacos-nacos
        environment: 
            - MODE=standalone
        ports: 
            - "6005:8848"
        networks:
            - glnet
        restart: always
        privileged: true

    nginx:
        image: glmx-nginx
        container_name: glmx-nginx
        ports:
            - "80:80"
            - "8080:8080"
            - "8081:8081"
            - "6868:6868"
        volumes:
            - "D:/Docker/glmx/nginx/conf/default.conf:/etc/nginx/conf.d/default.conf"
            - "D:/Docker/glmx/nginx/www:/usr/share/nginx/html"
            - "D:/Docker/glmx/nginx/log:/var/log/nginx"
        networks:
            - glnet
        restart: always
        privileged: true

    app:
        image: glmx-app
        container_name: glmx-app
        ports:
            - "24081:24081"
        volumes:
            - "D:/Docker/glmx/app:/root/glmx"
        networks:
            - glnet
        links:
            - mysql
            - redis
            - rabbitmq
            - nacos
        depends_on:
            - mysql
            - redis
            - rabbitmq
            - nacos
            - nginx
        restart: always
        privileged: true

networks:
    glnet:
        driver: bridge

```

