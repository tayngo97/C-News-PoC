package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PrimaryQueryResult {
    public Metadata __metadata;
    @JsonProperty("CustomResults")
    public CustomResults customResults;
    @JsonProperty("QueryId")
    public String queryId;
    @JsonProperty("QueryRuleId")
    public String queryRuleId;
    @JsonProperty("RefinementResults")
    public Object refinementResults;
    @JsonProperty("RelevantResults")
    public RelevantResults relevantResults;
    @JsonProperty("SpecialTermResults")
    public Object specialTermResults;
}
