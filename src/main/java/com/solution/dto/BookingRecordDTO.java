package com.solution.dto;

import java.io.Serializable;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class BookingRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 客户名
     */
    private String guestName;
    /**
     * 房间号
     */
    private Integer roomNumber;
    /**
     * 预定日期
     */
    private String date;

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BookingRecordDTO() {
    }

    public BookingRecordDTO(String guestName, Integer roomNumber, String date) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = date;
    }
}
