<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [钉钉robot机器人](#%E9%92%89%E9%92%89robot%E6%9C%BA%E5%99%A8%E4%BA%BA)
  - [需要环境](#%E9%9C%80%E8%A6%81%E7%8E%AF%E5%A2%83)
  - [部署方式](#%E9%83%A8%E7%BD%B2%E6%96%B9%E5%BC%8F)
  - [添加机器人方式](#%E6%B7%BB%E5%8A%A0%E6%9C%BA%E5%99%A8%E4%BA%BA%E6%96%B9%E5%BC%8F)
    - [1.在客户端钉钉群中点击群设置，点击群机器人](#1%E5%9C%A8%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%92%89%E9%92%89%E7%BE%A4%E4%B8%AD%E7%82%B9%E5%87%BB%E7%BE%A4%E8%AE%BE%E7%BD%AE%E7%82%B9%E5%87%BB%E7%BE%A4%E6%9C%BA%E5%99%A8%E4%BA%BA)
    - [2.点击添加机器人配置](#2%E7%82%B9%E5%87%BB%E6%B7%BB%E5%8A%A0%E6%9C%BA%E5%99%A8%E4%BA%BA%E9%85%8D%E7%BD%AE)
    - [3.点击添加自定义机器人](#3%E7%82%B9%E5%87%BB%E6%B7%BB%E5%8A%A0%E8%87%AA%E5%AE%9A%E4%B9%89%E6%9C%BA%E5%99%A8%E4%BA%BA)
    - [4.勾选outgoing，写入post地址（如http://xxx.com:10900）](#4%E5%8B%BE%E9%80%89outgoing%E5%86%99%E5%85%A5post%E5%9C%B0%E5%9D%80%E5%A6%82httpxxxcom10900)
  - [基本指令](#%E5%9F%BA%E6%9C%AC%E6%8C%87%E4%BB%A4)
    - [定义](#%E5%AE%9A%E4%B9%89)
      - [示例](#%E7%A4%BA%E4%BE%8B)
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
  - [编写一个目录式文档](#%E7%BC%96%E5%86%99%E4%B8%80%E4%B8%AA%E7%9B%AE%E5%BD%95%E5%BC%8F%E6%96%87%E6%A1%A3)
    - [钉钉自定义协议](#%E9%92%89%E9%92%89%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8D%8F%E8%AE%AE)
    - [目录式推荐模板](#%E7%9B%AE%E5%BD%95%E5%BC%8F%E6%8E%A8%E8%8D%90%E6%A8%A1%E6%9D%BF)
  - [编写一个投票机器人](#%E7%BC%96%E5%86%99%E4%B8%80%E4%B8%AA%E6%8A%95%E7%A5%A8%E6%9C%BA%E5%99%A8%E4%BA%BA)
  - [编写一个分页查询器](#%E7%BC%96%E5%86%99%E4%B8%80%E4%B8%AA%E5%88%86%E9%A1%B5%E6%9F%A5%E8%AF%A2%E5%99%A8)
  - [编写一个加班餐机器人](#%E7%BC%96%E5%86%99%E4%B8%80%E4%B8%AA%E5%8A%A0%E7%8F%AD%E9%A4%90%E6%9C%BA%E5%99%A8%E4%BA%BA)
  - [编写一个成语接龙游戏机器人](#%E7%BC%96%E5%86%99%E4%B8%80%E4%B8%AA%E6%88%90%E8%AF%AD%E6%8E%A5%E9%BE%99%E6%B8%B8%E6%88%8F%E6%9C%BA%E5%99%A8%E4%BA%BA)
  - [更新](#%E6%9B%B4%E6%96%B0)
    - [0.0.1-SNAPSHOT (alpha base)](#001-snapshot-alpha-base)
    - [0.0.2-SNAPSHOT](#002-snapshot)
    - [0.0.3-SNAPSHOT](#003-snapshot)

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
3. 确定lsof已经安装
4. 执行脚本  
```
~ sudo cd robot & chmod 755 start.sh & ./start.sh
```
5. 登陆h2网页管理,默认地址:host:10924/h2,将TEMPLE表TEMPLE字段修改为你期望的大小限制,具体执行sql请自行编写。
6. 已经集成图灵聊天机器人api，如果需要请在application.properties配置robot.tuling.apikey属性，官方链接：[图灵机器人](http://www.turingapi.com/)

## 添加机器人方式
### 1.在客户端钉钉群中点击群设置，点击群机器人
![](http://web.abigant.com:81/download/robot_setting_click.png)
### 2.点击添加机器人配置
![](http://web.abigant.com:81/download/add_robot.png)
### 3.点击添加自定义机器人
![](http://web.abigant.com:81/download/whrbot.png)
### 4.勾选outgoing，写入post地址（如http://xxx.com:10900）
![](http://web.abigant.com:81/download/og.png)


## 基本指令
### 定义
- \[*arg|...] arg代表参数,\*代表参数必要性（\*必要，不带不必要）
- 处理指令转义可以用转义字符，如空格='\\32',或者用全文本标记（""" """）如空格='""" """'
- 调用方法：
<font color=#3333ff >@机器人</font> <font color=#00CC00 >指令</font> <font color=#FFA500 >arg</font>:::<font color=#3333ff >value</font> ... <font color=#3333ff >@someone</font>

### 表达式
- 支持|和&两种运算符，分别代表或和与
- 可用()分组逻辑表示
- 带*字符串代表全匹配命中，不带则代表包含命中  

#### 示例
el=(我&爱&祖国)|\*爱我中华,表示：
全匹配'爱我中华'或者包含'我'，'爱','祖国'时可以命中。

### 权限指令
#### 第一次配置master权限
需要在数据库里power_entity添加,userId为钉钉userId，powerId为权限id。  
或者第一次使用命令与机器人聊天的用户也可以自动成为MASTER。  

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
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >授权</font> <font color=#FFA500 >*[power|...]</font> <font color=#3333ff >@被授权人</font>  

示例:  
![](http://web.abigant.com:81/download/power.png)

#### 模板帮助指令
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >模板帮助</font> <font color=#FFA500 >[]</font>

示例:  
![](http://web.abigant.com:81/download/helpo.png)


#### 指令帮助指令
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >指令帮助</font> <font color=#FFA500 >[]</font>

示例:  
![](http://web.abigant.com:81/download/helpt.png)

#### 机器人指令
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >机器人指令</font> <font color=#FFA500 >[]</font>

示例:  
![](http://web.abigant.com:81/download/roboto.png)

#### 增加TEXT模板 （简单模板）
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加TEXT模板</font> <font color=#FFA500 >[\*temple | \*el]</font>

示例:  
![](http://web.abigant.com:81/download/text.png)

#### 增加MARKDOWN模板 （高级模板）
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加MARKDOWN模板</font> <font color=#FFA500 >[\*temple | \*el | \*title]</font>

示例:  
![](http://web.abigant.com:81/download/md.png)

#### 增加LINK模板 （高级模板）
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加LINK模板</font> <font color=#FFA500 >[\*temple | \*el | \*title | \*messageUrl | \*picUrl]</font>


#### 增加SQL (内置的建议sql备忘录)
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >增加SQL</font> <font color=#FFA500 >[\*sql | \*title]</font>


#### 模板列表
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >模板列表</font> <font color=#FFA500 >[id | ids]</font>

![](http://web.abigant.com:81/download/list.png)

#### 删除模板
- 指令：<font color=#3333ff >@机器人</font> <font color=#00CC00 >删除模板</font> <font color=#FFA500 >[\*id]</font>

## 编写一个目录式文档
### 钉钉自定义协议
editing
### 目录式推荐模板
editing
## 编写一个投票机器人
editing
## 编写一个分页查询器
editing
## 编写一个加班餐机器人
editing
## 编写一个成语接龙游戏机器人
editing
## 使用sql备忘录
editing

## 更新
### 0.0.1-SNAPSHOT (alpha base)
build

### 0.0.2-SNAPSHOT 
update doc

### 0.0.3-SNAPSHOT 
一大波优化
