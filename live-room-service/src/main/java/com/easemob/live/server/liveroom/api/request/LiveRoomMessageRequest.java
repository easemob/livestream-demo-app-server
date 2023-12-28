package com.easemob.live.server.liveroom.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonSerialize
@Builder
public class LiveRoomMessageRequest {

    // 可以没有from字段，若有则不能为空串
    @JsonProperty("from")
    private String from ;

    @JsonProperty("to")
    private List<String> to ;

    @JsonProperty("type")
    private String type;

    @JsonProperty("body")
    private Map<String, String> body;

    @Override public String toString() {
        return "LiveRoomMessageRequest{" +
                "from='" + from + '\'' +
                ", to=" + to +
                ", type='" + type + '\'' +
                ", body=" + body +
                '}';
    }
}
