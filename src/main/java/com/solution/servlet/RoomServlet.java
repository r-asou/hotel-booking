package com.solution.servlet;

import com.solution.database.InMemoryDatabase;
import com.solution.entity.Room;

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
public class RoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String date = req.getParameter("date");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        List<Room> rooms = InMemoryDatabase.getAvailableRoom().get(date);
        writer.write(date + "可预订房间列表：");
        writer.write("<br/>");
        if (rooms != null) {
            rooms.forEach(room -> {
                writer.write("<li>" + room.getRoomNumber() + "</li>");
            });
        } else {
            writer.write("不存在可预订房间");
        }
    }
}
