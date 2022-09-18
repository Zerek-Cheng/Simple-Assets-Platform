# Simple Assets Platform / 静态资源托管系统/图床系统

---

### **更加灵活的图片、静态资源存放浏览系统**

#### **More flexible image and static resource storage and browsing system**

**Template Demo:**
**[https://zerek-cheng.github.io/Simple-Assets-Platform/](https://zerek-cheng.github.io/Simple-Assets-Platform/)**

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
1. docker run -d -p 8080:80 -v ./sap-backend/application-dev.yml:/app/config/application-dev.yml zerek00/simple_assets_platform
  + 配置application-dev.yml(填写完你的redis、mysql、文件保存位置等信息)
2. docker run -d -p 8081:8081 zerek00/simple_assets_platform_backend
3. 配置Web服务器的proxy_pass选项
  + /api/ -> 8181端口
  + / -> 8080端口
4. 访问 http://localhost/
5. Enjoy it!
```

### application-dev.yml Example:

```yaml
casdoor
endpoint: https://your_cassdoor_address************************************
client-id: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
client-secret: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
organization-name: dev-org
application-name: SimpleImgsPlatform
certificate: "-----BEGIN CERTIFICATE-----\r\n
************************************************************************\r\n 
#alert: every line must end with \r\n
-----END CERTIFICATE-----"
server:
  port: 8081
  servlet:
    session:
      cookie:
        name: PHPSESSIONID
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      url: jdbc:mysql://xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx/spring?useUnicode=true&characterEncoding=utf-8&useSSL=false&useAffectedRows=true&tinyInt1isBit=true
      username: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
      password: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
  session:
    store-type: redis
  redis:
    host: redis
    port: 6379
    database: 0
    password: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    jedis:
      pool:
        enabled: true
    client-type: jedis
  servlet:
    multipart:
      location: /tmp/xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
logging:
  level:
    cn.bukkit.sip.*: debug
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

## 项目 Star 趋势 / Project Star Trend

![](https://starchart.cc/Zerek-Cheng/Simple-Assets-Platform.svg)

## 作者状态 / Author Status

[![My Github Stats](https://github-readme-stats.vercel.app/api?username=Zerek-Cheng&show_icons=true&theme=radical)](https://github.com/Zerek-Cheng)