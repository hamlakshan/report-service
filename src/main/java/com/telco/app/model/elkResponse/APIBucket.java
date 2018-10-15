
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "doc_count",
    "dateagg"
})
public class APIBucket {

    @JsonProperty("key")
    private String key;
    @JsonProperty("doc_count")
    private Integer docCount;
    @JsonProperty("dateagg")
    private Dateagg dateagg;

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("doc_count")
    public Integer getDocCount() {
        return docCount;
    }

    @JsonProperty("doc_count")
    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }

    @JsonProperty("dateagg")
    public Dateagg getDateagg() {
        return dateagg;
    }

    @JsonProperty("dateagg")
    public void setDateagg(Dateagg dateagg) {
        this.dateagg = dateagg;
    }

}
