package com.telco.app.model.hourlyReport;

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
public class ServiceProvider {

    private String name;
    private List<Api> apis;
    private double totalSPTransactionsDay;
    int[] totalOfInterval;

    public ServiceProvider(int arraySize){
        totalOfInterval = new int[arraySize];
    }

    public int[] getTotalOfInterval() {
        return totalOfInterval;
    }

    public void setTotalOfInterval(int element, int value) {
        this.totalOfInterval[element] += value;
    }

    public double getTotalSPTransactionsDay() {
        return totalSPTransactionsDay;
    }

    public void setTotalSPTransactionsDay(double totalSPTransactionsDay) {
        this.totalSPTransactionsDay = totalSPTransactionsDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Api> getApis() {
        return apis;
    }

    public void setApis(List<Api> apis) {
        this.apis = apis;
    }
}
