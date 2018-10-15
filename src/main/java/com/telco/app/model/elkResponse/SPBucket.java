
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "doc_count",
    "apiagg"
})
public class SPBucket {

    @JsonProperty("key")
    private String key;
    @JsonProperty("doc_count")
    private Integer docCount;
    @JsonProperty("apiagg")
    private Apiagg apiagg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("apiagg")
    public Apiagg getApiagg() {
        return apiagg;
    }

    @JsonProperty("apiagg")
    public void setApiagg(Apiagg apiagg) {
        this.apiagg = apiagg;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
