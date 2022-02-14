package com.easemob.live.server.agora.token;


import com.easemob.agora.token.model.ResponseParam;
import com.easemob.agora.token.model.TokenInfo;
import com.easemob.agora.token.service.TokenService;
import com.easemob.agora.token.utils.RandomUidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AgoraTokenController {

    private final TokenService tokenService;

    public AgoraTokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/agora/rtcToken")
    public ResponseParam getAgoraToken(
            @RequestParam(name = "channelName") String channelName,
            @RequestParam(name = "agoraUserId", required = false) Integer agoraUserId) {

        String uid;

        if (StringUtils.isEmpty(channelName)) {
            throw new IllegalArgumentException("channelName must not empty.");
        }

        if (agoraUserId == null || agoraUserId == 0) {
            uid = RandomUidUtil.randomUid();
        } else {
            uid = String.valueOf(agoraUserId);
        }

        ResponseParam responseParam = new ResponseParam();
        TokenInfo token = tokenService.getRtcToken(channelName, uid);
        responseParam.setAccessToken(token.getToken());
        responseParam.setExpireTime(token.getExpireTime());
        responseParam.setAgoraUserId(Integer.valueOf(uid));
        return responseParam;
    }
}
