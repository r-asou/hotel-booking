package com.solution.servlet;

import com.solution.dto.BookingRecordDTO;
import com.solution.exception.BusinessException;
import com.solution.service.BookingService;
import com.solution.utils.ConvertUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author JC Liu
 * @date 2022/1/17
 */
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService;

    public BookingServlet(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * 新增预订记录
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String line;
        BookingRecordDTO dto = new BookingRecordDTO();
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("{") && !line.startsWith("}")) {
                ConvertUtils.recordConverter.accept(dto, line.trim());
            }
        }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        try {
            boolean flag = bookingService.storeBooking(dto);
            if (flag) {

                writer.write("<p>" + dto.getGuestName() + "已成功预订" + dto.getDate() + "的房间，房号为" + dto.getRoomNumber() + "</p>");
            } else {
                writer.write("该房间当前不可预订");
            }
        } catch (BusinessException e) {
            writer.write("<p>" + e.getCause().getMessage() + "</p>");
        }
    }
}
