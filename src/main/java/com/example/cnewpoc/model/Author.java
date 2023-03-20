package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
//    public Metadata __metadata;
    @JsonProperty("Title")
    public String title;
}
