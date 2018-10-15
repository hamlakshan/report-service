package com.telco.app.service;


import com.telco.app.core.CSVFileWriter;
import com.telco.app.core.ElasticsearchClient;
import com.telco.app.core.ReportGenerator;
import com.telco.app.core.ReportPrinter;
import com.telco.app.model.RequestParam;
import com.telco.app.model.hourlyReport.ApiUsageReport;
import com.telco.app.model.hourlyReport.HourlyAPIUsageReport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reports")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReportService {

    @POST
    @Path("/dailyAPIUsage")
    public Response getDailyAPIUsage(@HeaderParam("authorization") String authHeader, RequestParam requestParam) {

        ElasticsearchClient client = new ElasticsearchClient();
        ReportGenerator reportGenerator = new ReportGenerator();
        ReportPrinter reportPrinter = new ReportPrinter();
        CSVFileWriter csvFileWriter = new CSVFileWriter();

        //        ApiUsageReport apiUsageReport = reportGenerator.generateHourlySPWiseApiUsageReport(client.getApiUsage(beginOfDay,endOfDay,timeZone, "1h").getAggregations(), 1538936100000L);
        //reportPrinter.printHourlyApiUsageReport(apiUsageReport);
        //reportPrinter.generateJSONString(apiUsageReport);
        ApiUsageReport apiUsageReport = reportGenerator.generateDailySPWiseApiUsageReport(client.getApiUsage(requestParam.getBeginOfDay(),requestParam.getEndOfDay(),requestParam.getTimeZone(), "1h").getAggregations(), requestParam.getBeginOfDay());


        client.createIndex();
        client.saveUsageData(apiUsageReport);
        //csvFileWriter.writeHourlyApiUsageToCSV(apiUsageReport);


        Response response;
        response = Response.status(Response.Status.OK).entity("").build();
        return response;
    }

    @POST
    @Path("/hourlyapiusage")
    public Response getHourlyAPIUsage(@HeaderParam("authorization") String authHeader, RequestParam requestParam) {

        ElasticsearchClient client = new ElasticsearchClient();
        ReportGenerator reportGenerator = new ReportGenerator();
        CSVFileWriter csvFileWriter = new CSVFileWriter();
        HourlyAPIUsageReport apiUsageReport = reportGenerator.generateHourlyApiUsageReport(client.getHourlyAPIUsage(requestParam.getBeginOfDay(),requestParam.getEndOfDay(),requestParam.getTimeZone(), "1h").getAggregations(), requestParam.getBeginOfDay());
        csvFileWriter.writeHourlyApiUsageToCSV(apiUsageReport);
        Response response;
        response = Response.status(Response.Status.OK).entity("").build();
        return response;
    }

    @GET
    @Path("/simple")
    public Response getDailyAPIUsage() {
        Response response;
        response = Response.status(Response.Status.OK).entity("{ \"name\":\"manoj\"}").build();
        return response;
    }
}
