package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Result {

    @JsonProperty("Key")
    public String key;
    @JsonProperty("Value")
    public String value;
    @JsonProperty("ValueType")
    public String valueType;
//    public Metadata __metadata;
    @JsonProperty("Cells")
    public Cells cells;

    @JsonProperty("ListItemAllFields")
    public ListItemAllFields listItemAllFields;

    @JsonProperty("UniqueId")
    public String uniqueId;

}
