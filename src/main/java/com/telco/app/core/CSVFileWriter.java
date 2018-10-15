package com.telco.app.core;

import com.telco.app.model.hourlyReport.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Copyright (c) 2016, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 * <p>
 * WSO2.Telco Inc. licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CSVFileWriter {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "\"ServiceProvider\",\"API\",";

    public void writeHourlySPWiseApiUsageToCSV(ApiUsageReport apiUsageReport) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("/home/manoj/Desktop/csvreport.csv");

            double wholeTotal = 0;
            int[] columnTotal = new int[24];

            fileWriter.append(generateHeader(24).toString());
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (ServiceProvider serviceProvider : apiUsageReport.getServiceProviders()) {
                fileWriter.append("\"" + serviceProvider.getName() + "\",");
                for (int k = 0; k < 24; k++) {
                    fileWriter.append("," + serviceProvider.getTotalOfInterval()[k]);
                    columnTotal[k] += serviceProvider.getTotalOfInterval()[k];
                }
                fileWriter.append("," + serviceProvider.getTotalSPTransactionsDay() + "\n");
                for (Api api : serviceProvider.getApis()) {
                    fileWriter.append(",\"" + api.getName() + "\"");
                    for (Transaction transaction : api.getTransactions()) {
                        fileWriter.append("," + transaction.getValue());
                    }
                    fileWriter.append("," + api.getTotalAPITransactionsDay() + "\n");
                    wholeTotal += api.getTotalAPITransactionsDay();
                }
            }
            fileWriter.append("\"Total Result\",,");

            for (int value : columnTotal) {
                fileWriter.append(value + ",");
            }
            fileWriter.append(String.valueOf(wholeTotal));


            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void writeDailySPWiseApiUsageToCSV(ApiUsageReport apiUsageReport, double startTime) {

        int noOFDates = getDatesOfTheMonth((long) startTime);
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("/home/manoj/Desktop/csvreport.csv");

            double wholeTotal = 0;
            int[] columnTotal = new int[noOFDates];

            fileWriter.append(generateHeader(noOFDates).toString());
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (ServiceProvider serviceProvider : apiUsageReport.getServiceProviders()) {
                fileWriter.append("\"" + serviceProvider.getName() + "\",");
                for (int k = 0; k < noOFDates; k++) {
                    fileWriter.append("," + serviceProvider.getTotalOfInterval()[k]);
                    columnTotal[k] += serviceProvider.getTotalOfInterval()[k];
                }
                fileWriter.append("," + serviceProvider.getTotalSPTransactionsDay() + "\n");
                for (Api api : serviceProvider.getApis()) {
                    fileWriter.append(" ,\"" + api.getName() + "\"");
                    for (Transaction transaction : api.getTransactions()) {
                        fileWriter.append("," + transaction.getValue());
                    }
                    fileWriter.append("," + api.getTotalAPITransactionsDay() + "\n");
                    wholeTotal += api.getTotalAPITransactionsDay();
                }
            }
            fileWriter.append("\"Total Result\",,");

            for (int value : columnTotal) {
                fileWriter.append(value + ",");
            }
            fileWriter.append(String.valueOf(wholeTotal));


            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void writeHourlyApiUsageToCSV(HourlyAPIUsageReport apiUsageReport) {

        FileWriter fileWriter = null;
        File file;
        String path = "/home/manoj/Desktop/myreport/";

        for (Api api : apiUsageReport.getApis()) {
            try {
                file = new File(path + api.getName() + ".csv");
                file.createNewFile();
                fileWriter = new FileWriter(file);
                fileWriter.append("\"Time Interval\",\"Total Transactions\",\"Average (TPS)\"");
                fileWriter.append(NEW_LINE_SEPARATOR);

                for (Transaction transaction : api.getTransactions()) {
                    fileWriter.append("\""+getTimeInString((long)transaction.getDateInEpochMilliseconds())+" to "+ getTimeInString((long)transaction.getDateInEpochMilliseconds()+3600000) + "\"");
                    fileWriter.append("," + transaction.getValue());
                    fileWriter.append("," + transaction.getAverage());
                }
            } catch (Exception e) {
                System.out.println("Error in CsvFileWriter !!!");
                e.printStackTrace();
            } finally {

                try {
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public int getDatesOfTheMonth(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.getActualMaximum(Calendar.DATE);
    }

    public StringBuilder generateHeader(int size) {

        StringBuilder header = new StringBuilder(FILE_HEADER);
        for (int l = 1; l <= size; l++) {
            header.append(l + ",");
        }
        header.append("\"Total Result\"");
        return header;
    }

    public String getTimeInString(long millis){
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        cal.setTimeInMillis(millis);
        return dateFormat.format(cal.getTime());
    }
}
