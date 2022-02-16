package com.easemob.agora.token.service.impl;

import com.easemob.agora.token.AgoraIO.RtcTokenGenerate;
import com.easemob.agora.token.config.AppConfig;
import com.easemob.agora.token.model.TokenInfo;
import com.easemob.agora.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author skyfour
 * @date 2021/2/2
 * @email skyzhang@easemob.com
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private AppConfig appConfig;

    @Value("${agora.token.expire.period.seconds:86400}")
    private int expireTime;

    @Override public TokenInfo getRtcToken(String channelName, String agoraUid) {
        if (StringUtils.isEmpty(channelName) || StringUtils.isEmpty(agoraUid)) {
            throw new IllegalArgumentException("channelName or agoraUid must not null.");
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpireTime(expireTime);
        tokenInfo.setToken(RtcTokenGenerate
                .generateToken(appConfig.getAppId(), appConfig.getAppCert(),
                        channelName, agoraUid, expireTime));
        return tokenInfo;
    }

}
