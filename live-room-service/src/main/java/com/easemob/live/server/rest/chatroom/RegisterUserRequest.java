package com.easemob.live.server.rest.chatroom;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterUserRequest {
    private String username;
    private String grant_type;
    private boolean autoCreateUser;
    private long ttl;
}
