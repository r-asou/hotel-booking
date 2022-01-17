package com.solution.service;

import com.solution.database.InMemoryDatabase;
import com.solution.dto.BookingRecordDTO;
import com.solution.dto.GuestBookingsRespDTO;
import com.solution.entity.BookingRecord;
import com.solution.exception.BusinessException;
import com.solution.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author JC Liu
 * @date 2022/1/16
 */
public class BookingService {

    private final RoomService roomService;
    private static final Lock lock = new ReentrantLock();
    public BookingService(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * 订购房间
     *
     * @param dto dto.BookingDTO
     * @return 订房是否成功
     */
    public boolean storeBooking(BookingRecordDTO dto) throws BusinessException {
        // 判断入参是否为空
        if (dto == null || dto.getDate() == null || dto.getGuestName() == null || dto.getRoomNumber() == null) {
            throw new BusinessException("参数非法");
        }
        // 判断房间号是否存在
        Integer roomNumber = dto.getRoomNumber();
        try {
            lock.lock();
            long count = roomService.getAvailableRoomByDate(dto.getDate())
                    .stream()
                    .filter(e -> e.getRoomNumber().equals(roomNumber))
                    .count();// 判断当天该房是否可以预订
            if (count == 0) {
                return false;
            }
            BookingRecord record = ConvertUtils.convertDtoToBookingRecord.apply(dto);
            InMemoryDatabase.getBookingRecordList().add(record);
            //移除可订房间
            roomService.updateAvailableRooms(dto.getDate(), dto.getRoomNumber());
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 根据客户名查看其所有订购记录
     *
     * @param guestName 客户名
     * @return 返回订购记录
     */
    public GuestBookingsRespDTO findBookingRecordByGuestName(String guestName) {
        List<BookingRecord> result = InMemoryDatabase.getBookingRecordList()
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
