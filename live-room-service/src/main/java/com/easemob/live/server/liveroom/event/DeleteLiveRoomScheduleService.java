package com.easemob.live.server.liveroom.event;

import com.easemob.live.server.liveroom.model.LiveRoomDetails;
import com.easemob.live.server.liveroom.model.LiveRoomDetailsRepository;
import com.easemob.live.server.rest.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeleteLiveRoomScheduleService {
    
    private LiveRoomDetailsRepository liveRoomDetailsRepository;

    private RestClient restClient;

    @Value("${easemob.clear.liveroom.switch}")
    private boolean clearLiveRoomSwitch;

    @Value("${easemob.liveroom.retention.time.second}")
    private long liveRoomRetentionTime;

    public DeleteLiveRoomScheduleService(LiveRoomDetailsRepository liveRoomDetailsRepository,
            RestClient restClient) {
        this.liveRoomDetailsRepository = liveRoomDetailsRepository;
        this.restClient = restClient;
    }

    @Async("scheduleThread")
    @Scheduled(cron = "${easemob.delete.liveroom.scheduler.time}")
    public void deleteScheduler() {

        if (clearLiveRoomSwitch) {
            long id = 0L;
            while (true) {
                List<LiveRoomDetails>
                        liveRoomDetailsList = liveRoomDetailsRepository.findLiveRoomsAfterId(id, 5);
                if (liveRoomDetailsList != null && liveRoomDetailsList.size() > 0) {
                    for (LiveRoomDetails liveRoomDetails : liveRoomDetailsList) {
                        long chatroomId = liveRoomDetails.getId();
                        long chatroomCreated = liveRoomDetails.getCreated();
                        long timeDifference = System.currentTimeMillis() - chatroomCreated;

                        if (timeDifference > liveRoomRetentionTime * 1000L) {
                            try {
                                restClient.deleteChatroom(String.valueOf(chatroomId), restClient.retrieveAppToken());
                            } catch (Exception e) {
                                log.error("rest delete live room fail. chatroomId : {}, error : {}", chatroomId, e.getMessage());
                            }
                            liveRoomDetailsRepository.deleteById(chatroomId);
                            log.info("Delete live room exceeding retention time : {}", chatroomId);
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
