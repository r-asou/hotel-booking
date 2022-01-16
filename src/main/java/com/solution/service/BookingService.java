package com.solution.service;

import com.solution.dto.AddBookingDTO;
import com.solution.dto.BookingRecordDTO;
import com.solution.dto.GuestBookingsRespDTO;
import com.solution.dto.RoomDTO;
import com.solution.entity.BookingRecord;
import com.solution.entity.Room;
import com.solution.exception.BusinessException;
import com.solution.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JC Liu
 * @date 2022/1/16
 */
public class BookingService {

    private final RoomService roomService;

    public BookingService(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * 订购房间
     *
     * @param dto dto.BookingDTO
     * @return 订房是否成功
     */
    public boolean storeBooking(AddBookingDTO dto) throws BusinessException {
        // 判断入参是否为空
        if (dto == null || dto.getDate() == null || dto.getGuestName() == null || dto.getRoomNumber() == null) {
            throw new BusinessException("参数非法");
        }
        synchronized (Room.class) {
            // 判断房间号是否存在
            Integer roomNumber = dto.getRoomNumber();
            List<RoomDTO> availableRoomByDate = roomService.getAvailableRoomByDate(dto.getDate());// 获取当天的可定房间
            long count = availableRoomByDate.stream().filter(e -> e.getRoomNumber().equals(roomNumber)).count();
            if (count == 0) throw new BusinessException("该房间已被预定");

            // 判断是否已经定过房间
            long alreadyBookCount = BookingRecord.getBookingRecordList().stream()
                    .filter(r -> r.getDate().equals(dto.getDate()) && r.getGuestName().equals(dto.getGuestName()))
                    .count();
            if (alreadyBookCount == 0) {
                BookingRecord record = ConvertUtils.convertDtoToBookingRecord.apply(dto);
                BookingRecord.getBookingRecordList().add(record);
                //移除可订房间
                roomService.updateAvailableRooms(dto.getDate(), dto.getRoomNumber());
                return true;
            } else {
                throw new BusinessException("已经预订过房间了");
            }
        }
    }

    /**
     * 根据客户名查看其所有订购记录
     *
     * @param guestName 客户名
     * @return 返回订购记录
     */
    public GuestBookingsRespDTO findBookingRecordByGuestName(String guestName) {
        List<BookingRecord> result = BookingRecord.getBookingRecordList()
                .stream()
                .filter(ele -> ele.getGuestName().equals(guestName))
                .collect(Collectors.toList());

        GuestBookingsRespDTO resultDto = new GuestBookingsRespDTO();
        resultDto.setGuestName(guestName);
        if (result.size() == 0) {
            resultDto.setBookingRecordDtoList(Collections.emptyList());
        }
        if (result.size() > 0) {
            List<BookingRecordDTO> resultList = new ArrayList<>();
            result.forEach(ele -> {
                BookingRecordDTO recordDTO = ConvertUtils.convertBookingRecordToDto.apply(ele);
                resultList.add(recordDTO);

            });
            resultDto.setBookingRecordDtoList(resultList);
        }
        return resultDto;
    }
}
