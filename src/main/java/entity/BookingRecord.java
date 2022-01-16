package entity;

import java.util.Date;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class BookingRecord {

    private String guestName;
    private Integer roomNumber;
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
}
