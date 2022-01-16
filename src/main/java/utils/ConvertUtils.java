package utils;

import dto.BookingRecordDTO;
import dto.RoomDTO;
import entity.BookingRecord;
import entity.Room;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class ConvertUtils {

    public RoomDTO convertRoomToDto(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber(room.getRoomNumber());
        return dto;
    }

    public BookingRecordDTO convertBookingRecordToDto(BookingRecord bookingRecord) {
        BookingRecordDTO recordDTO = new BookingRecordDTO();
        recordDTO.setRoomNumber(bookingRecord.getRoomNumber());
        recordDTO.setGuestName(bookingRecord.getGuestName());
        recordDTO.setDate(bookingRecord.getDate());
        return recordDTO;
    }


}
