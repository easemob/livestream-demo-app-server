package com.easemob.live.server.liveroom.api.response;

import lombok.Data;

import java.util.Map;

@Data
public class LiveRoomBroadcastResponse {
    private Map<String, Long> data;
}
