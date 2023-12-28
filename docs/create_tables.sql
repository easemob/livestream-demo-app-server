create database app_server character set utf8mb4;

CREATE TABLE `live_room_user_info`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `appkey`   varchar(512) NOT NULL COMMENT 'appkey',
    `username` varchar(200) NOT NULL COMMENT '用户名',
    `nickname` varchar(100) DEFAULT NULL COMMENT '用户昵称',
    `icon_key` varchar(512) DEFAULT NULL COMMENT '头像key',
    `created`  bigint(20) DEFAULT NULL COMMENT '用户创建时间戳',
    PRIMARY KEY (`id`),
    KEY        `idx_appkey_username` (`appkey`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `live_room_details`
(
    `id`                 bigint(20) NOT NULL COMMENT '直播间ID，即对应的聊天室 ID，聊天室唯一标识符，由环信服务器生成',
    `name`               varchar(512) NOT NULL COMMENT '直播间名称，即对应的聊天室名称，任意字符串',
    `icon_key`           varchar(200)          DEFAULT NULL COMMENT '直播间创建者头像key',
    `description`        varchar(512)          DEFAULT NULL COMMENT '直播间描述，即对应的聊天室描述，任意字符串',
    `created`            bigint(20) DEFAULT NULL COMMENT '直播间创建时间戳',
    `owner`              varchar(512)          DEFAULT NULL COMMENT '直播间主播的username，也是对应聊天室的所有者',
    `showid`             bigint(20) DEFAULT NULL COMMENT '直播场次ID',
    `status`             int(11) DEFAULT NULL COMMENT '直播状态',
    `cover`              varchar(512)          DEFAULT NULL COMMENT '直播间封面Url',
    `affiliations_count` int(11) DEFAULT NULL COMMENT '直播间人数',
    `ext`                varchar(1024)         DEFAULT NULL COMMENT '直播间扩展参数',
    `persistent`         bit(1)       NOT NULL DEFAULT b'1',
    `video_type`         int(11) NOT NULL DEFAULT '0',
    `channel`            varchar(64)  NOT NULL,
    PRIMARY KEY (`id`),
    KEY                  `SHOW_STATUS` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE live_room_details ADD channel varchar(64) NOT NULL;
