
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiagg"
})
public class HourlyAPIUsageAggregations {

    @JsonProperty("apiagg")
    private Apiagg apiagg;

    @JsonProperty("apiagg")
    public Apiagg getApiagg() {
        return apiagg;
    }

    @JsonProperty("apiagg")
    public void setApiagg(Apiagg apiagg) {
        this.apiagg = apiagg;
    }
}
