package com.solution.entity;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class Room {

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




}
