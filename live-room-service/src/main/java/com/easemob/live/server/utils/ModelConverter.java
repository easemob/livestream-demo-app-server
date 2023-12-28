package com.easemob.live.server.utils;

import com.easemob.live.server.liveroom.model.LiveRoomInfo;
import com.easemob.live.server.liveroom.model.LiveRoomDetails;
import com.easemob.live.server.liveroom.model.LiveRoomUserInfo;

import java.util.Map;

/**
 * @author shenchong@easemob.com 2020/2/20
 */
public class ModelConverter {

    public static LiveRoomInfo detailsConverterLiveRoomInfo(LiveRoomDetails liveRoomDetails) {
        return LiveRoomInfo.builder()
                .id(liveRoomDetails.getId().toString())
                .name(liveRoomDetails.getName())
                .iconKey(liveRoomDetails.getIconKey())
                .description(liveRoomDetails.getDescription())
                .owner(liveRoomDetails.getOwner())
                .created(liveRoomDetails.getCreated())
                .persistent(liveRoomDetails.getPersistent())
                .videoType(liveRoomDetails.getVideoType())
                .cover(liveRoomDetails.getCover())
                .status(liveRoomDetails.getStatus())
                .showid(liveRoomDetails.getShowid())
                .affiliationsCount(liveRoomDetails.getAffiliationsCount())
                .ext(JsonUtils.parse(liveRoomDetails.getExt(), Map.class))
                .build();
    }

    public static LiveRoomInfo detailsConverterLiveRoomInfo(LiveRoomDetails liveRoomDetails, LiveRoomUserInfo liveRoomUserInfo) {
        return LiveRoomInfo.builder()
                .id(liveRoomDetails.getId().toString())
                .name(liveRoomDetails.getName())
                .iconKey(liveRoomUserInfo.getIconKey())
                .description(liveRoomDetails.getDescription())
                .nickname(liveRoomUserInfo.getNickname())
                .owner(liveRoomDetails.getOwner())
                .created(liveRoomDetails.getCreated())
                .persistent(liveRoomDetails.getPersistent())
                .videoType(liveRoomDetails.getVideoType())
                .cover(liveRoomDetails.getCover())
                .status(liveRoomDetails.getStatus())
                .showid(liveRoomDetails.getShowid())
                .affiliationsCount(liveRoomDetails.getAffiliationsCount())
                .ext(JsonUtils.parse(liveRoomDetails.getExt(), Map.class))
                .build();
    }
}
