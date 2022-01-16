package com.solution.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JC Liu
 * @date 2022/1/16
 */
public class BookingRecord {
    // 保存预订记录
    private static volatile List<BookingRecord> bookingRecordList = new ArrayList<>();
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


    public BookingRecord() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BookingRecord(String guestName, Integer roomNumber, String date) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = date;
    }

    public static List<BookingRecord> getBookingRecordList() {
        return bookingRecordList;
    }
}
