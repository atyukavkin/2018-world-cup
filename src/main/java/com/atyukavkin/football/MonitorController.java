package com.atyukavkin.football;

import com.atyukavkin.football.dao.MonitorDao;
import com.atyukavkin.football.model.monitoring.MonitorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value = "/monitoring")
@Api(value = "Monitoring", description = "Inspect monitoring data")
public class MonitorController {

    @Autowired
    private MonitorDao monitorDao;

    @ApiOperation(value = "Print or download monitoring data", response = MonitorData.class, responseContainer = "List")
    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json")
    public String getMonitoringData(@RequestParam(required = false) String format, HttpServletResponse response) throws IOException {
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
