# Livestream Demo App Server
![logo](./docs/img/214x70.png)


## 简介

该服务为 环信直播间 Demo 提供后端服务，可作为 App 使用环信 SDK 实现直播间的服务器端实现示例。

```
1、每一个直播间，都唯一对应一个聊天室， 直播间复用了对应聊天室的所有资源，包括聊天室详情及成员列表等；
2、新建一个直播间会同时新建一个聊天室；新建一个直播间时；
3、删除直播间后，聊天室成员会被移出聊天室，直播间所有信息会被删除，用户不可再加入该直播间；
4、提供了用户登录功能，如果登录的用户不存在会进行自动注册（同时会在环信服务器上注册环信用户）并返回环信用户 Token 为其在端上登录环信服务器使用，如果用户已存在则直接返回环信用户 Token；
```

- 该服务目前提供的功能有

```
1、用户登录；
2、创建直播间；
3、创建置顶直播间（用于官方宣传使用）；
4、获取直播间列表；
5、删除直播间；
5、定期清理直播间；
6、定期向全部直播间发送全局广播消息（如需使用该功能，需要联系环信商务开通）；
7、清理直播间前，向直播间发送通知消息；
```

## 技术选择

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [lombok](https://projectlombok.org/)


## 主要组件

* MySQL


## 数据库使用说明

* 使用MySQL存储直播间信息
* 建表SQL见 [建表SQL](./docs/create_tables.sql)


## 直播间API文档

[直播间Demo服务端API文档](./docs/直播间API文档.md)


## 使用

- 若初次使用环信，需前往 [环信IM开发者注册页](https://console.easemob.com/user/register) 注册成为环信IM开发者；

- 注册成为环信IM开发者后，登录[环信IM管理后台](https://console.easemob.com/user/login) 创建一个应用(App)，之后在App详情页可获得AppKey以及AppKey的clientId和clientSecret；

    - 管理后台的使用可参考文档：[环信管理后台使用指南](https://docs-im-beta.easemob.com/document/server-side/enable_and_configure_IM.html)

- 成为环信IM开发者并成功注册App后，可在自己的服务器部署服务

    - 服务配置文件参考：[application.properties](./live-room-service/src/main/resources/application.properties)
    
    - AppKey组成规则：${orgName}#${appName}，拿到AppKey后可得到对应的orgName和appName；
    
    - 使用baseUrl [前往Console的数据中心查看](https://docs-im-beta.easemob.com/document/server-side/enable_and_configure_IM.html#%E8%8E%B7%E5%8F%96%E7%8E%AF%E4%BF%A1%E5%8D%B3%E6%97%B6%E9%80%9A%E8%AE%AF-im-%E7%9A%84%E4%BF%A1%E6%81%AF)、orgName、appName、clientId、clientSecret修改配置文件，如下：
    ```
        easemob.live.rest.baseUrl=xxx
        easemob.live.rest.appkey.orgName=xxx
        easemob.live.rest.appkey.appName=xxx
        easemob.live.rest.appkey.clientId=xxx
        easemob.live.rest.appkey.clientSecret=xxx
    ```
    
    - 安装MySQL，并根据[建表SQL](./docs/create_tables.sql)创建数据库及表，设置服务配置文件：
    ```
        spring.datasource.driver-class-name=com.mysql.jdbc.Driver
        spring.datasource.url=jdbc:mysql://127.0.0.1:3306/app_server?useSSL=false&useUnicode=true&characterEncoding=utf8
        spring.datasource.username=root
        spring.datasource.password=123456
        spring.datasource.hikari.maximum-pool-size=50
        spring.datasource.hikari.minimum-idle=20
    ```
    - 其余功能配置，根据自己的需求开启对应开关：
    ```
       ## 用户登录 API 会返回 Token 给客户端 SDK 登录使用，单位：秒
       easemob.live.rest.tokenTtl=86400

       ## 客户端 Demo 上展示的固定宣传直播间配置，name 为直播间名称，videoUrl 为直播间封面图地址，owner 为直播间创建者
       easemob.live.promotion.room.name=liveroom
       easemob.live.promotion.room.video.url=http://localhost:8080/icon
       easemob.live.promotion.room.owner=us1

       ## 定期清理直播间开关
       easemob.clear.liveroom.switch=false
       ## 定期清理直播间时间表达式，多久进行一次清理任务
       easemob.delete.liveroom.scheduler.time=*/50 * * * * ?
       ## 直播间创建后保留的时长，如果开启定期清理直播间开关，那么超过这个时间直播间会被删除，单位：秒
       easemob.liveroom.retention.time.second=3600

       ## 清理直播间前向客户端发送通知开关
       easemob.clear.liveroom.notice.switch=false
       ## 清理直播间前向客户端发送通知时间表达式，多久进行一次通知任务
       easemob.delete.liveroom.notice.scheduler.time=*/1 * * * * ?
       ## 清理直播间前，需要提前多久进行通知，单位：秒
       easemob.liveroom.delete.notice.time.second=10
       ## 清理直播间前向客户端发送通知内容
       easemob.liveroom.send.notice.content=\u8bd5\u7528\u5c06\u5728\u0031\u5206\u949f\u540e\u7ed3\u675f

       ## 向所有直播间发送全局广播开关
       easemob.liveroom.send.broadcast.switch=false
       ## 向所有直播间发送全局广播时间表达式，多久进行一次广播任务
       easemob.liveroom.send.broadcast.scheduler.time=*/50 * * * * ?
       ## 向所有直播间发送全局广播内容，如果打开开关，那么会轮询发送以下内容
       easemob.liveroom.send.broadcast.content.a=\u73af\u4fe1\u5168\u65b0\u4f4e\u95e8\u69db\u5168\u5957\u0049\u004d\u0020\u0055\u0049\u004b\u0069\u0074\uff0c\u5f00\u7bb1\u5373\u7528\uff0c\u0031\u5929\u642d\u5efa\u4e0a\u7ebf
       easemob.liveroom.send.broadcast.content.b=\u73af\u4fe1\u6b63\u5f0f\u53d1\u5e03\u0049\u004d\u51fa\u6d77\u4e00\u7ad9\u5f0f\u89e3\u51b3\u65b9\u6848\uff0c\u89e3\u51b3\u65b9\u6848\u4f19\u4f34\u8986\u76d6\u0052\u0054\u0043\u3001\u5185\u5bb9\u5ba1\u6838\u3001\u91d1\u878d\u652f\u4ed8\u3001\u6295\u653e\u3001\u8bed\u8a00\u7ffb\u8bd1\u3001\u6e38\u620f\u53d1\u884c\u3001\u5e94\u7528\u5206\u53d1\u3001\u6570\u5b57\u4eba\u3001\u0041\u0049\u5927\u6a21\u578b\u7b49\u3002\u4e3a\u4f01\u4e1a\u63d0\u4f9b\u4ece\u4ea7\u54c1\u7814\u53d1\u3001\u672c\u5730\u5316\u8fd0\u8425\u3001\u7528\u6237\u670d\u52a1\u5230\u5546\u4e1a\u53d8\u73b0\u7684\u5168\u65b9\u4f4d\u652f\u6301\u3002
       easemob.liveroom.send.broadcast.content.c=\u73af\u4fe1\u0049\u004d\u0020\u0036\u002e\u0030\u4e0a\u7ebf\u5566\uff0c\u65b0\u5e73\u53f0\u0049\u004d\u0020\u0036\u002e\u0030\u7248\u672c\u5448\u73b0\u51fa\u5168\u5e73\u53f0\u3001\u573a\u666f\u5316\u3001\u667a\u80fd\u5316\u3001\u5168\u7403\u5316\u3001\u66f4\u6613\u7528\u3001\u66f4\u5f3a\u5927\u3001\u66f4\u5b89\u5168\u7b49\u0037\u5927\u7279\u6027\u3002
       easemob.liveroom.send.broadcast.content.d=\u53f7\u5916\uff1a\u73af\u4fe1\u884c\u4e1a\u9996\u5bb6\u901a\u8fc7\u0047\u0044\u0050\u0052\u5b89\u5168\u5408\u89c4\u6807\u51c6
    ```
    
    - 启动服务即可

## 模块说明

#### [liveroom模块](./live-room-service/src/main/java/com/easemob/live/server/liveroom)

- 提供直播间Rest Api服务，包含controller、model、service、exception等。

#### [rest模块](./live-room-service/src/main/java/com/easemob/live/server/rest)

- 直播间服务需调用环信REST接口，该模块提供调用环信REST服务，包含token、user、chatroom API的调用，封装了调用所需的RequestBody、ResponseBody。

## 环信文档

[服务端REST文档](https://docs-im-beta.easemob.com/document/server-side/overview.html)
