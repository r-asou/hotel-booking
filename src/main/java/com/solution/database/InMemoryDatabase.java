package com.solution.database;

import com.solution.entity.BookingRecord;
import com.solution.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JC Liu
 * @date 2022/1/17
 */
public class InMemoryDatabase {
    // 只保存每天可定房间，实际只操作availableRoom容器，可以根据要求进行实时更新
    private static final Map<String, List<Room>> availableRoom = new ConcurrentHashMap<>();
    // 保存预订记录
    private static volatile List<BookingRecord> bookingRecordList = new ArrayList<>();

    /**
     * 配置指定日期的房间数量
     *
     * @param number 初始化房间数量
     */
    public static void configureRoomNumber(String date, Integer number) {
        List<Room> list = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            Room room = new Room(i);
            list.add(room);
        }
        availableRoom.put(date, list);
    }

    public static Map<String, List<Room>> getAvailableRoom() {
        return availableRoom;
    }

    public static List<BookingRecord> getBookingRecordList() {
        return bookingRecordList;
    }
}
