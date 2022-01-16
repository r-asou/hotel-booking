import com.solution.dto.AddBookingDTO;
import com.solution.dto.BookingRecordDTO;
import com.solution.dto.GuestBookingsRespDTO;
import com.solution.dto.RoomDTO;
import com.solution.entity.BookingRecord;
import com.solution.entity.Room;
import com.solution.exception.BusinessException;
import com.solution.service.BookingService;
import com.solution.service.RoomService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class BookingServiceTest {

    private RoomService roomService;
    private BookingService bookingService;


    @Before
    public void init() {
        roomService = new RoomService();
        bookingService = new BookingService(roomService);
        Room.configureRoomNumber("2021-01-01", 100); // 初始化房间100
        Room.configureRoomNumber("2021-01-02", 50); // 初始化房间100
        Room.configureRoomNumber("2021-01-03", 10); // 初始化房间10
    }

    @After
    public void destroy() {
        Room.getAvailableRoom().clear();
        BookingRecord.getBookingRecordList().clear();
        roomService = null;
        bookingService = null;
    }

    @Test
    public void testStoreBooking() throws BusinessException {
        AddBookingDTO guestA = new AddBookingDTO();
        guestA.setGuestName("GuestA");
        guestA.setRoomNumber(30);
        guestA.setDate("2021-01-01");
        bookingService.storeBooking(guestA);
        // guestA成功预订1个房间
        List<BookingRecordDTO> bookedByGuestA = bookingService.findBookingRecordByGuestName("GuestA").getBookingRecordDtoList();
        Assert.assertEquals(1, bookedByGuestA.size());
        Assert.assertEquals(guestA.getDate(), bookedByGuestA.get(0).getDate());
        Assert.assertEquals(guestA.getRoomNumber(), bookedByGuestA.get(0).getRoomNumber());
        Assert.assertEquals(guestA.getGuestName(), bookedByGuestA.get(0).getGuestName());
    }

    @Test
    public void testGetAvailableRoomByDate() {
        List<RoomDTO> result = roomService.getAvailableRoomByDate("2021-01-01");
        Assert.assertEquals(result.size(), 100);
        result = roomService.getAvailableRoomByDate("2021-01-02");
        Assert.assertEquals(result.size(), 50);
        result = roomService.getAvailableRoomByDate("2021-01-03");
        Assert.assertEquals(result.size(), 10);
        result = roomService.getAvailableRoomByDate("2021-01-09");
        Assert.assertEquals(result.size(), 0);
    }


    @Test
    public void testFindBookingRecordByGuestName() throws BusinessException {
        GuestBookingsRespDTO guest = bookingService.findBookingRecordByGuestName("GuestA");
        Assert.assertTrue(guest.getBookingRecordDtoList().isEmpty());

        AddBookingDTO guestA = new AddBookingDTO();
        guestA.setGuestName("GuestA");
        guestA.setRoomNumber(30);
        guestA.setDate("2021-01-01");
        bookingService.storeBooking(guestA);

        guestA.setRoomNumber(10);
        guestA.setDate("2021-01-02");
        bookingService.storeBooking(guestA);
        GuestBookingsRespDTO result = bookingService.findBookingRecordByGuestName("GuestA");
        Assert.assertEquals(result.getBookingRecordDtoList().size(), 2);
    }

    @Test
    public void testRepeatedBooking() throws BusinessException {
        AddBookingDTO guestA = new AddBookingDTO();
        guestA.setGuestName("GuestA");
        guestA.setRoomNumber(30);
        guestA.setDate("2021-01-01");
        boolean bookingResult = bookingService.storeBooking(guestA);
        Assert.assertTrue(bookingResult);
        guestA.setRoomNumber(10);
        guestA.setDate("2021-01-01");
        Assert.assertThrows(BusinessException.class, () -> bookingService.storeBooking(guestA));
    }

    @Test
    public void testNotExistedBookingRecord() {
        Assert.assertTrue(bookingService.findBookingRecordByGuestName("GuestNotExisted").getBookingRecordDtoList().isEmpty());
    }

    @Test
    public void testBookingSameRoomByDifferentGuest() throws BusinessException {
        AddBookingDTO guestA = new AddBookingDTO();
        guestA.setGuestName("GuestA");
        guestA.setRoomNumber(30);
        guestA.setDate("2021-01-01");
        boolean bookingResult = bookingService.storeBooking(guestA);
        Assert.assertTrue(bookingResult);
        AddBookingDTO guestB = new AddBookingDTO();
        guestB.setGuestName("GuestB");
        guestB.setRoomNumber(30);
        guestB.setDate("2021-01-01");
        Assert.assertThrows(BusinessException.class, () -> bookingService.storeBooking(guestB));
    }

    @Test
    public void testMultiThread() throws BusinessException, InterruptedException {
        AddBookingDTO guestA = random();
        AddBookingDTO guestB = random();
        AddBookingDTO guestC = random();
        AddBookingDTO guestD = random();

        Thread t1 = new Thread(() -> {
            boolean result = false;
            try {
                result = bookingService.storeBooking(guestA);
            } catch (BusinessException e) {
                System.out.println(e);
            }
            Assert.assertTrue(result);
        });
        Thread t2 = new Thread(() -> {
            boolean result = false;
            try {
                result = bookingService.storeBooking(guestB);
            } catch (BusinessException e) {
                System.out.println(e);
            }
            Assert.assertTrue(result);
        });
        Thread t3 = new Thread(() -> {
            boolean result = false;
            try {
                result = bookingService.storeBooking(guestC);
            } catch (BusinessException e) {
                System.out.println(e);
            }
            Assert.assertTrue(result);
        });
        Thread t4 = new Thread(() -> {
            boolean result = false;
            try {
                result = bookingService.storeBooking(guestD);
            } catch (BusinessException e) {
                System.out.println(e);
            }
            Assert.assertTrue(result);
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    public AddBookingDTO random() {
        AddBookingDTO guest = new AddBookingDTO();
        guest.setGuestName(UUID.randomUUID().toString());
        guest.setRoomNumber(30);
        guest.setDate("2021-01-01");
        return guest;
    }

}