package com.easemob.live.server.liveroom.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonSerialize
public class LiveRoomBroadcastRequest {

    @JsonProperty("msg")
    private Map msg;

    // 可以没有from字段，若有则不能为空串
    @JsonProperty("from")
    private String from ;

    // 扩展消息，可选参数必须为Map类型
    private Object ext;

    @JsonProperty("sync_device")
    private Boolean syncDevice;

    //聊天室消息优先级
    @JsonProperty("chatroom_msg_level")
    private String chatroomMsgLevel;

    @Override
    public String toString() {
        return "MessageRequest{" +
                ", from='" + from + '\'' +
                ", syncDevice='" + syncDevice + '\'' +
                ", chatroomMsgLevel='" + chatroomMsgLevel + '\'' +
                '}';
    }
}
