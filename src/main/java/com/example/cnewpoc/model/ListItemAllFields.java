package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListItemAllFields {
//    public Metadata __metadata;
    @JsonProperty("BannerImageUrl")
    public BannerImageUrl bannerImageUrl;
    @JsonProperty("Description")
    public String description;
}
