package com.solution.dto;

import java.io.Serializable;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class RoomDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public RoomDTO(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomDTO() {
    }
}
