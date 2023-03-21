package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cells {
    public ArrayList<Result> results;
}
