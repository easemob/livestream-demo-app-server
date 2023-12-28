package com.easemob.live.server.liveroom.event;

import com.easemob.live.server.liveroom.api.request.LiveRoomBroadcastRequest;
import com.easemob.live.server.rest.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LiveRoomSendBroadcastScheduleService implements InitializingBean {

    private RestClient restClient;

    @Value("${easemob.liveroom.send.broadcast.switch}")
    private boolean liveRoomSendBroadcastSwitch;

    @Value("${easemob.live.promotion.room.owner}")
    private String liveRoomSendBroadcastFrom;

    @Value("${easemob.liveroom.send.broadcast.content.a}")
    private String liveRoomSendBroadcastContentA;

    @Value("${easemob.liveroom.send.broadcast.content.b}")
    private String liveRoomSendBroadcastContentB;

    @Value("${easemob.liveroom.send.broadcast.content.c}")
    private String liveRoomSendBroadcastContentC;

    @Value("${easemob.liveroom.send.broadcast.content.d}")
    private String liveRoomSendBroadcastContentD;

    private List<String> liveRoomSendBroadcastContentList;

    private int liveRoomSendBroadcastContentIndex = 0;

    @Override public void afterPropertiesSet() throws Exception {
        liveRoomSendBroadcastContentList = new ArrayList<>(4);
        liveRoomSendBroadcastContentList.add(liveRoomSendBroadcastContentA);
        liveRoomSendBroadcastContentList.add(liveRoomSendBroadcastContentB);
        liveRoomSendBroadcastContentList.add(liveRoomSendBroadcastContentC);
        liveRoomSendBroadcastContentList.add(liveRoomSendBroadcastContentD);

    }

    public LiveRoomSendBroadcastScheduleService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Async("scheduleThread")
    @Scheduled(cron = "${easemob.liveroom.send.broadcast.scheduler.time}")
    public void sendBroadcastScheduler() {
        if (liveRoomSendBroadcastSwitch) {
            if (liveRoomSendBroadcastContentIndex >= liveRoomSendBroadcastContentList.size()) {
                liveRoomSendBroadcastContentIndex = 0;
            }
            LiveRoomBroadcastRequest broadcastRequest = new LiveRoomBroadcastRequest();
            Map<String, String> msg = new HashMap<>();
            msg.put("type", "txt");
            msg.put("msg", liveRoomSendBroadcastContentList.get(liveRoomSendBroadcastContentIndex++));
            broadcastRequest.setFrom(liveRoomSendBroadcastFrom);
            broadcastRequest.setMsg(msg);

            restClient.sendLiveRoomsBroadcast(broadcastRequest, restClient.retrieveAppToken());
        }
    }
}
