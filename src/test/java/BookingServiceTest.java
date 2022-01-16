import dto.AddBookingDTO;
import exception.BusinessException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class BookingServiceTest {

    private BookingService service = new BookingService();

    @Before
    public void init() throws BusinessException {
        service.initRoomNumber(100);
    }

    @Test
    public void storeBooking() throws BusinessException {
        AddBookingDTO personA = new AddBookingDTO();
        personA.setGuestName("A");
        personA.setRoomNumber(30);
        personA.setDate("2022-01-01");
        service.storeBooking(personA);
        AddBookingDTO personB = new AddBookingDTO();
        personB.setGuestName("d");
        personB.setRoomNumber(32);
        personB.setDate("2022-01-01");
        service.storeBooking(personB);
        System.out.println(service.findBookingRecordByGuestName("A").getBookingRecordDtoList().size());
        System.out.println(service.findBookingRecordByGuestName("C").getBookingRecordDtoList().isEmpty());
    }

    @Test
    public void getAvailableRoomByDate() {
    }

    @Test
    public void findBookingRecordByGuestName() {
    }


    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}