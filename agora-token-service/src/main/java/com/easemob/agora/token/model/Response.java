package com.agora.token.model;

import lombok.Data;

@Data
public class Response {
    private  Integer  resCode;
    private  String rtcToken;
    private  String errorInfo;
}
