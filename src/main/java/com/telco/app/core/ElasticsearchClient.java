package com.telco.app.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telco.app.model.elkResponse.APIUsage;
import com.telco.app.model.elkResponse.HourlyAPIUsage;
import com.telco.app.model.hourlyReport.ApiUsageReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.telco.app.util.Variables.*;

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

public class ElasticsearchClient {

    private HttpClient client;
    private ObjectMapper mapper;
    private final Log log = LogFactory.getLog(ElasticsearchClient.class);

    public ElasticsearchClient() {
        this.client = HttpClientBuilder.create().build();
        this.mapper = new ObjectMapper();
    }

    public void executeGet() {
        HttpGet httpGet = new HttpGet("http://localhost:9200");

        try {

            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("response is " + EntityUtils.toString(response.getEntity()));
            } else {
                log.error(response.getStatusLine().getStatusCode() + " Error loading data");

            }
        } catch (IOException e) {
            log.error("Exception while calling get " + e);
        }
    }

    public APIUsage getApiUsage(double beginOfDay, double endOfDay, String timeZone, String interval) {

        String request = "{ \"size\": 0, \"_source\": { \"excludes\": [] }, \"aggs\": { \"usenameagg\": { \"terms\": { \"field\": \"username.keyword\", \"size\": 100, \"order\": { \"_count\": \"desc\" } }, \"aggs\": { \"apiagg\": { \"terms\": { \"field\": \"api.keyword\", \"size\": 100, \"order\": { \"_count\": \"desc\" } }, \"aggs\": { \"dateagg\": { \"date_histogram\": { \"field\": \"@timestamp\", \"interval\": \"" + interval + "\", \"time_zone\": \"" + timeZone + "\", \"min_doc_count\": 0 } } } } } } }, \"stored_fields\": [ \"*\" ], \"script_fields\": {}, \"docvalue_fields\": [ \"@timestamp\" ], \"query\": { \"bool\": { \"must\": [{ \"match_all\": {} }, { \"range\": { \"@timestamp\": { \"gte\": " + beginOfDay + ", \"lte\": " + endOfDay + ", \"format\": \"epoch_millis\" } } } ], \"filter\": [], \"should\": [], \"must_not\": [] } } }";

        HttpPost httpPost = new HttpPost(new StringBuilder(ELKIP.getValue()).append(REQUEST_INDEX.getValue()).append(LATTER.getValue()).toString());
        /** add headers */
        httpPost.setHeader("Content-Type", "application/json");

        try {
            /** set request body */
            httpPost.setEntity(new StringEntity(request));

            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                APIUsage apiUsage = mapper.readValue(response.getEntity().getContent(), APIUsage.class);
                return apiUsage;
            } else {
                log.error(response.getStatusLine().getStatusCode() + "Error");
            }
        } catch (IOException e) {
            log.error("exception while executing the get hourly api usage post request " + e);
        }

        return null;
    }

    public void createIndex() {

//        curl -X PUT "localhost:9200/customer?pretty"

        String index = "apiusage";
        String request = "{ \"mappings\": { \"_doc\": { \"properties\": { \"date\": { \"type\": \"date\", \"format\": \"epoch_millis\" } } } } }";

        HttpPut httpPut = new HttpPut(new StringBuilder(LOCALELKIP.getValue()).append(index).append("?pretty").toString());
        httpPut.setHeader("Content-Type", "application/json");
        try {
            httpPut.setEntity(new StringEntity(request));
            HttpResponse response = client.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("new index created successfully");
            } else {
                log.error("error while creating new index");
            }
        } catch (IOException e) {
            log.error("Exception while calling put " + e);
        }
    }

    public void saveUsageData(ApiUsageReport apiUsageReport) {

//        curl -X PUT "localhost:9200/customer?pretty"

        String index = "apiusage";
        String request = new ReportPrinter().generateJSONString(apiUsageReport);

        HttpPost httpPost = new HttpPost(new StringBuilder(LOCALELKIP.getValue()).append(index).append("/_doc/_bulk?pretty").toString());
        /** add headers */
        httpPost.setHeader("Content-Type", "application/json");

        try {
            /** set request body */
            httpPost.setEntity(new StringEntity(request));

            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("new data added successfully");
            } else {
                log.error(response.getStatusLine().getStatusCode() + "error while adding data to index");
            }
        } catch (IOException e) {
            log.error("Exception while calling post request" + e);
        }
    }


    public HourlyAPIUsage getHourlyAPIUsage(double beginOfDay, double endOfDay, String timeZone, String interval) {

        String request = "{ \"size\": 0, \"_source\": { \"excludes\": [] }, \"aggs\": { \"apiagg\": { \"terms\": { \"field\": \"api.keyword\", \"size\": 20, \"order\": { \"_count\": \"desc\" } }, \"aggs\": { \"dateagg\": { \"date_histogram\": { \"field\": \"@timestamp\", \"interval\": \""+ interval+ "\", \"time_zone\": \"" + timeZone + "\", \"min_doc_count\": 0 } } } } }, \"stored_fields\": [ \"*\" ], \"script_fields\": {}, \"docvalue_fields\": [ \"@timestamp\" ], \"query\": { \"bool\": { \"must\": [ { \"match_all\": {} }, { \"range\": { \"@timestamp\": { \"gte\": "+ beginOfDay+ ", \"lte\": "+ endOfDay+ ", \"format\": \"epoch_millis\" } } } ], \"filter\": [], \"should\": [], \"must_not\": [] } } }";

        HttpPost httpPost = new HttpPost(new StringBuilder(ELKIP.getValue()).append(REQUEST_INDEX.getValue()).append(LATTER.getValue()).toString());
        /** add headers */
        httpPost.setHeader("Content-Type", "application/json");

        try {
            /** set request body */
            httpPost.setEntity(new StringEntity(request));

            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HourlyAPIUsage apiUsage = mapper.readValue(response.getEntity().getContent(), HourlyAPIUsage.class);
                return apiUsage;
            } else {
                log.error(response.getStatusLine().getStatusCode() + "error while adding data to index");
            }
        } catch (IOException e) {
            log.error("exception while executing the get hourly api usage post request " + e);
        }

        return null;
    }
}

