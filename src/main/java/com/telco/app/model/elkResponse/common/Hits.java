
package com.telco.app.model.elkResponse.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "max_score",
    "hits"
})
public class Hits {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("max_score")
    private Double maxScore;
    @JsonProperty("hits")
    private List<Object> hits = null;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("max_score")
    public Double getMaxScore() {
        return maxScore;
    }

    @JsonProperty("max_score")
    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    @JsonProperty("hits")
    public List<Object> getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(List<Object> hits) {
        this.hits = hits;
    }

}
