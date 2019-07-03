# 钉钉robot机器人
轻量级的钉钉文档机器人，支持简单语义分析，部署迅捷，交互方便，组件式编程方便二次开发，集成h2数据库无需自建数据库。实现sql备忘录，图灵chatstearm等组件，如果有疑问可以[联系我](#%E8%81%94%E7%B3%BB%E6%88%91)，如果喜欢的话可以点个⭐。 

## 必须环境
- 需要组织架构支持机器人outgoing模式，如果没有此权限，可以联系你公司的管理员与钉钉小二大柚（钉钉号：w47zhfa）联系添加。
- JDK 8以上版本  （如果是java9及以上请确保中央仓库）  
  前往官方下载:[jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- 如果是JDK 9或以上版本建议使用中心仓库，否则可能某些jar找不到：  
```
HTTP: http://repo1.maven.org/maven2
HTTPS: https://repo1.maven.org/maven2
```
- maven 3以上版本  
  前往官方下载:[maven](http://maven.apache.org/download.cgi)

## 部署方式
1. fork项目，并将start.sh中项目地址改为自己的链接
2. 授权start.sh执行权限  
3. 确定lsof已经安装
4. 执行脚本  
```
~ sudo cd robot & chmod 755 start.sh & ./start.sh
```
5. 登陆h2网页管理,默认地址:host:10924/h2,将TEMPLE表TEMPLE字段修改为你期望的大小限制,具体执行sql请自行编写。
6. 已经集成图灵聊天机器人api，如果需要请在application.properties配置robot.tuling.apikey属性，官方链接：[图灵机器人](http://www.turingapi.com/)

## 使用WIKI
[ChatRobotWiki](https://github.com/ghwswywps/robot/wiki/ChatRobot)

## 更新
### 0.0.1-SNAPSHOT (alpha base)
first build

### 0.0.2-SNAPSHOT 
create doc&wiki

### 0.0.3-SNAPSHOT 
for reconstruction

## 联系我
- 邮箱：[i@abigant.com](mailto://i@abigant.com)  
- 钉钉：[abigant](dingtalk://dingtalkclient/action/sendmsg?dingtalk_id=abigant)
