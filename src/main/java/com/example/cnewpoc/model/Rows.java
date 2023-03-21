package com.example.cnewpoc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Rows {

    public ArrayList<Result> results;

}
