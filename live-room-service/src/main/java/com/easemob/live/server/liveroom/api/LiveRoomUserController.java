package com.easemob.live.server.liveroom.api;

import com.easemob.live.server.liveroom.api.request.LiveRoomUserLoginRequest;
import com.easemob.live.server.liveroom.service.LiveRoomUserService;
import com.easemob.live.server.rest.chatroom.LiveRoomUserLoginInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LiveRoomUserController {

    private LiveRoomUserService liveRoomUserService;

    public LiveRoomUserController(LiveRoomUserService liveRoomUserService) {
        this.liveRoomUserService = liveRoomUserService;
    }

    @PostMapping("/internal/appserver/liverooms/user/login")
    public ResponseEntity login(@RequestBody @Valid LiveRoomUserLoginRequest request) {

        LiveRoomUserLoginInfo liveRoomUserLoginInfo = this.liveRoomUserService.userLogin(request);

        return ResponseEntity.ok(liveRoomUserLoginInfo);
    }

}
