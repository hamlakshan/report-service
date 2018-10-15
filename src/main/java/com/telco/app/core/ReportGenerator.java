package com.telco.app.core;

import com.telco.app.model.elkResponse.*;
import com.telco.app.model.hourlyReport.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
public class ReportGenerator extends ReportEngine {

    public ApiUsageReport generateHourlySPWiseApiUsageReport(Aggregations aggregations, double startTime) {

        ApiUsageReport apiUsageReport = new ApiUsageReport();
        List<SPBucket> buckets = aggregations.getUsenameagg().getBuckets();
        List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();

        for (SPBucket spBucket : buckets) {
            ServiceProvider serviceProvider = new ServiceProvider(24);
            serviceProvider.setName(spBucket.getKey());
            serviceProvider.setTotalSPTransactionsDay(spBucket.getDocCount());
            List<Api> apis = new ArrayList<Api>();
            for (APIBucket apiBucket : spBucket.getApiagg().getBuckets()) {
                Api api = new Api();
                api.setName(apiBucket.getKey());
                api.setTotalAPITransactionsDay(apiBucket.getDocCount());
                List<Transaction> transactions = new ArrayList<Transaction>();
                int count = 0;
                double tempStartTime = startTime;
                TransactionBucket transactionBucket = apiBucket.getDateagg().getBuckets().get(count);
                for (int i = 0; i < 24; i++) {
                    Transaction transaction = new Transaction();
                    if (transactionBucket.getKey() == tempStartTime) {
                        transaction.setValue(transactionBucket.getDocCount());
                        transaction.setDateInEpochMilliseconds(transactionBucket.getKey());
                        serviceProvider.setTotalOfInterval(i,transactionBucket.getDocCount());
                        count++;
                    } else {
                        transaction.setValue(0);
                        transaction.setDateInEpochMilliseconds(tempStartTime);
                        serviceProvider.setTotalOfInterval(i,0);
                    }
                    tempStartTime += 3600000;
                    transactions.add(transaction);
                    if (count < apiBucket.getDateagg().getBuckets().size()) {
                        transactionBucket = apiBucket.getDateagg().getBuckets().get(count);
                    }
                }

                api.setTransactions(transactions);
                apis.add(api);
            }
            serviceProvider.setApis(apis);
            serviceProviders.add(serviceProvider);
        }
        apiUsageReport.setServiceProviders(serviceProviders);

        return apiUsageReport;

    }

    public ApiUsageReport generateDailySPWiseApiUsageReport(Aggregations aggregations, double startTime) {

        int datesOfMonth = getDatesOfTheMonth((long)startTime);

        ApiUsageReport apiUsageReport = new ApiUsageReport();
        List<SPBucket> buckets = aggregations.getUsenameagg().getBuckets();
        List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();

        for (SPBucket spBucket : buckets) {
            ServiceProvider serviceProvider = new ServiceProvider(datesOfMonth);
            serviceProvider.setName(spBucket.getKey());
            serviceProvider.setTotalSPTransactionsDay(spBucket.getDocCount());
            List<Api> apis = new ArrayList<Api>();
            for (APIBucket apiBucket : spBucket.getApiagg().getBuckets()) {
                Api api = new Api();
                api.setName(apiBucket.getKey());
                api.setTotalAPITransactionsDay(apiBucket.getDocCount());
                List<Transaction> transactions = new ArrayList<Transaction>();
                int count = 0;
                double tempTime = startTime;
                TransactionBucket transactionBucket = apiBucket.getDateagg().getBuckets().get(count);
                for (int i = 0; i < datesOfMonth; i++) {
                    Transaction transaction = new Transaction();
                    if (transactionBucket.getKey() == tempTime) {
                        transaction.setValue(transactionBucket.getDocCount());
                        transaction.setDateInEpochMilliseconds(transactionBucket.getKey());
                        serviceProvider.setTotalOfInterval(i,transactionBucket.getDocCount());
                        count+=1;
                    } else {
                        transaction.setValue(0);
                        transaction.setDateInEpochMilliseconds(tempTime);
                        serviceProvider.setTotalOfInterval(i,0);
                    }
                    tempTime += 86400000;
                    transactions.add(transaction);
                    if (count < apiBucket.getDateagg().getBuckets().size()) {
                        transactionBucket = apiBucket.getDateagg().getBuckets().get(count);
                    }
                }

                api.setTransactions(transactions);
                apis.add(api);
            }
            serviceProvider.setApis(apis);
            serviceProviders.add(serviceProvider);
        }
        apiUsageReport.setServiceProviders(serviceProviders);

        return apiUsageReport;

    }


    public HourlyAPIUsageReport generateHourlyApiUsageReport(HourlyAPIUsageAggregations aggregations, double startTime) {

        HourlyAPIUsageReport apiUsageReport = new HourlyAPIUsageReport();

            List<Api> apis = new ArrayList<Api>();
            for (APIBucket apiBucket : aggregations.getApiagg().getBuckets()) {
                Api api = new Api();
                api.setName(apiBucket.getKey());
                api.setTotalAPITransactionsDay(apiBucket.getDocCount());
                List<Transaction> transactions = new ArrayList<Transaction>();
                int count = 0;
                double tempStartTime = startTime;
                TransactionBucket transactionBucket = apiBucket.getDateagg().getBuckets().get(count);
                for (int i = 0; i < 24; i++) {
                    Transaction transaction = new Transaction();
                    if (transactionBucket.getKey() == tempStartTime) {
                        transaction.setValue(transactionBucket.getDocCount());
                        transaction.setDateInEpochMilliseconds(transactionBucket.getKey());
                        transaction.setAverage(transactionBucket.getDocCount()/3600);
                        count++;
                    } else {
                        transaction.setValue(0);
                        transaction.setDateInEpochMilliseconds(tempStartTime);
                        transaction.setAverage(0);
                    }
                    tempStartTime += 3600000;
                    transactions.add(transaction);
                    if (count < apiBucket.getDateagg().getBuckets().size()) {
                        transactionBucket = apiBucket.getDateagg().getBuckets().get(count);
                    }
                }

                api.setTransactions(transactions);
                apis.add(api);
            }


        return apiUsageReport;

    }

    private int getDatesOfTheMonth(long millis){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.getActualMaximum(Calendar.DATE);
    }
}
