package com.atyukavkin.football;

import com.atyukavkin.football.dao.MonitorDao;
import com.atyukavkin.football.model.monitoring.MonitorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Andrey Tyukavkin.
 */
@RestController
public class MonitorController {

    @Autowired
    private MonitorDao monitorDao;

    @RequestMapping("/monitoring-data")
    public String getMonitoringData(@RequestParam String format, HttpServletResponse response) throws IOException {
        List<MonitorData> monitoring = monitorDao.findAll();
        String jsonOutput = new ObjectMapper().writeValueAsString(monitoring);
        if (format != null && "csv".equals(format)) {
            response.setContentType("text/plain");
            response.addHeader("Content-Disposition", "attachment; filename=monitoring_data_" + System.currentTimeMillis() + ".csv");
            String toString = CDL.toString(new JSONArray(jsonOutput));
            InputStream inputStream = new ByteArrayInputStream(toString.getBytes());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.getOutputStream().flush();
            return "";
        } else {
            return jsonOutput;
        }
    }
}
