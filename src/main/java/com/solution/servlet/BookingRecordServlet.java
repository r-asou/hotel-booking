package com.solution.servlet;

import com.solution.dto.BookingRecordDTO;
import com.solution.dto.GuestBookingsRespDTO;
import com.solution.service.BookingService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author JC Liu
 * @date 2022/1/17
 */
public class BookingRecordServlet extends HttpServlet {

    private final BookingService bookingService;

    public BookingRecordServlet(BookingService service) {
        this.bookingService = service;
    }

    /**
     * 查询指定客户的订房记录
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        GuestBookingsRespDTO bookingRecordByGuestName = bookingService.findBookingRecordByGuestName(name);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(name + "的订房记录: ");
        List<BookingRecordDTO> bookingRecordDtoList = bookingRecordByGuestName.getBookingRecordDtoList();
        writer.write("<table border=\"1\">");
        writer.write("<tr>\n" +
                "    <th>订房日期</th>\n" +
                "    <th>房间号</th>\n" +
                "  </tr>");
        if (bookingRecordDtoList.size() != 0) {
            bookingRecordDtoList.forEach(record -> writer.write("<tr><td>" + record.getDate() + "</td><td>" + record.getRoomNumber() + "</td></tr>"));
        }
        writer.write("</table>");


    }
}
