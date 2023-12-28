package com.easemob.live.server.liveroom.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveRoomUserRepository extends JpaRepository<LiveRoomUserInfo, Long> {

    @Query(value = "select * from live_room_user_info where appkey = ? and username = ?", nativeQuery = true)
    LiveRoomUserInfo findByAppkeyAndUsername(String appkey, String username);
}
