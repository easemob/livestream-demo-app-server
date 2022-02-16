package com.easemob.agora.token.AgoraIO;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
