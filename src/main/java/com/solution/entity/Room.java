package com.solution.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class Room {
    // 只保存每天可定房间，实际只操作availableRoom容器，可以根据要求进行实时更新
    private static final Map<String, List<Room>> availableRoom = new ConcurrentHashMap<>();
    /**
     * 房间号
     */
    private Integer roomNumber;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room(Integer roomId) {
        this.roomNumber = roomId;
    }

    /**
     * 配置指定日期的房间数量
     *
     * @param number 初始化房间数量
     */
    public static void configureRoomNumber(String date, Integer number) {
        List<Room> list = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            Room room = new Room(i);
            list.add(room);
        }
        availableRoom.put(date, list);
    }

    public static Map<String, List<Room>> getAvailableRoom() {
        return availableRoom;
    }


}
