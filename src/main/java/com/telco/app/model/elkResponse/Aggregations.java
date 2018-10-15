
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "usenameagg"
})
public class Aggregations {

    @JsonProperty("usenameagg")
    private Usenameagg usenameagg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("usenameagg")
    public Usenameagg getUsenameagg() {
        return usenameagg;
    }

    @JsonProperty("usenameagg")
    public void setUsenameagg(Usenameagg usenameagg) {
        this.usenameagg = usenameagg;
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
