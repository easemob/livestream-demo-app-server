package com.easemob.agora.token.service;

import com.agora.token.model.TokenInfo;

/**
 * @author skyfour
 * @date 2021/2/1
 * @email skyzhang@easemob.com
 */
public interface TokenService {

    /**
     * 通过agoraUid获取token
     * @param channelName
     * @param agoraUid
     * @return
     */
    TokenInfo getRtcToken(String channelName, String agoraUid);
}
