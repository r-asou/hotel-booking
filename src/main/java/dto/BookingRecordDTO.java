package dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class BookingRecordDTO implements Serializable {

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
