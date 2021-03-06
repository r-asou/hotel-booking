package com.solution.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class GuestBookingsRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 客户名
     */
    private String guestName;
    /**
     * 该客户的所有预订记录
     */
    private List<BookingRecordDTO> bookingRecordDtoList;

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public List<BookingRecordDTO> getBookingRecordDtoList() {
        return bookingRecordDtoList;
    }

    public void setBookingRecordDtoList(List<BookingRecordDTO> bookingRecordDtoList) {
        this.bookingRecordDtoList = bookingRecordDtoList;
    }

    public GuestBookingsRespDTO() {
    }

    public GuestBookingsRespDTO(String guestName, List<BookingRecordDTO> bookingRecordDtoList) {
        this.guestName = guestName;
        this.bookingRecordDtoList = bookingRecordDtoList;
    }
}
