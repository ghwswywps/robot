# 钉钉robot机器人
## 需要环境
- 需要组织架构支持机器人outgoing模式，如果没有此权限，可以联系你公司的管理员与钉钉小二大柚（钉钉号：w47zhfa）联系添加。
- JDK 8以上版本  
  前往官方下载:[jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- maven 3以上版本  
  前往官方下载:[maven](http://maven.apache.org/download.cgi)

## 部署方式
1. fork项目，并将start.sh中项目地址改为自己的链接
2. 授权start.sh执行权限  
```
~ sudo cd robot & chmod 755 start.sh & ./start.sh
```
3. 执行脚本
4. 登陆h2网页管理,默认地址:host:10924/h2,将TEMPLE表TEMPLE字段修改为你期望的大小限制,具体执行sql请自行编写。

## 基本指令
### 定义
- \[*arg|...] arg代表参数,\*代表参数必要性（\*必要，不带不必要）
- 处理指令转义可以用转义字符，如空格='\\32',或者用全文本标记（""" """）如空格='""" """'
- 调用方法：@机器人 指令 arg:::value ... @someone


### 权限指令
#### 第一次配置master权限需要在数据库里power_entity添加,userId为钉钉userId，powerId为权限id  

```java
@AllArgsConstructor
@Getter
public static enum Power {
    MASTER(0,"MASTER"),
    ADMIN(1,"ADMIN"),
    USER(2,"USER");
    }
```

#### 授权指令
- 原则，高授低
- 指令：@机器人 授权 *[power|...] @被授权人

#### 模板帮助指令
- 指令：@机器人 模板帮助 []


#### 指令帮助指令
- 指令：@机器人 指令帮助 []

#### 机器人指令
- 指令：@机器人 机器人指令 []

#### 增加TEXT模板 （简单模板）
- 指令：@机器人 增加TEXT模板 [\*temple | \*el]

#### 增加MARKDOWN模板 （高级模板）
- 指令：@机器人 增加MARKDOWN模板 [\*temple | \*el | \*title]

#### 增加LINK模板 （高级模板）
- 指令：@机器人 增加LINK模板 [\*temple | \*el | \*title | \*messageUrl | \*picUrl]

#### 增加SQL (内置的建议sql备忘录)
- 指令：@机器人 增加SQL [\*sql | \*title]

#### 模板列表
- 指令：@机器人 模板列表 [id | ids]

#### 删除模板
- 指令：@机器人 删除模板 [\*id]



## 更新
### 0.0.2-SNAPSHOT (alpha base)
build

### 0.0.2-SNAPSHOT 
update doc
