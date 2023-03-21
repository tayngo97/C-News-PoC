package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Metadata {
    public String type;

    public String id;
    public String uri;
    public String etag;
}
