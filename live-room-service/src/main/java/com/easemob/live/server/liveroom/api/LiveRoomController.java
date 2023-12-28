package com.easemob.live.server.liveroom.api;

import com.easemob.live.server.liveroom.api.request.CreateLiveRoomRequest;
import com.easemob.live.server.liveroom.api.request.LiveRoomPromotionRequest;
import com.easemob.live.server.liveroom.api.request.LiveRoomRequest;
import com.easemob.live.server.liveroom.api.response.LiveRoomListResponse;
import com.easemob.live.server.liveroom.model.LiveRoomInfo;
import com.easemob.live.server.liveroom.model.VideoType;
import com.easemob.live.server.liveroom.service.LiveRoomService;
import com.easemob.live.server.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author shenchong@easemob.com 2020/2/19
 */
@Slf4j
@RestController
public class LiveRoomController {

    private final LiveRoomService liveRoomService;
    private final Integer batchMaxSize;

    public LiveRoomController(LiveRoomService liveRoomService,
                              LiveRoomProperties properties) {
        this.liveRoomService = liveRoomService;
        this.batchMaxSize = properties.getBatchMaxSize();
    }

    /**
     * 创建直播间
     */
    @PostMapping("/internal/appserver/liverooms")
    public ResponseEntity createLiveRoom(@RequestBody @Valid CreateLiveRoomRequest requestBody,
                                         HttpServletRequest request) {

        RequestUtils.resolveAuthorizationToken(request.getHeader(AUTHORIZATION));

        return ResponseEntity.ok(liveRoomService.createLiveRoom(requestBody));
    }

    /**
     * 创建宣传直播间
     */
    @PostMapping("/internal/appserver/promotion/liverooms")
    public ResponseEntity createPromotionLiveRoom(@RequestBody
    LiveRoomPromotionRequest liveRoomPromotionRequest,
            HttpServletRequest request) {

        RequestUtils.resolveAuthorizationToken(request.getHeader(AUTHORIZATION));

        return ResponseEntity.ok(liveRoomService.createPromotionLiveRoom(liveRoomPromotionRequest.getIconKey()));
    }

    /**
     * 获取直播间详情
     */
    @GetMapping("/internal/appserver/liverooms/{liveroomId}")
    public ResponseEntity getLiveRoomInfo(@PathVariable("liveroomId") String liveroomId,
                                          HttpServletRequest request) {

        String token = RequestUtils
                .resolveAuthorizationToken(request.getHeader(AUTHORIZATION));

        return ResponseEntity.ok(liveRoomService.getLiveRoomInfo(liveroomId, token));
    }

    /**
     * 根据直播间创建时间逆序获取直播间列表
     * @param limit 默认为10，最大为100
     * @param cursor 默认为Long.MAX_VALUE
     */
    @GetMapping("/internal/appserver/liverooms")
    public ResponseEntity getLiveRooms(@RequestParam(name = "limit", required = false) Integer limit,
                                       @RequestParam(name = "cursor", required = false,
                                               defaultValue = "9223372036854775807") String cursor,
                                       @RequestParam(name = "video_type", required = false) VideoType videoType,
                                       HttpServletRequest request) {

        limit = getLimit(limit);
        RequestUtils.resolveAuthorizationToken(request.getHeader(AUTHORIZATION));

        List<LiveRoomInfo> liveRoomInfos = liveRoomService.getLiveRooms(videoType, Long.valueOf(cursor), limit);

        LiveRoomListResponse response = new LiveRoomListResponse();
        response.setEntities(liveRoomInfos);
        response.setCount(liveRoomInfos.size());

        if (!liveRoomInfos.isEmpty() && liveRoomInfos.size() == limit) {
            response.setCursor(liveRoomInfos.get(liveRoomInfos.size() - 1).getId());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 删除直播间
     */
    @DeleteMapping("/internal/appserver/liverooms/{liveroomId}")
    public ResponseEntity deleteLiveRoom(@PathVariable("liveroomId") String liveroomId,
                                         HttpServletRequest request) {

        RequestUtils
                .resolveAuthorizationToken(request.getHeader(AUTHORIZATION));

        return ResponseEntity.ok(liveRoomService.deleteLiveRoom(liveroomId));
    }

    private int getLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return 10;
        }
        return limit > batchMaxSize ? batchMaxSize : limit;
    }
}
