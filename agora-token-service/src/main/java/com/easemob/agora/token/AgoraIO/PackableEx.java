package com.agora.token.AgoraIO;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
