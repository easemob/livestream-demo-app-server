以下 API 中涉及到的 token 均为环信用户 token。

## 用户登录

**API说明:** 用户登录。

**Path:** `http://loalhost:8083/internal/appserver/liverooms/user/login`

**HTTP Method:** `POST`

**Permission:** App User

**Request Parameters:** 无

**Request Headers:**

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:**

```json
{
  "username": "us1",
  "nickname": "tom",
  "icon_key": "ChatroomDemoAvatar1~20"
}
```

**Request Body参数说明:**

| 参数       | 类型 | 说明                     |
|----------| --- |------------------------|
| username | String | 用户id，此属性为必选项           |
| nickname | String | 用户昵称用户客户端展示昵称，此属性为可选项  |
| icon_key | String| 头像key用于客户端展示头像，此属性为可选项 |

**请求示例:**

```
curl -X POST -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' 'http://localhost:8083/internal/appserver/liverooms/user/login'
-d {
    "username" : "us1",
    "nickname" : "tom",
    "icon_key" : "ChatroomDemoAvatar1~20"
}
```

**返回示例:**

```json

{
  "userName": "us1",
  "icon_key": "ChatroomDemoAvatar1",
  "access_token": "YWMtx4nvCnihEe6zQdeazoXSK2tY0F2ZwEjHokQf8-laPcCjEzwgeI4R7qSLA1R1lXdXAwMAAAGLim_sjwABUYCWQGDKHOq1dE6Ru6CvrNorwa_SxFq2wUdi7IXeFGDanA",
  "expires_in": 86400
}
```

## 创建直播间

**API说明:** 创建一个直播间，如果开启定时清理直播间开关，该直播间被清理。。

**Path:** `http://localhost:8083/internal/appserver/liverooms`

**HTTP Method:** `POST`

**Permission:** App User

**Request Parameters:** 无

**Request Headers:**

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:**

```json
{
  "name": "live room name",
  "owner": "us1"
}
```

**Request Body参数说明:**

| 参数 | 类型 | 说明  |
| --- | --- | --- |
| name        | String | 直播间名称，此属性为必选项 |
| owner       | String | 创建直播间的用户id，此属性为必须的 |

**请求示例:**

```
curl -X POST -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' 'http://localhost:8083/internal/appserver/liverooms'
-d {
    "name" : "live room name",
    "owner" : "us1"
}
```

**返回示例:**
说明：id为聊天室id，iconKey为创建直播间的用户的iconKey，取自己需要的返回参数，不需要的可以忽略。
```json
{
  "id": "230091694080001",
  "name": "liveroom2",
  "iconKey": "123",
  "description": "nothing left here",
  "nickname": "tom",
  "owner": "usss1",
  "created": 1698830939082,
  "mute": false,
  "persistent": false,
  "status": "offline",
  "showid": 0,
  "video_type": "live",
  "maxusers": 1000,
  "affiliations_count": 0,
  "affiliations": []
}
```

## 创建宣传直播间

**API说明:** 创建一个宣传直播间，该直播间用于在直播间列表置顶宣传产品使用，如果开启定时清理直播间开关，该直播间不会被清理。
该直播间的创建者为配置文件中的 easemob.live.promotion.room.owner=xxx，直播间的名称为配置文件中的 easemob.live.promotion.room.name=xxx。

**Path:** `http://localhost:8083/internal/appserver/promotion/liverooms`

**HTTP Method:** `POST`

**Permission:** App User

**Request Parameters:** 无

**Request Headers:**

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:**

```json
{
  "icon_key": "ChatroomDemoAvatar1~30"
}
```

**Request Body参数说明:**

| 参数 | 类型 | 说明  |
| --- | --- | --- |
| icon_key | String| 头像key用于客户端展示头像，此属性为可选项 |

**请求示例:**

```
curl -X POST -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' 'http://localhost:8083/internal/appserver/promotion/liverooms'
-d {
    "icon_key": "ChatroomDemoAvatar1~30"
}
```

**返回示例:**
说明：id为聊天室id，iconKey为创建直播间的用户的iconKey，取自己需要的返回参数，不需要的可以忽略。
```json
{
  "id": "230091694080002",
  "name": "liveroom3",
  "iconKey": "ChatroomDemoAvatar1~30",
  "description": "nothing left here",
  "nickname": "tom",
  "owner": "usss1",
  "created": 1698830939082,
  "mute": false,
  "persistent": false,
  "status": "ongoing",
  "showid": 0,
  "video_type": "promotion_live",
  "maxusers": 1000,
  "affiliations_count": 0,
  "affiliations": []
}
```

## 获取直播间列表

**API说明:** 获取直播间列表。

**Path:** `http://localhost:8083/internal/appserver/liverooms`

**HTTP Method:** `GET`

**Permission:** App User

**Request Headers:**

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:** 无

**Request Body参数说明:** 无

**请求示例:**

```
curl -X GET -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' 'http://localhost:8083/internal/appserver/liverooms'
```

**返回示例:**
1.列表中返回的第一个直播间为置顶直播间, video_type 为 promotion_live

2.response中id为聊天室id，iconKey为创建直播间的用户的iconKey，取自己需要的返回参数，不需要的可以忽略。
```json
{
  "entities": [
    {
      "id": "230087947517955",
      "name": "liveroom",
      "iconKey": "ChatroomDemoAvatar1~20",
      "description": "nothing left here",
      "nickname" : "tom",
      "owner": "us1",
      "created": 1698827366812,
      "persistent": true,
      "status": "ongoing",
      "showid": 0,
      "ext": {
        "videoUrl": "http://localhost:8080/icon"
      },
      "video_type": "promotion_live",
      "affiliations_count": 0
    },
    {
      "id": "230096444129292",
      "name": "liveroom2",
      "iconKey": "123",
      "description": "nothing left here",
      "nickname" : "tom",
      "owner": "usss1",
      "created": 1698835469820,
      "persistent": false,
      "status": "offline",
      "showid": 0,
      "video_type": "live",
      "affiliations_count": 0
    }
  ],
  "count": 2,
  "cursor": "107777290731521"
}
```

## 删除直播间

**API说明:** 删除直播间。

**Path:** `http://localhost:8083/internal/appserver/liverooms/{liveroomId}`

**HTTP Method:** `DELETE`

**Permission:** App User (LIveRoom Owner)

**Request Parameters:** 无

**Request Headers:**

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:** 无

**Request Body参数说明:** 无

**请求示例:**

```
curl -X DELETE -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' 'http://localhost:8083/internal/appserver/liverooms/230518943711235'
```

**返回示例:**

```json
{
  "id": "230518943711235",
  "name": "test-liveroom",
  "iconKey": "ChatroomDemoAvatar1~20",
  "description": "nothing left here",
  "owner": "user2",
  "created": 1699238396242,
  "persistent": false,
  "status": "offline",
  "showid": 0,
  "video_type": "live",
  "affiliations_count": 0
}
```
