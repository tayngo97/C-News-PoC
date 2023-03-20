package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BannerImageUrl {
//    public Metadata __metadata;
    @JsonProperty("Description")
    public String description;
    @JsonProperty("Url")
    public String url;
}
