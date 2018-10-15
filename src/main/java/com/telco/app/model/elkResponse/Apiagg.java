
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "doc_count_error_upper_bound",
    "sum_other_doc_count",
    "buckets"
})
public class Apiagg {

    @JsonProperty("doc_count_error_upper_bound")
    private Integer docCountErrorUpperBound;
    @JsonProperty("sum_other_doc_count")
    private Integer sumOtherDocCount;
    @JsonProperty("buckets")
    private List<APIBucket> buckets = null;

    @JsonProperty("doc_count_error_upper_bound")
    public Integer getDocCountErrorUpperBound() {
        return docCountErrorUpperBound;
    }

    @JsonProperty("doc_count_error_upper_bound")
    public void setDocCountErrorUpperBound(Integer docCountErrorUpperBound) {
        this.docCountErrorUpperBound = docCountErrorUpperBound;
    }

    @JsonProperty("sum_other_doc_count")
    public Integer getSumOtherDocCount() {
        return sumOtherDocCount;
    }

    @JsonProperty("sum_other_doc_count")
    public void setSumOtherDocCount(Integer sumOtherDocCount) {
        this.sumOtherDocCount = sumOtherDocCount;
    }

    @JsonProperty("buckets")
    public List<APIBucket> getBuckets() {
        return buckets;
    }

    @JsonProperty("buckets")
    public void setBuckets(List<APIBucket> buckets) {
        this.buckets = buckets;
    }

}
