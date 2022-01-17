package com.solution.utils;

import com.solution.dto.BookingRecordDTO;
import com.solution.entity.BookingRecord;

import java.util.function.BiConsumer;
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

    /**
     * Dto转实体
     */
    Function<BookingRecordDTO, BookingRecord> convertDtoToBookingRecord = bookingRecordDTO -> {
        BookingRecord record = new BookingRecord();
        record.setRoomNumber(bookingRecordDTO.getRoomNumber());
        record.setGuestName(bookingRecordDTO.getGuestName());
        record.setDate(bookingRecordDTO.getDate());
        return record;
    };

    /**
     * JSON数据处理
     */
    Function<String, String[]> dataHandler = data -> {
        if (data.length() == 0) return new String[0];
        String[] splitContent = data.split(":");
        splitContent[0] = splitContent[0].trim().replaceAll("\"", "");
        if (splitContent[1].endsWith(",")) {
            splitContent[1] = splitContent[1].substring(0, splitContent[1].length() - 1);
        }
        return splitContent;
    };

    /**
     * 数据解析
     */
    BiConsumer<BookingRecordDTO, String> recordConverter = (dto, content) -> {
        String[] splitContent = content.split(":");
        if (content.contains("name")) {
            if (splitContent[1].endsWith(",")) {
                splitContent[1] = splitContent[1].substring(0, splitContent[1].length() - 1).replaceAll("\"", "");
            } else {
                splitContent[1] = splitContent[1].replaceAll("\"", "");
            }
            dto.setGuestName(splitContent[1].trim());
        } else if (content.contains("date")) {
            if (splitContent[1].endsWith(",")) {
                splitContent[1] = splitContent[1].substring(0, splitContent[1].length() - 1).replaceAll("\"", "");
            } else {
                splitContent[1] = splitContent[1].replaceAll("\"", "");
            }
            dto.setDate(splitContent[1].trim());
        } else if (content.contains("roomId")) {
            if (splitContent[1].endsWith(",")) {
                splitContent[1] = splitContent[1].substring(0, splitContent[1].length() - 1).replaceAll("\"", "").trim();
            } else {
                splitContent[1] = splitContent[1].replaceAll("\"", "").trim();
            }
            dto.setRoomNumber(Integer.valueOf(splitContent[1].trim()));
        }
    };
}
