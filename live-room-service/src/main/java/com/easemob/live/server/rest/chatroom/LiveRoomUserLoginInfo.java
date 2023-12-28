package com.easemob.live.server.rest.chatroom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LiveRoomUserLoginInfo {

    private String userName;

    @JsonProperty("icon_key")
    private String iconKey;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;


}
