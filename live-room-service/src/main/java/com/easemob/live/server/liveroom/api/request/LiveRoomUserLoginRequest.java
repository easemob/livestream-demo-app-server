package com.easemob.live.server.liveroom.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LiveRoomUserLoginRequest {
    @NotBlank(message = "username must be provided")
    private String username;

    @NotBlank(message = "nickname must be provided")
    private String nickname;

    @NotBlank(message = "iconKey must be provided")
    @JsonProperty("icon_key")
    private String iconKey;
}
