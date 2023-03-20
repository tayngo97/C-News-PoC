package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Result {

//    public Metadata __metadata;
    @JsonProperty("Author")
    public Author author;
    @JsonProperty("ListItemAllFields")
    public ListItemAllFields listItemAllFields;

    @JsonProperty("TimeCreated")
    public Date timeCreated;

    @JsonProperty("Title")
    public String title;
}
