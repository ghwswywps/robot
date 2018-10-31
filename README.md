## 钉钉robot机器人
### 需要环境
- JDK 8以上版本  
  前往官方下载:[jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- maven 3以上版本  
  前往官方下载:[maven](http://maven.apache.org/download.cgi)

### 部署方式
1. fork项目，并将start.sh中项目地址改为自己的链接
2. 授权start.sh执行权限  
```
~ cd robot & chmod 755 start.sh
```
3. 执行脚本
4. 登陆h2网页管理,默认地址:host:10924/h2,将TEMPLE表TEMPLE字段修改为你期望的大小限制,具体执行sql请自行编写。

### 更新
alpha   