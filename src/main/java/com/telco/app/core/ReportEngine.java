package com.telco.app.core;

import com.telco.app.model.elkResponse.Aggregations;
import com.telco.app.model.hourlyReport.ApiUsageReport;

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
public abstract class ReportEngine {

    public abstract ApiUsageReport generateHourlyApiUsageReport(Aggregations aggregations, double startTime);

    public abstract ApiUsageReport generateDailyApiUsageReport(Aggregations aggregations, double startTime);

}
