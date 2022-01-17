package com.solution;

import com.solution.service.BookingService;
import com.solution.service.RoomService;
import com.solution.servlet.BookingRecordServlet;
import com.solution.servlet.BookingServlet;
import com.solution.servlet.ConfigureServlet;
import com.solution.servlet.RoomServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author JC Liu
 * @date 2022/1/17
 */
public class Application {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        BookingService bookingService = new BookingService(new RoomService());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // 配置每日的房间数
        context.addServlet(new ServletHolder(new ConfigureServlet()), "/room-configure");
        // 获取指定日期可预订的房间号码
        context.addServlet(new ServletHolder(new RoomServlet()), "/room/available-room-by-date");
        // 根据客户名获取相关订房记录
        context.addServlet(new ServletHolder(new BookingRecordServlet(bookingService)), "/book-record");
        // 预订房间
        context.addServlet(new ServletHolder(new BookingServlet(bookingService)), "/book-room");

        server.start();
        server.join();
    }
}
