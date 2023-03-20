package com.example.cnewpoc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SharePointAuth implements Serializable {

    private String token_type;
    private String expires_in;
    private String not_before;
    private String expires_on;
    private String resource;
    private String access_token;
}
