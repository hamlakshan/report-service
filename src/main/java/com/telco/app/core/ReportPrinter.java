package com.telco.app.core;

import com.telco.app.model.hourlyReport.Api;
import com.telco.app.model.hourlyReport.ApiUsageReport;
import com.telco.app.model.hourlyReport.ServiceProvider;
import com.telco.app.model.hourlyReport.Transaction;

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
public class ReportPrinter {

    public void printHourlyApiUsageReport(ApiUsageReport apiUsageReport) {

        System.out.println("The report");

        double wholeTotal = 0;
        int[] columnTotal = new int[24];

        System.out.println("\"ServiceProvider\",\"API\",1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,\"Total Result\"");

        for (ServiceProvider serviceProvider : apiUsageReport.getServiceProviders()) {
            System.out.print("\"" + serviceProvider.getName() + "\",");
            for (int k = 0; k < 24; k++) {
                System.out.print("," + serviceProvider.getTotalOfInterval()[k]);
                columnTotal[k] += serviceProvider.getTotalOfInterval()[k];
            }
            System.out.print("," + serviceProvider.getTotalSPTransactionsDay() + "\n");
            for (Api api : serviceProvider.getApis()) {
                System.out.print(",\"" + api.getName() + "\"");
                for (Transaction transaction : api.getTransactions()) {
                    System.out.print("," + transaction.getValue());
                }
                System.out.print("," + api.getTotalAPITransactionsDay() + "\n");
                wholeTotal += api.getTotalAPITransactionsDay();
            }
        }
        System.out.print("\"Total Result\",,");

        for (int value : columnTotal) {
            System.out.print(value + ",");
        }
        System.out.print(wholeTotal);

//        for(ServiceProvider serviceProvider: apiUsageReport.getServiceProviders()){
//            System.out.println("--"+serviceProvider.getName()+"--");
//            for (Api api: serviceProvider.getApis()){
//                System.out.print("\n" + api.getName() + ": ");
//                for (Transaction transaction: api.getTransactions()){
//                    System.out.print("," + transaction.getValue());
//                }
//            }
//            System.out.println("\n --------------------------------");
//        }
    }


    public String generateJSONString(ApiUsageReport apiUsageReport) {


        StringBuilder request = new StringBuilder();
        for (ServiceProvider serviceProvider : apiUsageReport.getServiceProviders()) {
            for (Api api : serviceProvider.getApis()) {
                request.append("{ \"index\":{} }\n{\"date\":" + System.currentTimeMillis() + ",");
                request.append("\"username\":\"" + serviceProvider.getName() + "\",");
                request.append("\"api\":\"" + api.getName() + "\"");
                for (int i = 0; i < 24; i++) {
                    request.append(",\"" + (i + 1) + "\":" + api.getTransactions().get(i).getValue());
                }
                request.append("}\n");

//                System.out.print(request.toString());
//                for (Transaction transaction: api.getTransactions()){
//                    request.append("," + transaction.getValue());
//                }
            }

        }

        return request.toString();

    }
}
