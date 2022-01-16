//import dto.AddBookingDTO;
//import dto.GuestBookingsRespDTO;
//import exception.BusinessException;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author JC Liu
// * @date 2022/1/16$
// */
//public class FunctionTest {
//
//    private static final BookingService service = new BookingService();
//
//    static {
//        try {
//            service.initRoomNumber(100);
//        } catch (BusinessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws BusinessException, ParseException {
//        FunctionTest test = new FunctionTest();
//
//        AddBookingDTO addBookingDTO = test.randomBookingDto();
//        for (int i = 0; i < 2; i++) {
//            service.storeBooking(addBookingDTO);
//        }
//
//        GuestBookingsRespDTO result = service.findBookingRecordByGuestName("Customer1");
//        System.out.println(result.getBookingRecordDtoList().size());
//    }
//
//    public AddBookingDTO randomBookingDto() throws ParseException {
//        AddBookingDTO addBookingDTO = new AddBookingDTO();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date start = format.parse("2020-01-01");// 构造开始日期
//        Date end = format.parse("2022-10-30");// 构造结束日期
//        long date = random(start.getTime(), end.getTime());
//        String format1 = format.format(date);
//        System.out.println(format1);
//        addBookingDTO.setDate(format1);
//        addBookingDTO.setGuestName("Customer1");
//        addBookingDTO.setRoomNumber(1+(int)(Math.random()*100));
//        return addBookingDTO;
//    }
//
//    private static long random(long begin, long end) {
//        long rtn = begin + (long) (Math.random() * (end - begin));
//        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
//        if (rtn == begin || rtn == end) {
//            return random(begin, end);
//        }
//        return rtn;
//    }
//}
