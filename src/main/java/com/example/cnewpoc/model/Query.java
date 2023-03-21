package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Query {

    public Metadata __metadata;
    @JsonProperty("ElapsedTime")
    public int elapsedTime;
    @JsonProperty("PrimaryQueryResult")
    public PrimaryQueryResult primaryQueryResult;
    @JsonProperty("Properties")
    public Properties properties;
    @JsonProperty("SecondaryQueryResults")
    public SecondaryQueryResults secondaryQueryResults;
    @JsonProperty("SpellingSuggestion")
    public Object spellingSuggestion;
    @JsonProperty("TriggeredRules")
    public TriggeredRules triggeredRules;
}
