# 静态资源托管系统/图床系统 / Simple Assets Platform

#### **更加灵活的图片、静态资源存放浏览系统** / **More flexible image and static resource storage and browsing system**

---  
**Template Demo:**
**[https://zerek-cheng.github.io/Simple-Assets-Platform/](https://zerek-cheng.github.io/Simple-Assets-Platform/)**  
***友情提示: 上为Github Pages，未架设后端程序，故只可预览界面***  
***Tips: On Github Pages, no backend program is set up, so only the interface can be previewed***

**Backend :**
[![](https://github.com/Zerek-Cheng/Simple-Assets-Platform/actions/workflows/docker.yml/badge.svg?branch=master)](https://hub.docker.com/repository/docker/zerek00/simple-assets-platform)
[![Docker Image Version (latest semver)](https://img.shields.io/docker/v/zerek00/simple-assets-platform)](https://hub.docker.com/repository/docker/zerek00/simple-assets-platform)

**Template:**
[![](https://github.com/Zerek-Cheng/Simple-Assets-Platform/actions/workflows/docker.yml/badge.svg?branch=master-template)](https://hub.docker.com/repository/docker/zerek00/simple-assets-platform-template)
[![Docker Image Version (latest semver)](https://img.shields.io/docker/v/zerek00/simple-assets-platform-template)](https://hub.docker.com/repository/docker/zerek00/simple-assets-platform-template)

![](https://imgEntity.shields.io/github/languages/code-size/Zerek-Cheng/Simple-Assets-Platform?style=for-the-badge)
![](https://imgEntity.shields.io/github/stars/Zerek-Cheng/Simple-Assets-Platform?style=for-the-badge)
![](https://imgEntity.shields.io/github/license/Zerek-Cheng/Simple-Assets-Platform?style=for-the-badge)

![](https://github.com/Zerek-Cheng/Simple-Assets-Platform/raw/master/show.jpg)

### 安装过程 / Install Steps：

**Pre-Install: Docker, Docker-Compose, Mysql, Redis, Web Server, [Casdoor](https://github.com/casdoor/casdoor)**

```
1. docker run -d -p 8080:80 -v ./sap-backend/application.yml:/app/config/application.yml zerek00/simple-assets-platform-template
  + 配置application-dev.yml(填写完你的redis、mysql、文件保存位置等信息)
2. docker run -d -p 8081:8081 zerek00/simple-assets-platform
3. 配置Web服务器的proxy_pass选项
  + /api/ -> 8181端口
  + / -> 8080端口
4. 访问 http://localhost/
5. Enjoy it!
```

> **友情提示 / Tips:**  
> docker中的env同样生效，且优先级最高！ 例子: server.port=8888
> The env in docker also takes effect and has the highest priority! Example: server.port=8888

### application.yml Example:

```yaml
spring:
  jackson:
    time-zone: GMT+8
    date-format: yyyy-MM-dd HH:mm:ss
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      url: jdbc:mysql://192.168.xxx.5:3306/spring?useUnicode=true&characterEncoding=utf-8&useSSL=false&useAffectedRows=true&serverTimezone=GMT%2B8
      username: xxxxxx
      password: xxxxxx
  session:
    store-type: redis
  redis:
    host: 192.168.xxxxx.5
    port: 6379
    database: 0
    password: xxxxxxxxxx
    jedis:
      pool:
        enabled: true
    client-type: jedis
  servlet:
    multipart:
      location: D:\\dev\\tmp\\

web:
  url: http://192.168.100.114:8080
  casdoor-callback: ${web.url}/api/login/callback
  upload:
    allow-upload:
      - jpg
      - jpeg
      - png
      - gif
      - bmp
      - webp
      - svg
      - tiff

casdoor:
  endpoint: https://oauth.xxxxxxxxxx.cn
  client-id: 54aba1exxxxxxxxxxxxx
  client-secret: 58a93442745950554ea2a980dxxxxxxxxxxxx
  organization-name: dev-org
  application-name: SimpleImgsPlatform
  certificate: "-----BEGIN CERTIFICATE-----\r\n
MIIE2TCCAsGgAwIBAgIDAeJAMA0GCSqGSIb3DQEBCwUAMCYxDjAMBgNVBAoTBWFk\r\n
-----END CERTIFICATE-----"
server:
  port: 8081
  servlet:
    session:
      cookie:
        name: PHPSESSIONID
mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: cn.bukkit.sip.orm.entity
  configuration:
    cache-enabled: true
  type-handlers-package: cn.bukkit.sip.orm.handler
```

### Nginx conf Example

```yaml
server {
  listen 80;
  #listen 443 ssl http2;
  server_name localhost;
  #ssl_certificate "/home/certs/localhost.pem";
  #ssl_certificate_key "/home/certs/localhost.key";
  #ssl_session_cache shared:SSL:1m;
  #ssl_session_timeout  10m;
  #ssl_ciphers PROFILE=SYSTEM;
  #ssl_prefer_server_ciphers on;
  proxy_set_header    Host    $http_host;
  proxy_set_header    X_Real-IP   $remote_addr;
  proxy_set_header    X-Forwarded-For $proxy_add_x_forwarded_for;
  proxy_redirect      off;
  location ~* ^/api/ {
  proxy_pass http://127.0.0.1:8081;
  rewrite ^/api(.*) $1 break;
  }
  location ~* ^/(assets|js|css)/ {
  proxy_pass http://127.0.0.1:8080;
  }
  location ~* "(.*)\.[a-zA-Z]{1,4}$" {
  proxy_pass http://127.0.0.1:8080;
  }
  location / {
  proxy_pass http://127.0.0.1:8080;
  try_files $uri $uri/ /index.html;
  }
}
```

### 当然，你也可以直接使用docker-compose来启动(注意修改下面的端口映射)

```yaml
version: '3.1'

services:
  sap-backend:
    user: root
    container_name: sap-backend
    image: zerek00/simple-assets-platform
    restart: always
    volumes:
      - ./sap-backend/application.yml:/app/config/application.yml
    networks:
      db:
        aliases:
          - sap_backend
    ports:
      - 11181:8081
    depends_on:
      - mysql
      - redis
  sap-template:
    user: root
    container_name: sap-template
    image: zerek00/simple-assets-platform-template
    restart: always
    networks:
      db:
        aliases:
          - sap_template
    ports:
      - 11180:80
networks:
  db:
    name: dbNet
    external: true
```

## 项目 Star 趋势 / Project Star Trend

![](https://starchart.cc/Zerek-Cheng/Simple-Assets-Platform.svg)

## 作者状态 / Author Status

[![My Github Stats](https://github-readme-stats.vercel.app/api?username=Zerek-Cheng&show_icons=true&theme=radical)](https://github.com/Zerek-Cheng)