## 创建直播间

**API说明:** 创建一个直播间。

**Path:** `http://localhost:8080/appserver/liverooms`

**HTTP Method:** `POST`

**Permission:** App管理员

**Request Parameters:** 无

**Request Headers:** 

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:**

```json
{
	"name":"test1",
	"description":"server create chatroom",
	"maxusers":300,
	"owner":"hxtest1",
	"cover":"http://177.0.0.1:8080/cover/pictiure"
}
```

**Request Body参数说明:**

| 参数 | 类型 | 说明  |
| --- | --- | --- |
| name        | String | 直播间名称，此属性为必选项 |
| description | String | 直播间描述，此属性为可选项 |
| maxusers    | Integer| 直播间成员最大数（包括直播间创建者），值为数值类型，默认值200，最大值5000，此属性为可选的 |
| owner       | String | 直播间的主播，此属性为必须的 |
| members     | Array  | 直播间成员，此属性为可选的，但是如果加了此项，数组元素至少一个 |
| persistent  | Boolean| 直播间是否持久化，此属性为可选的，默认为true，设为false后，直播间停播后一小时内未有状态更新，会自动清理直播间 |
| video_type  | String | 直播间视频类型，此属性为可选的，默认是live，即直播，可设为vod，即点播类型。可设为agora_speed_live，即声网极速直播。可设为agora_vod，即声网点播。可设为agora_interaction_live，即声网互动直播。可设为agora_cdn_live，即声网cdn直播。 |
| cover       | String | 直播间封面Url | 
| ext         | Map    | 直播间自定义属性 |

**请求示例:**

```
curl -X POST http://localhost:8080/appserver/liverooms -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json' -d '{"name":"test1","description":"server create chatroom","maxusers":300,"owner":"hxtest1","cover":"http://172.0.0.1:8080/cover/pictiure"}'
```

**返回示例:**

```json
{
    "id": "107776865009665",
    "name": "test1",
    "description": "server create chatroom",
    "owner": "hxtest1",
    "created": 1582182428511,
    "mute": false,
    "cover": "http://172.0.0.1:8080/cover/pictiure",
    "persistent": true,
    "video_type": "live",
    "status": "offline",
    "showid": 0,
    "maxusers": 300,
    "affiliations_count": 0,
    "affiliations": []
}
```


## 获取直播间详情

**API说明:** 获取直播间详情。

**Path:** `http://localhost:8080/appserver/liverooms/{liveroomId}`

**HTTP Method:** `GET`

**Permission:** App User

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
curl -X GET http://localhost:8080/appserver/liverooms/107776865009665 -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "id": "107776865009665",
    "name": "test1",
    "description": "server create chatroom",
    "owner": "hxtest1",
    "created": 1582182428511,
    "mute": false,
    "cover": "http://172.0.0.1:8080/cover/pictiure",
    "persistent": true,
    "video_type": "live",
    "status": "offline",
    "showid": 0,
    "maxusers": 300,
    "affiliations_count": 1,
    "affiliations": [
        {
            "member": "hxtest2"
        }
    ]
}
```


## 修改直播间详情

**API说明:** 修改直播间详情。

**Path:** `http://localhost:8080/appserver/liverooms/{liveroomId}`

**HTTP Method:** `PUT`

**Permission:** LiveRoom Owner

**Request Parameters:** 无

**Request Headers:** 

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:**

```json
{
    "cover": "http://172.0.0.1:8080/cover",
    "maxusers": 1000,
    "name": "test1",
    "owner": "hxtest1"
}
```

**Request Body参数说明:**

| 参数 | 类型 | 说明  |
| --- | --- | --- |
| name        | String | 直播间名称 |
| description | String | 直播间描述 |
| maxusers    | Integer| 直播间成员最大数（包括直播间创建者），值为数值类型，默认值200，最大值5000 |
| owner       | String | 直播间的主播 |
| cover       | String | 直播间封面Url |
| persistent  | Boolean| 直播间是否持久化，默认为true，设为false后，直播间停播后一小时内未有状态更新，会自动清理直播间 |
| video_type  | String | 直播间视频类型，默认是live，即直播，可设为vod，即点播类型。可设为agora_speed_live，即声网极速直播。可设为agora_vod，即声网点播。可设为agora_interaction_live，即声网互动直播。可设为agora_cdn_live，即声网cdn直播。  |
| ext         | Map    | 直播间自定义属性 |

**请求示例:**

```
curl -X PUT http://localhost:8080/appserver/liverooms/107776865009665 -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json' -d '{"name":"test1","maxusers":1000,"owner":"hxtest1","cover":"http://172.0.0.1:8080/cover"}'
```

**返回示例:**

```json
{
    "id": "108144876388353",
    "name": "test1",
    "description": "nothing left here",
    "owner": "hxtest1",
    "created": 1582533391279,
    "cover": "http://177.0.0.1:8080/cover",
    "persistent": true,
    "video_type": "live",
    "status": "offline",
    "showid": 0,
    "affiliations_count": 1
}
```


## 分页获取直播间列表

**API说明:** 分页获取直播间列表。

**Path:** `http://localhost:8080/appserver/liverooms`

**HTTP Method:** `GET`

**Permission:** App User

**Request Parameters:** 

| 参数 | 类型 | 说明  |
| --- | --- | --- |
| limit  | int | 默认为10，可选项 |
| cursor | String | 分页返回的游标，可获取下一页数据 |

**Request Headers:** 

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:** 无

**Request Body参数说明:** 无

**请求示例:**

```
curl -X GET http://localhost:8080/appserver/liverooms?limit=2&cursor=107776865009666 -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "entities": [
        {
            "id": "107741231251457",
            "name": "test1",
            "owner": "hxtest1",
            "persistent": true,
            "video_type": "live",
            "status": "ongoing",
            "showid": 1,
            "affiliations_count": 1
        },
        {
            "id": "107776865009665",
            "name": "test1",
            "owner": "hxtest1",
            "cover": "http://177.0.0.1:8080/cover/pictiure",
            "persistent": true,
            "video_type": "live",
            "status": "offline",
            "showid": 0,
            "affiliations_count": 1
        }
    ],
    "count": 2,
    "cursor": "107777290731521"
}
```


## 分页获取正在直播的直播间列表

**API说明:** 分页获取正在直播的直播间列表。

**Path:** `http://localhost:8080/appserver/liverooms/ongoing`

**HTTP Method:** `GET`

**Permission:** App User

**Request Parameters:** 

| 参数 | 类型 | 说明  |
| --- | --- | --- |
| limit  | int | 默认为10，可选项 |
| cursor | String | 分页返回的游标，可获取下一页数据 |

**Request Headers:** 

| 参数 | 说明  |
| --- | --- |
| Content-Type  | application/json |
| Authorization | Bearer ${token} |

**Request Body示例:** 无

**Request Body参数说明:** 无

**请求示例:**

```
curl -X GET http://localhost:8080/appserver/liverooms/ongoing?limit=2&cursor=107776865009666 -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "entities": [
        {
            "id": "107741231251457",
            "name": "test1",
            "owner": "hxtest1",
            "persistent": true,
            "video_type": "live",
            "status": "ongoing",
            "showid": 1,
            "affiliations_count": 1
        },
        {
            "id": "107776865009665",
            "name": "test1",
            "owner": "hxtest1",
            "cover": "http://177.0.0.1:8080/cover/pictiure",
            "persistent": true,
            "video_type": "live",
            "status": "ongoing",
            "showid": 1,
            "affiliations_count": 1
        }
    ],
    "count": 2,
    "cursor": "107777290731521"
}
```


## 开始直播

**API说明:** 

修改直播直播状态，开启直播。开始直播的前提：1、直播间当前状态是未直播的状态；2、直播间当前状态是正在直播，但主播离线，此时其他用户可进入直播间进行直播。否则返回403错误码。

**Path:** `http://localhost:8080/appserver/liverooms/{liveroomId}/users/{username}/ongoing`

**HTTP Method:** `POST`

**Permission:** App User

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
curl -X POST http://localhost:8081/appserver/liverooms/107780133421057/users/hxtest1/ongoing -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "id": "107780133421057",
    "name": "test1",
    "description": "server create chatroom",
    "owner": "hxtest1",
    "created": 1582185545093,
    "mute": false,
    "cover": "http://172.0.0.1:8080/cover/pictiure",
    "persistent": true,
    "video_type": "live",
    "status": "ongoing",
    "showid": 4,
    "maxusers": 300,
    "affiliations_count": 1,
    "affiliations": [
        {
            "member": "hxtest2"
        }
    ]
}
```


## 结束直播

**API说明:** 修改直播直播状态，结束直播。结束直播条件：需要是直播间主播，且直播间当前直播状态是正在直播，可结束直播。否则返回403错误码。

**Path:** `http://localhost:8080/appserver/liverooms/{liveroomId}/users/{username}/offline`

**HTTP Method:** `POST`

**Permission:** LIveRoom Owner

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
curl -X POST http://localhost:8081/appserver/liverooms/107780133421057/users/hxtest1/offline -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "id": "107780133421057",
    "name": "test1",
    "owner": "hxtest1",
    "cover": "http://177.0.0.1:8080/cover/pictiure",
    "persistent": true,
    "video_type": "live",
    "status": "offline",
    "showid": 4,
    "affiliations_count": 1
}
```


## 删除直播间

**API说明:** 删除直播间。

**Path:** `http://localhost:8080/appserver/liverooms/{liveroomId}`

**HTTP Method:** `DELETE`

**Permission:** LIveRoom Owner

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
curl -X DELETE http://localhost:8081/appserver/liverooms/107780133421057 -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "id": "107780133421057",
    "name": "test1",
    "description": "server create chatroom",
    "owner": "hxtest1",
    "created": 1583320857682,
    "cover": "http://172.0.0.1:8080/cover/pictiure",
    "persistent": true,
    "video_type": "live",
    "status": "offline",
    "showid": 0,
    "affiliations_count": 1
}
```


## 转让直播间

**API说明:** 转让直播间。由主播将直播间转让给其他用户。

**Path:** `http://localhost:8080/appserver/liverooms/{liveroomId}/owner/{newOwner}`

**HTTP Method:** `PUT`

**Permission:** LIveRoom Owner

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
curl -X PUT http://localhost:8081/appserver/liverooms/107780133421057/owner/hxtest2 -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8' -H 'Content-Type: application/json'
```

**返回示例:**

```json
{
    "id": "107780133421057",
    "name": "test1",
    "description": "server create chatroom",
    "owner": "hxtest2",
    "created": 1583320900413,
    "cover": "http://172.0.0.1:8080/cover/pictiure",
    "persistent": true,
    "video_type": "live",
    "status": "offline",
    "showid": 0,
    "affiliations_count": 1
}
```


## 获取声网 CDN 推流地址

**HTTP Method:** `GET`

**Path:** `http://localhost:8080/appserver/agora/cdn/streams/url/push`

**Permission:** `App User`

**Request Parameters:** 

| 参数      | 类型    | 说明                                                         |
| --------- | ------- | ------------------------------------------------------------ |
| domain    | String  | 声网 CDN 直播推流域名 |
| pushPoint | String  | 声网 CDN 直播发布点，传 `live` 即可                          |
| streamKey | String  | 声网 CDN 直播流名称，目前以 `channel` 即聊天室id命名，此id唯一 |
| expire    | Integer | 声网 CDN 直播推流地址有效期，单位：秒                        |

**Request Headers:** 

| 参数          | 说明             |
| ------------- | ---------------- |
| Content-Type  | application/json |
| Authorization | Bearer ${token}  |

**请求示例:**

```
curl -X GET -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json' 'http://localhost:8080/appserver/agora/cdn/streams/url/push?domain=xxxxxx&pushPoint=live&streamKey=167393094598658&expire=3600'
```

**Response Parameters:**

| 参数 | 类型   | 说明                        |
| ---- | ------ | --------------------------- |
| data | String | 声网CDN直播推流地址(防盗链) |

**返回示例:**

```json
{
    "data": "rtmp://xxxxxx/live/167393094598658?ts=1638955459&sign=692c3214f8de39cf"
}
```


## 获取声网 CDN 拉流地址

**HTTP Method:** `GET`

**Path:** `http://localhost:8080/appserver/agora/cdn/streams/url/play`

**Permission:** `App User`

**Request Parameters:** 

| 参数      | 类型   | 说明                                                         |
| --------- | ------ | ------------------------------------------------------------ |
| protocol  | String | 播放协议，分为 rtmp、flv、hls                                |
| domain    | String | 声网 CDN 直播拉流域名 |
| pushPoint | String | 声网 CDN 直播发布点，传 `live` 即可                          |
| streamKey | String | 声网 CDN 直播流名称，目前以 `channel` 即聊天室id命名，此id唯一 |

**Request Headers:** 

| 参数          | 说明             |
| ------------- | ---------------- |
| Content-Type  | application/json |
| Authorization | Bearer ${token}  |

**请求示例:**

```
curl -X GET -H 'Authorization: Bearer YWMtVPHfHCeREeqZiOl8_bc0eAAAAAAAA' -H 'Content-Type: application/json' 'http://localhost:8080/appserver/agora/cdn/streams/url/play?protocol=hls&domain=xxxxxx&pushPoint=live&streamKey=167393094598658'
```

**Response Parameters:**

| 参数 | 类型   | 说明                        |
| ---- | ------ | --------------------------- |
| data | String | 声网CDN直播拉流地址(防盗链) |

**返回示例:**

```json
{
    "data": "http://xxxxxx/live/167393094598658/playlist.m3u8?ts=1638955646&sign=ca42533c45d3b4dc"
}
```
