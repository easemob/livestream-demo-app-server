package com.easemob.live.server.liveroom.event;

import com.easemob.live.server.liveroom.api.request.LiveRoomMessageRequest;
import com.easemob.live.server.liveroom.model.LiveRoomDetails;
import com.easemob.live.server.liveroom.model.LiveRoomDetailsRepository;
import com.easemob.live.server.rest.RestClient;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DeleteLiveRoomNoticeScheduleService implements InitializingBean {

    private LiveRoomDetailsRepository liveRoomDetailsRepository;

    private RestClient restClient;

    @Value("${easemob.clear.liveroom.notice.switch}")
    private boolean clearLiveRoomNoticeSwitch;

    @Value("${easemob.liveroom.delete.notice.time.second}")
    private long liveRoomDeleteNoticeTime;

    @Value("${easemob.liveroom.retention.time.second}")
    private long liveRoomRetentionTime;

    @Value("${easemob.liveroom.send.notice.content}")
    private String sendNoticeContent;

    private ThreadPoolExecutor threadPool;

    public DeleteLiveRoomNoticeScheduleService(LiveRoomDetailsRepository liveRoomDetailsRepository,
            RestClient restClient) {
        this.liveRoomDetailsRepository = liveRoomDetailsRepository;
        this.restClient = restClient;
    }

    @Override public void afterPropertiesSet() {
        threadPool = new ThreadPoolExecutor(
                16,
                100,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder().setNameFormat("liveroom-notice").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Async("notciesSheduleThread")
    @Scheduled(cron = "${easemob.delete.liveroom.notice.scheduler.time}")
    public void deleteScheduler() {

        if (clearLiveRoomNoticeSwitch) {
            long id = 0L;
            while (true) {
                List<LiveRoomDetails>
                        liveRoomDetailsList = liveRoomDetailsRepository.findLiveRoomsAfterId(id, 5);
                if (liveRoomDetailsList != null && liveRoomDetailsList.size() > 0) {
                    for (LiveRoomDetails liveRoomDetails : liveRoomDetailsList) {
                        long chatroomId = liveRoomDetails.getId();
                        long chatroomCreated = liveRoomDetails.getCreated();
                        long timeDifference = (System.currentTimeMillis() - chatroomCreated) / 1000;

                        if (timeDifference == (liveRoomRetentionTime - liveRoomDeleteNoticeTime)) {

                            threadPool.execute(() -> {
                                try {
                                    List<String> to = new ArrayList<>();
                                    to.add(String.valueOf(chatroomId));

                                    LiveRoomMessageRequest liveRoomMessageRequest =
                                            LiveRoomMessageRequest.builder()
                                                    .to(to)
                                                    .type("txt")
                                                    .body(new HashMap<String, String>() {{
                                                        put("msg", sendNoticeContent);
                                                    }})
                                                    .build();

                                    restClient.sendLiveRoomsMessage(liveRoomMessageRequest,
                                            restClient.retrieveAppToken());
                                } catch (Exception e) {
                                    log.error(
                                            "rest send live room notice message fail. chatroomId : {}, error : {}",
                                            chatroomId, e.getMessage());
                                }
                            });
                        }
                    }

                    id = liveRoomDetailsList.get(liveRoomDetailsList.size() - 1).getId();
                } else {
                    break;
                }
            }
        }
    }

}
