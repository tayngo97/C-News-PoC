package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ListItemAllFields {

//    public Metadata __metadata;
    @JsonProperty("CanvasContent1")
    public String canvasContent1;
}
