package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RelevantResults {

    public Metadata __metadata;
    @JsonProperty("GroupTemplateId")
    public Object groupTemplateId;
    @JsonProperty("ItemTemplateId")
    public Object itemTemplateId;
    @JsonProperty("Properties")
    public Properties properties;
    @JsonProperty("ResultTitle")
    public Object resultTitle;
    @JsonProperty("ResultTitleUrl")
    public Object resultTitleUrl;
    @JsonProperty("RowCount")
    public int rowCount;
    @JsonProperty("Table")
    public Table table;
    @JsonProperty("TotalRows")
    public int totalRows;
    @JsonProperty("TotalRowsIncludingDuplicates")
    public int totalRowsIncludingDuplicates;
}
