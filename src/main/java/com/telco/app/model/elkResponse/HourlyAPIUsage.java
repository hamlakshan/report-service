
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.telco.app.model.elkResponse.common.Hits;
import com.telco.app.model.elkResponse.common.Shards;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "took",
    "timed_out",
    "_shards",
    "hits",
    "aggregations"
})
public class HourlyAPIUsage {

    @JsonProperty("took")
    private Integer took;
    @JsonProperty("timed_out")
    private Boolean timedOut;
    @JsonProperty("_shards")
    private Shards shards;
    @JsonProperty("hits")
    private Hits hits;
    @JsonProperty("aggregations")
    private HourlyAPIUsageAggregations aggregations;

    @JsonProperty("took")
    public Integer getTook() {
        return took;
    }

    @JsonProperty("took")
    public void setTook(Integer took) {
        this.took = took;
    }

    @JsonProperty("timed_out")
    public Boolean getTimedOut() {
        return timedOut;
    }

    @JsonProperty("timed_out")
    public void setTimedOut(Boolean timedOut) {
        this.timedOut = timedOut;
    }

    @JsonProperty("_shards")
    public Shards getShards() {
        return shards;
    }

    @JsonProperty("_shards")
    public void setShards(Shards shards) {
        this.shards = shards;
    }

    @JsonProperty("hits")
    public Hits getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(Hits hits) {
        this.hits = hits;
    }

    @JsonProperty("aggregations")
    public HourlyAPIUsageAggregations getAggregations() {
        return aggregations;
    }

    @JsonProperty("aggregations")
    public void setAggregations(HourlyAPIUsageAggregations aggregations) {
        this.aggregations = aggregations;
    }
}
