package com.solution.utils;

import com.solution.dto.BookingRecordDTO;
import com.solution.entity.BookingRecord;

import java.util.function.Function;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public interface ConvertUtils {

    /**
     * BookingRecord实体类转DTO
     */
    Function<BookingRecord, BookingRecordDTO> convertBookingRecordToDto = bookingRecord -> {
        BookingRecordDTO recordDTO = new BookingRecordDTO();
        recordDTO.setRoomNumber(bookingRecord.getRoomNumber());
        recordDTO.setGuestName(bookingRecord.getGuestName());
        recordDTO.setDate(bookingRecord.getDate());
        return recordDTO;
    };

    Function<BookingRecordDTO, BookingRecord> convertDtoToBookingRecord = bookingRecordDTO -> {
        BookingRecord record = new BookingRecord();
        record.setRoomNumber(bookingRecordDTO.getRoomNumber());
        record.setGuestName(bookingRecordDTO.getGuestName());
        record.setDate(bookingRecordDTO.getDate());
        return record;
    };
}
