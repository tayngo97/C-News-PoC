package com.example.cnewpoc.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class D {
    public ArrayList<Result> results;

    @JsonProperty("CanvasContent1")
    public String canvasContent1;
}
