package com.easemob.live.server.liveroom.service;

import com.easemob.live.server.liveroom.api.request.CreateLiveRoomRequest;
import com.easemob.live.server.liveroom.api.LiveRoomProperties;
import com.easemob.live.server.liveroom.model.*;
import com.easemob.live.server.liveroom.model.LiveRoomInfo;
import com.easemob.live.server.liveroom.exception.ForbiddenOpException;
import com.easemob.live.server.liveroom.exception.LiveRoomNotFoundException;
import com.easemob.live.server.rest.RestClient;
import com.easemob.live.server.rest.chatroom.CreateChatroomRequest;
import com.easemob.live.server.utils.JsonUtils;
import com.easemob.live.server.utils.ModelConverter;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author shenchong@easemob.com 2020/2/19
 */
@Slf4j
@Service
public class LiveRoomService {

    private RestClient restClient;

    private LiveRoomDetailsRepository liveRoomDetailsRepository;

    private LiveRoomUserRepository liveRoomUserRepository;

    private ApplicationEventPublisher eventPublisher;

    private int maxAffiliationsSize;

    @Value("${easemob.live.promotion.room.name}")
    private String promotionRoomName;

    @Value("${easemob.live.promotion.room.video.url}")
    private String promotionRoomVideoUrl;

    @Value("${easemob.live.promotion.room.owner}")
    private String promotionRoomOwner;

    private Cache<String, LiveRoomInfo> infoCache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(1000)
            .expireAfterWrite(10000, TimeUnit.MILLISECONDS)
            .build();

    public LiveRoomService(RestClient restClient,
            LiveRoomDetailsRepository liveRoomDetailsRepository,
            LiveRoomUserRepository liveRoomUserRepository,
            ApplicationEventPublisher eventPublisher,
            LiveRoomProperties properties) {
        this.restClient = restClient;
        this.liveRoomDetailsRepository = liveRoomDetailsRepository;
        this.liveRoomUserRepository = liveRoomUserRepository;
        this.eventPublisher = eventPublisher;
        this.maxAffiliationsSize = properties.getMaxAffiliationsSize();
    }


    public LiveRoomInfo createLiveRoom(CreateLiveRoomRequest liveRoomRequest) {

        log.info("create liveroom, request : {}", liveRoomRequest);

        String token = restClient.retrieveAppToken();

        // 创建聊天室
        CreateChatroomRequest chatroomRequest = CreateChatroomRequest.builder()
                .name(liveRoomRequest.getName())
                .description(liveRoomRequest.getDescription())
                .owner(liveRoomRequest.getOwner())
                .maxUsers(liveRoomRequest.getMaxUsers())
                .members(liveRoomRequest.getMembers())
                .mute(liveRoomRequest.getMute())
                .scale(CreateChatroomRequest.Scale.LARGE)
                .build();

        String chatroomId = restClient.createChatroom(chatroomRequest, token);

        log.info("create chatroom success, chatroomId : {}", chatroomId);

        String appKey = restClient.getOrgName() + "#" + restClient.getAppName();
        LiveRoomUserInfo userInfo = liveRoomUserRepository.findByAppkeyAndUsername(appKey, liveRoomRequest.getOwner());
        String userIconKey = userInfo.getIconKey();
        String nickname = userInfo.getNickname();

        // 获取聊天室详情
        LiveRoomInfo liveRoomInfo = restClient.retrieveChatroomInfo(chatroomId, token)
                .filterLiveRoomInfo(maxAffiliationsSize);
        liveRoomInfo.setPersistent(liveRoomRequest.getPersistent());
        liveRoomInfo.setVideoType(liveRoomRequest.getVideoType());
        liveRoomInfo.setCover(liveRoomRequest.getCover());
        liveRoomInfo.setExt(liveRoomRequest.getExt());
        liveRoomInfo.setIconKey(userIconKey);
        liveRoomInfo.setNickname(nickname);

        // 创建直播间
        LiveRoomDetails liveRoomDetails = LiveRoomDetails.builder()
                .id(Long.valueOf(liveRoomInfo.getId()))
                .name(liveRoomInfo.getName())
                .iconKey(userIconKey)
                .description(liveRoomInfo.getDescription())
                .created(liveRoomInfo.getCreated())
                .owner(liveRoomInfo.getOwner())
                .cover(liveRoomInfo.getCover())
                .persistent(liveRoomInfo.getPersistent())
                .videoType(liveRoomInfo.getVideoType())
                .status(liveRoomInfo.getStatus())
                .showid(liveRoomInfo.getShowid())
                .affiliationsCount(liveRoomInfo.getAffiliationsCount())
                .ext(JsonUtils.mapToJsonString(liveRoomInfo.getExt()))
                .channel("")
                .build();

        liveRoomDetailsRepository.save(liveRoomDetails);

        log.info("create liveroom success, liveRoomInfo : {}", liveRoomInfo);

        return liveRoomInfo;
    }

    public LiveRoomInfo createPromotionLiveRoom(String iconKey) {

        String token = restClient.retrieveAppToken();

        // 创建聊天室
        CreateChatroomRequest chatroomRequest = CreateChatroomRequest.builder()
                .name(promotionRoomName)
                .owner(promotionRoomOwner)
                .maxUsers(2000)
                .mute(false)
                .scale(CreateChatroomRequest.Scale.LARGE)
                .build();

        String chatroomId = restClient.createChatroom(chatroomRequest, token);

        log.info("create chatroom success, chatroomId : {}", chatroomId);

        // 获取聊天室详情
        LiveRoomInfo liveRoomInfo = restClient.retrieveChatroomInfo(chatroomId, token)
                .filterLiveRoomInfo(maxAffiliationsSize);
        liveRoomInfo.setPersistent(true);
        liveRoomInfo.setVideoType(VideoType.promotion_live);
        liveRoomInfo.setStatus(LiveRoomStatus.ONGOING);
        Map<String, Object> videoInfo = new HashMap<>();
        videoInfo.put("videoUrl", promotionRoomVideoUrl);
        liveRoomInfo.setExt(videoInfo);
        liveRoomInfo.setIconKey(iconKey);

        LiveRoomDetails liveRoomDetails = LiveRoomDetails.builder()
                .id(Long.valueOf(liveRoomInfo.getId()))
                .name(liveRoomInfo.getName())
                .iconKey(iconKey)
                .description(liveRoomInfo.getDescription())
                .created(liveRoomInfo.getCreated())
                .owner(liveRoomInfo.getOwner())
                .cover(liveRoomInfo.getCover())
                .persistent(liveRoomInfo.getPersistent())
                .videoType(liveRoomInfo.getVideoType())
                .status(liveRoomInfo.getStatus())
                .showid(liveRoomInfo.getShowid())
                .affiliationsCount(liveRoomInfo.getAffiliationsCount())
                .ext(JsonUtils.mapToJsonString(liveRoomInfo.getExt()))
                .channel(chatroomId)
                .build();
        liveRoomInfo.setChannel(chatroomId);

        liveRoomDetailsRepository.save(liveRoomDetails);

        log.info("create promotion liveroom success, liveRoomInfo : {}", liveRoomInfo);

        return liveRoomInfo;
    }

    public LiveRoomInfo getLiveRoomInfo(String liveroomId, String token) {
        return infoCache.get(liveroomId, key -> getLiveRoomInfoFromDb(key, token));
    }

    public LiveRoomInfo getLiveRoomInfoFromDb(String liveroomId, String token) {

        log.info("get liveroom info, liveroomId : {}, token : {}", liveroomId, token);

        LiveRoomInfo liveRoomInfo = restClient.retrieveChatroomInfo(liveroomId, token)
                .filterLiveRoomInfo(maxAffiliationsSize);

        LiveRoomDetails oldDetails =
                liveRoomDetailsRepository.findById(Long.valueOf(liveroomId))
                        .orElseThrow(
                                () -> new LiveRoomNotFoundException(liveroomId + " is not found"));

        // 更新直播间信息
        oldDetails.setName(liveRoomInfo.getName());
        oldDetails.setDescription(liveRoomInfo.getDescription());
        oldDetails.setOwner(liveRoomInfo.getOwner());
        oldDetails.setAffiliationsCount(liveRoomInfo.getAffiliationsCount());
        LiveRoomDetails liveRoomDetails = liveRoomDetailsRepository.save(oldDetails);

        liveRoomInfo.setPersistent(liveRoomDetails.getPersistent());
        liveRoomInfo.setVideoType(liveRoomDetails.getVideoType());
        liveRoomInfo.setCover(liveRoomDetails.getCover());
        liveRoomInfo.setStatus(liveRoomDetails.getStatus());
        liveRoomInfo.setShowid(liveRoomDetails.getShowid());
        liveRoomInfo.setExt(JsonUtils.parse(liveRoomDetails.getExt(), Map.class));

        return liveRoomInfo;
    }

    public List<LiveRoomInfo> getLiveRooms(VideoType videoType, Long cursor, int limit) {

        List<LiveRoomDetails> liveRoomDetailsList = new ArrayList<>();

        liveRoomDetailsList = liveRoomDetailsRepository.findLiveRoomsBeforeId(cursor, limit);
        if (cursor == 9223372036854775807L) {
            LiveRoomDetails PromotionLiveRoomDetails = liveRoomDetailsRepository.findPromotionLiveRoom();
            if (PromotionLiveRoomDetails != null) {
                liveRoomDetailsList.add(0, PromotionLiveRoomDetails);
            }
        }

        String appKey = restClient.getOrgName() + "#" + restClient.getAppName();
        return liveRoomDetailsList.stream()
                .map(liveRoomDetails -> {
                    LiveRoomUserInfo liveRoomUserInfo = liveRoomUserRepository.findByAppkeyAndUsername(appKey, liveRoomDetails.getOwner());
                    return ModelConverter.detailsConverterLiveRoomInfo(liveRoomDetails, liveRoomUserInfo);
                })
                .collect(Collectors.toList());
    }

    public LiveRoomInfo deleteLiveRoom(String liveroomId) {

        log.info("delete liveroom, liveroomId : {}", liveroomId);

        String token = restClient.retrieveAppToken();

        infoCache.invalidate(liveroomId);

        LiveRoomDetails liveRoomDetails =
                liveRoomDetailsRepository.findLiveRoomById(Long.valueOf(liveroomId));

        if (liveRoomDetails == null) {
            throw new LiveRoomNotFoundException("liveroom " + liveroomId + " is not found");
        }

        Boolean success = restClient.deleteChatroom(liveroomId, token);

        if (!success) {
            log.error("delete liveroom failed, liveroomId : {}, token : {}",
                    liveroomId, token);
            throw new ForbiddenOpException("delete liveroom failed");
        }

        liveRoomDetailsRepository.deleteById(Long.valueOf(liveroomId));

        return ModelConverter.detailsConverterLiveRoomInfo(liveRoomDetails);
    }
}
