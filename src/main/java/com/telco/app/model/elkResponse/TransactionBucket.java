
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key_as_string",
    "key",
    "doc_count"
})
public class TransactionBucket {

    @JsonProperty("key_as_string")
    private String keyAsString;
    @JsonProperty("key")
    private Double key;
    @JsonProperty("doc_count")
    private Integer docCount;

    @JsonProperty("key_as_string")
    public String getKeyAsString() {
        return keyAsString;
    }

    @JsonProperty("key_as_string")
    public void setKeyAsString(String keyAsString) {
        this.keyAsString = keyAsString;
    }

    @JsonProperty("key")
    public Double getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(Double key) {
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
}
