package utils;

import dto.BookingRecordDTO;
import dto.RoomDTO;
import entity.BookingRecord;
import entity.Room;

import java.util.function.Function;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public interface ConvertUtils {

    Function<Room, RoomDTO> convertRoomToDto = room -> {
        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber(room.getRoomNumber());
        return dto;
    };

    Function<BookingRecord, BookingRecordDTO> convertBookingRecordToDto = bookingRecord -> {
        BookingRecordDTO recordDTO = new BookingRecordDTO();
        recordDTO.setRoomNumber(bookingRecord.getRoomNumber());
        recordDTO.setGuestName(bookingRecord.getGuestName());
        recordDTO.setDate(bookingRecord.getDate());
        return recordDTO;
    };


}
