package com.solution.service;

import com.solution.dto.RoomDTO;
import com.solution.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JC Liu
 * @date 2022/1/16
 */
public class RoomService {

    /**
     * 查询给定日期下所有可预订房价
     *
     * @param date 入住日期
     * @return 返回可预订房间列表
     */
    public List<RoomDTO> getAvailableRoomByDate(String date) {
        List<RoomDTO> result = new ArrayList<>();
        if (Room.getAvailableRoom().containsKey(date)) {
            List<Room> rooms = Room.getAvailableRoom().get(date);
            rooms.forEach(ele -> {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoomNumber(ele.getRoomNumber());
                result.add(roomDTO);
            });
        }
        return result;
    }

    /**
     * 更新指定日期可订房间信息
     *
     * @param date   指定日期
     * @param roomId 房间号
     */
    public void updateAvailableRooms(String date, Integer roomId) {
        Map<String, List<Room>> availableRoom = Room.getAvailableRoom();
        List<Room> rooms = availableRoom.get(date);
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                rooms.remove(room);
                break;
            }
        }
    }
}
