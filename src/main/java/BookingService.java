
import dto.AddBookingDTO;
import dto.BookingRecordDTO;
import dto.GuestBookingsRespDTO;
import dto.RoomDTO;
import entity.BookingRecord;
import entity.Room;
import exception.BusinessException;
import utils.ConvertUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author JC Liu
 * @date 2022/1/16
 */
public class BookingService {

    // 只是保存Room的基础信息
    private static final List<Room> rooms = new ArrayList<>();

    // 保存预订记录
    private static volatile List<BookingRecord> bookingRecordList = new ArrayList<>();

    // 只保存每天可定房间，实际只操作availableRoom容器，可以根据要求进行实时更新
    private static final Map<String, List<Room>> availableRoom = new ConcurrentHashMap<>();

    /**
     * 配置房间数量
     *
     * @param num 初始化房间数量
     */
    public void initRoomNumber(Integer num) throws BusinessException {
        if (rooms.isEmpty()) {
            for (int i = 1; i <= num; i++) {
                Room room = new Room(i);
                rooms.add(room);
            }
        }
    }

    //查询房间 OK
    //预定房间 OK
    //预定房间 NG 房间没有了 报错
    //预定房间 NG 同一天已预定 报错
    //查询已预定记录

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
        synchronized (availableRoom) {
            // 判断房间号是否存在
            Integer roomNumber = dto.getRoomNumber();
            long count = rooms.stream().filter(e -> e.getRoomNumber().equals(roomNumber)).count();
            if (count == 0) throw new BusinessException("该房间不存在");
            if (!availableRoom.containsKey(dto.getDate())) {
                availableRoom.put(dto.getDate(), rooms);
            }
            // 判断今天该房间是否可以预订
            List<Room> roomsInToday = availableRoom.get(dto.getDate());
            Set<Integer> availableRoomInToday = roomsInToday
                    .stream()
                    .map(Room::getRoomNumber)
                    .filter(ele -> ele.equals(roomNumber))
                    .collect(Collectors.toSet());

            if (!availableRoomInToday.contains(roomNumber)) throw new BusinessException("该房间已被人预订");

            // 判断是否已经定过房间
            long alreadyBookCount = bookingRecordList.stream()
                    .filter(r -> r.getDate().equals(dto.getDate()) && r.getGuestName().equals(dto.getGuestName()))
                    .count();
            if (alreadyBookCount == 0) {
                BookingRecord record = new BookingRecord();
                record.setGuestName(dto.getGuestName());
                record.setRoomNumber(roomNumber);
                record.setDate(dto.getDate());
                bookingRecordList.add(record);
                //移除可订房间
                List<Room> available = availableRoom.get(dto.getDate())
                        .stream()
                        .filter(r -> !r.getRoomNumber().equals(roomNumber))
                        .collect(Collectors.toList());
                availableRoom.put(dto.getDate(), available);
                return true;
            } else {
                throw new BusinessException("已经预订过房间了");
            }
        }
    }

    /**
     * 查询给定日期下所有可预订房价
     *
     * @param date 入住日期
     * @return 返回可预订房间列表
     */
    public List<RoomDTO> getAvailableRoomByDate(String date) {
        List<RoomDTO> result = new ArrayList<>();
        if (availableRoom.containsKey(date)) {
            List<Room> rooms = availableRoom.get(date);
            rooms.forEach(ele -> {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoomNumber(ele.getRoomNumber());
                result.add(roomDTO);
            });
        } else {
            rooms.forEach(ele -> {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoomNumber(ele.getRoomNumber());
                result.add(roomDTO);
            });
        }
        return result;
    }

    /**
     * 根据客户名查看其所有订购记录
     *
     * @param guestName 客户名
     * @return 返回订购记录
     */
    public GuestBookingsRespDTO findBookingRecordByGuestName(String guestName) {
        List<BookingRecord> result = bookingRecordList.stream().filter(ele -> ele.getGuestName().equals(guestName)).collect(Collectors.toList());

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
