
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "buckets"
})
public class Dateagg {

    @JsonProperty("buckets")
    private List<TransactionBucket> buckets = null;

    @JsonProperty("buckets")
    public List<TransactionBucket> getBuckets() {
        return buckets;
    }

    @JsonProperty("buckets")
    public void setBuckets(List<TransactionBucket> buckets) {
        this.buckets = buckets;
    }
}
