package com.easemob.live.server.liveroom.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenchong@easemob.com 2020/2/20
 */
@Repository
public interface LiveRoomDetailsRepository extends JpaRepository<LiveRoomDetails, Long> {

    @Query(value = "select * from live_room_details where video_type=0 and id < ?1 order by id desc limit ?2", nativeQuery = true)
    List<LiveRoomDetails> findLiveRoomsBeforeId(Long id, int limit);

    @Query(value = "select * from live_room_details where video_type=0 and id > ?1 order by id desc limit ?2", nativeQuery = true)
    List<LiveRoomDetails> findLiveRoomsAfterId(Long id, int limit);

    @Query(value = "select * from live_room_details where video_type=6 limit 1", nativeQuery = true)
    LiveRoomDetails findPromotionLiveRoom();

    @Query(value = "select * from live_room_details where id = ?", nativeQuery = true)
    LiveRoomDetails findLiveRoomById(Long id);
}
