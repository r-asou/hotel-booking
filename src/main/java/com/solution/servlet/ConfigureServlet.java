package com.solution.servlet;

import com.solution.database.InMemoryDatabase;
import com.solution.utils.ConvertUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JC Liu
 * @date 2022/1/17
 */
public class ConfigureServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ConfigureServlet() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String line;
        Map<String, Integer> map = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("{") && !line.startsWith("}")) {
                String[] roomPerDay = ConvertUtils.dataHandler.apply(line.trim());
                if (roomPerDay.length == 2) {
                    map.put(roomPerDay[0], Integer.valueOf(roomPerDay[1]));
                }
            }
        }
        map.forEach(InMemoryDatabase::configureRoomNumber);
    }
}
