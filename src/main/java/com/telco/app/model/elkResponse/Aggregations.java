
package com.telco.app.model.elkResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "usenameagg"
})
public class Aggregations {

    @JsonProperty("usenameagg")
    private Usenameagg usenameagg;

    @JsonProperty("usenameagg")
    public Usenameagg getUsenameagg() {
        return usenameagg;
    }

    @JsonProperty("usenameagg")
    public void setUsenameagg(Usenameagg usenameagg) {
        this.usenameagg = usenameagg;
    }

}
