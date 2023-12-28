package com.easemob.live.server.liveroom.service;

import com.easemob.live.server.liveroom.api.request.LiveRoomUserLoginRequest;
import com.easemob.live.server.liveroom.model.LiveRoomUserInfo;
import com.easemob.live.server.liveroom.model.LiveRoomUserRepository;
import com.easemob.live.server.rest.RestClient;
import com.easemob.live.server.rest.chatroom.LiveRoomUserLoginInfo;
import com.easemob.live.server.rest.chatroom.RegisterUserResponse;
import org.springframework.stereotype.Service;

@Service
public class LiveRoomUserService {

    private RestClient restClient;

    private LiveRoomUserRepository liveRoomUserRepository;

    public LiveRoomUserService(RestClient restClient,
            LiveRoomUserRepository liveRoomUserRepository) {
        this.restClient = restClient;
        this.liveRoomUserRepository = liveRoomUserRepository;
    }

    public LiveRoomUserLoginInfo userLogin(LiveRoomUserLoginRequest request) {
        String appToken = restClient.retrieveAppToken();
        RegisterUserResponse response = restClient.registerUser(request.getUsername(), appToken);

        String appKey = restClient.getOrgName() + "#" + restClient.getAppName();
        LiveRoomUserInfo liveRoomUserInfo = new LiveRoomUserInfo();
        liveRoomUserInfo.setAppkey(appKey);
        liveRoomUserInfo.setUsername(request.getUsername());
        liveRoomUserInfo.setNickname(request.getNickname());
        liveRoomUserInfo.setIconKey(request.getIconKey());
        liveRoomUserInfo.setCreated(System.currentTimeMillis());

        LiveRoomUserInfo userInfo = this.liveRoomUserRepository.findByAppkeyAndUsername(appKey, request.getUsername().toLowerCase());
        if (userInfo == null) {
            this.liveRoomUserRepository.save(liveRoomUserInfo);
        }

        LiveRoomUserLoginInfo liveRoomUserLoginInfo = new LiveRoomUserLoginInfo();
        liveRoomUserLoginInfo.setUserName(request.getUsername());
        liveRoomUserLoginInfo.setIconKey(request.getIconKey());
        liveRoomUserLoginInfo.setAccessToken(response.getAccessToken());
        liveRoomUserLoginInfo.setExpiresIn(response.getExpiresIn());
        return liveRoomUserLoginInfo;
    }
}
