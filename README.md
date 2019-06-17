<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- **Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)* -->
## **Table of Contents**
- [钉钉robot机器人](#%E9%92%89%E9%92%89robot%E6%9C%BA%E5%99%A8%E4%BA%BA)
  - [需要环境](#%E9%9C%80%E8%A6%81%E7%8E%AF%E5%A2%83)
  - [部署方式](#%E9%83%A8%E7%BD%B2%E6%96%B9%E5%BC%8F)
  - [添加机器人方式](#%E6%B7%BB%E5%8A%A0%E6%9C%BA%E5%99%A8%E4%BA%BA%E6%96%B9%E5%BC%8F)
  - [基本指令](#%E5%9F%BA%E6%9C%AC%E6%8C%87%E4%BB%A4)
    - [定义](#%E5%AE%9A%E4%B9%89)
    - [权限指令](#%E6%9D%83%E9%99%90%E6%8C%87%E4%BB%A4)
      - [第一次配置master权限](#%E7%AC%AC%E4%B8%80%E6%AC%A1%E9%85%8D%E7%BD%AEmaster%E6%9D%83%E9%99%90)
      - [授权指令](#%E6%8E%88%E6%9D%83%E6%8C%87%E4%BB%A4)
      - [模板帮助指令](#%E6%A8%A1%E6%9D%BF%E5%B8%AE%E5%8A%A9%E6%8C%87%E4%BB%A4)
      - [指令帮助指令](#%E6%8C%87%E4%BB%A4%E5%B8%AE%E5%8A%A9%E6%8C%87%E4%BB%A4)
      - [机器人指令](#%E6%9C%BA%E5%99%A8%E4%BA%BA%E6%8C%87%E4%BB%A4)
      - [增加TEXT模板 （简单模板）](#%E5%A2%9E%E5%8A%A0text%E6%A8%A1%E6%9D%BF-%E7%AE%80%E5%8D%95%E6%A8%A1%E6%9D%BF)
      - [增加MARKDOWN模板 （高级模板）](#%E5%A2%9E%E5%8A%A0markdown%E6%A8%A1%E6%9D%BF-%E9%AB%98%E7%BA%A7%E6%A8%A1%E6%9D%BF)
      - [增加LINK模板 （高级模板）](#%E5%A2%9E%E5%8A%A0link%E6%A8%A1%E6%9D%BF-%E9%AB%98%E7%BA%A7%E6%A8%A1%E6%9D%BF)
      - [增加SQL (内置的建议sql备忘录)](#%E5%A2%9E%E5%8A%A0sql-%E5%86%85%E7%BD%AE%E7%9A%84%E5%BB%BA%E8%AE%AEsql%E5%A4%87%E5%BF%98%E5%BD%95)
      - [模板列表](#%E6%A8%A1%E6%9D%BF%E5%88%97%E8%A1%A8)
      - [删除模板](#%E5%88%A0%E9%99%A4%E6%A8%A1%E6%9D%BF)
  - [更新](#%E6%9B%B4%E6%96%B0)
    - [0.0.1-SNAPSHOT (alpha base)](#001-snapshot-alpha-base)
    - [0.0.2-SNAPSHOT](#002-snapshot)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

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

## 添加机器人方式
TODO

## 基本指令
### 定义
- \[*arg|...] arg代表参数,\*代表参数必要性（\*必要，不带不必要）
- 处理指令转义可以用转义字符，如空格='\\32',或者用全文本标记（""" """）如空格='""" """'
- 调用方法：
<font color=#3333ff >@机器人</font> <font color=#00CC00 >指令</font> <font color=#FFA500 >arg</font>:::<font color=#3333ff >value</font> ... <font color=#3333ff >@someone</font>


### 权限指令
#### 第一次配置master权限
需要在数据库里power_entity添加,userId为钉钉userId，powerId为权限id  

```java
@AllArgsConstructor
@Getter
public static enum Power {
    MASTER(0,"MASTER"),
    ADMIN(1,"ADMIN"),
    USER(2,"USER");
    }
```
示例:  
TODO

#### 授权指令
- 原则，高授低
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >授权</font> <font color=#FFA500 >*[power|...]</font> <font color=#3333ff >@被授权人</font>  

示例:  
TODO

#### 模板帮助指令
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >模板帮助</font> <font color=#FFA500 >[]</font>

示例:  
TODO


#### 指令帮助指令
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >指令帮助</font> <font color=#FFA500 >[]</font>

示例:  
TODO

#### 机器人指令
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >机器人指令</font> <font color=#FFA500 >[]</font>

示例:  
TODO

#### 增加TEXT模板 （简单模板）
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加TEXT模板</font> <font color=#FFA500 >[\*temple | \*el]</font>

示例:  
TODO

#### 增加MARKDOWN模板 （高级模板）
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加MARKDOWN模板</font> <font color=#FFA500 >[\*temple | \*el | \*title]</font>

示例:  
TODO

#### 增加LINK模板 （高级模板）
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加LINK模板</font> <font color=#FFA500 >[\*temple | \*el | \*title | \*messageUrl | \*picUrl]</font>

示例:  
TODO

#### 增加SQL (内置的建议sql备忘录)
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加SQL</font> <font color=#FFA500 >[\*sql | \*title]</font>

示例:  
TODO

#### 模板列表
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >模板列表</font> <font color=#FFA500 >[id | ids]</font>

示例:  
TODO

#### 删除模板
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >删除模板</font> <font color=#FFA500 >[\*id]</font>

示例:  
TODO



## 更新
### 0.0.1-SNAPSHOT (alpha base)
build

### 0.0.2-SNAPSHOT 
update doc
