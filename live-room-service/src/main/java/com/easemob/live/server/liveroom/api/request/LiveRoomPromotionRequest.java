package com.easemob.live.server.liveroom.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LiveRoomPromotionRequest {

    @JsonProperty("icon_key")
    private String iconKey;

}
